package live.betterman.house.service.impl;

import live.betterman.core.entity.House;
import live.betterman.core.exception.SyncDataException;
import live.betterman.core.service.HouseService;
import live.betterman.house.dao.HouseDao;
import live.betterman.house.service.HouseSyncDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Slf4j
@Service
public class HouseServiceImpl implements HouseService{

    @Autowired
    private HouseSyncDataService houseSyncDataService;

    @Autowired
    private HouseDao houseDao;

    @Override
    public House getByCode(String houseCode) {
        return houseDao.getByCode(houseCode);
    }

    @Override
    public void sync(House house) {
        try{
            boolean syncResult = houseSyncDataService.sync(house);
            if(syncResult && log.isDebugEnabled()){
                log.debug("sync house:" + house.getHouseCode());
            }
        }catch (SyncDataException ex){
            log.error("error", ex);
            //TODO:sync data exception
        }
    }

    @Override
    public void sync(String houseCode) {
        House house = houseSyncDataService.getResource(houseCode);
        sync(house);
    }

    @Override
    public boolean resetIndex() {
        return houseSyncDataService.resetIndex();
    }


}
