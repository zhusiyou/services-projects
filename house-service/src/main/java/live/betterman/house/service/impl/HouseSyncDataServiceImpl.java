package live.betterman.house.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import live.betterman.core.entity.House;
import live.betterman.core.event.house.BrokerChangedEvent;
import live.betterman.core.event.house.ShowStatusChangedEvent;
import live.betterman.core.exception.SyncDataException;
import live.betterman.core.model.Tag;
import live.betterman.core.search.HouseSearchModel;
import live.betterman.core.service.CommunityService;
import live.betterman.core.service.HouseSearchService;
import live.betterman.house.HouseRuleConfigs;
import live.betterman.house.dao.HouseDao;
import live.betterman.house.dao.HouseSourceDao;
import live.betterman.house.service.HouseSyncDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Slf4j
@Service
public class HouseSyncDataServiceImpl implements HouseSyncDataService {
    @Autowired
    private HouseSourceDao houseSourceDao;

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private HouseSearchService houseSearchService;

    @Autowired
    private CommunityService communityService;

    /**
     * 同步房源系统数据至在线系统
     * @param house 房源系统中的房源信息
     */
    @Override
    public boolean sync(House house) throws SyncDataException {
        String houseCode = house.getHouseCode();
        //获取在线数据中的房源信息
        House original = getOriginal(houseCode);

        //如果数据无变化, 直接返回
        if(original != null && original.equals(house)){
            return true;
        }

        //匹配展示规则
        matchRules(house);

        generateSyncEvents(house, original);

        boolean isSuccessful = updateDb(house);
        if(isSuccessful){
            index(house);
            dispatchEvents(house);
        }else{
            //TODO:
            throw new SyncDataException();
        }

        return true;
    }

    @Override
    public House getResource(String houseCode) {
        return houseSourceDao.getByCode(houseCode);
    }

    @Override
    public boolean resetIndex() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("读入数据");
        List<House> houses = houseDao.getAll();
        stopWatch.stop();

        stopWatch.start("转换");
        List<HouseSearchModel> models = houses.parallelStream().map(h->convert2SearchModel(h)).collect(Collectors.toList());
        stopWatch.stop();


        stopWatch.start("es");
        boolean result = houseSearchService.bulkIndex(models);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return result;

//        int maxBulkCount = 2000;

//        List<Boolean> result = new ArrayList<>();
//        int count = 0;
//
//        while (true){
//            List<HouseSearchModel> list = models.stream().skip(count * maxBulkCount).limit(maxBulkCount).collect(Collectors.toList());
//            System.out.println("count: " + count);
//
//            if(list.size()==0){
//                break;
//            }
//
//            boolean r = houseSearchService.bulkIndex(list);
//            result.add(r);
//        if(list.size()<maxBulkCount){
//            break;
//        }
//
//            count++;
//        }
//
//        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());


//        List<Boolean> result = new ArrayList<>();
//
//        //使用 guava 开源框架的 ThreadFactoryBuilder 给线程池的线程设置名字
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("bulk-index-thread-%d").build();
//
//        ExecutorService pool = new ThreadPoolExecutor(5, 5, 0L,
//                TimeUnit.MILLISECONDS,
//                new LinkedBlockingDeque<Runnable>(200),
//                namedThreadFactory,
//                new ThreadPoolExecutor.AbortPolicy());
//
//        int count = 0;
//
//        while (true){
//            List<HouseSearchModel> list = models.stream().skip(count * maxBulkCount).limit(maxBulkCount).collect(Collectors.toList());
//            System.out.println("count: " + count + new Random());
//
//            if(list.size()==0){
//                break;
//            }
//            pool.submit(()->{
//                System.out.println(Thread.currentThread().getName() + "-------------" + list.size());
//                boolean r = houseSearchService.bulkIndex(list);
//                result.add(r);
//
//            });
//
//            if(list.size()<maxBulkCount){
//                break;
//            }
//            count++;
//        }
//
//        pool.shutdown();
//
//
//        try{
//            pool.awaitTermination(20, TimeUnit.SECONDS);
//            stopWatch.stop();
//            System.out.println(stopWatch.prettyPrint());
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }





    }

    private House getOriginal(String houseCode) {
        return houseDao.getByCode(houseCode);
    }
    private void generateSyncEvents(House house, House original){

        if(original != null && original.isShowStatus()!=house.isShowStatus()){
            house.addEvent(new ShowStatusChangedEvent(house));
        }

        if(original != null && !original.getBrokerId().equals(house.getBrokerId())){
            house.addEvent(new BrokerChangedEvent(house, original.getBrokerId()));
        }

    }
    private boolean matchRules(House house){
        //1.待售、非举报锁定(排除新增/淘宝三天锁定)； 2.非私密、已上架；
        //5.小区名称是已存在；
        //7.独家、锁盘、授权委托至少满足其一(建委真房源打开时有效)；8.房源单价小于15万

        //6.必须麦田真房源
        if(!house.isRealHouseFlag()){
            return false;
        }

        //4.有跟单人、有房源评论至少满足其一
        if(!StringUtils.hasText(house.getBrokerId())){
            return false;
        }

        //3.总价大于40万并且小于8000万
        if(house.getPriceTotal()< HouseRuleConfigs.minHousePrice
                || house.getPriceTotal()>HouseRuleConfigs.maxHousePrice){
            return false;
        }

        house.setShowStatus(true);
        return true;
    }
    private boolean updateDb(House house){
        return houseDao.saveOrUpdate(house);
    }
    private HouseSearchModel convert2SearchModel(House house){
        HouseSearchModel searchModel = new HouseSearchModel();
        //BeanUtils.copyProperties(house, searchModel);

        //searchModel.setTitle();
        searchModel.setHouseCode(house.getHouseCode());
        searchModel.setBrokerId(house.getBrokerId());
        searchModel.setCommunityId(house.getCommunityId());
        searchModel.setShowStatus(house.isShowStatus());
        searchModel.setRealHouseFlag(house.isRealHouseFlag());

        Tag[] tags = generateTags(house);
        if(null != tags && tags.length>0){
            List<String> tagCodes = Arrays.asList(tags).stream().map(m->m.getCode()).collect(Collectors.toList());
            searchModel.setTags(tagCodes.toArray(new String[]{}));
        }

        searchModel.setCommunityAlias(communityService.getAlias(house.getCommunityId()));
        searchModel.setCommunityName(communityService.getName(house.getCommunityId()));

        //TODO: to be continued

        return searchModel;
    }
    private Tag[] generateTags(House house){
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("t1","满五唯一", "1"));
        tags.add(new Tag("t2", "带车位", "2"));
        tags.add(new Tag("t3", "临近地铁", "3"));

        return tags.toArray(new Tag[]{});
    }
    private void dispatchEvents(House house){
        if(house.getEvents()==null || house.getEvents().isEmpty()){
            return;
        }
        house.getEvents().stream().forEach(m->System.out.println(m));
    }
    private void index(House house){
        boolean showStatusChanged = house.getEvents().stream().anyMatch(e->"ShowStatusChangedEvent".equals(e.getEventName()));
        boolean needUpdateEs = showStatusChanged && !house.isShowStatus();
        if(house.isShowStatus() || needUpdateEs){
            try {
                //index to es
                houseSearchService.index(convert2SearchModel(house));
            }catch (IOException ex){
                log.error(ex.getMessage(), ex.getStackTrace());
            }
        }
    }

}
