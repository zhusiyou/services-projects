package live.betterman.search.test;

import com.alibaba.fastjson.JSON;
import live.betterman.core.search.HouseSearchModel;
import live.betterman.core.service.HouseSearchService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: zhudawei
 * @date: 2019/2/27
 * @description:
 */
public class AppTest {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.start();

        HouseSearchService searchService = context.getBean(HouseSearchService.class);
        HouseSearchModel model = new HouseSearchModel();
        model.setHouseCode("FY00005");
        model.setAreaSize(105.33);
        model.setBrokerId("50410");
        model.setCommunityId("communityId");
        model.setPriceTotal(102400.23);
        model.setRealHouseFlag(true);
        model.setShowStatus(false);
        model.setCommunityName("小区名称");
        model.setCommunityAlias(new String[]{"小区别名1", "小区别名2"});
        model.setTags(new String[]{"t1", "t2", "t3"});
        String dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        System.out.println(dt);
        model.setAddDatetime(dt);
        model.setVersion("1");
        model.setRoomNumberId("001");
        model.setTitle("省委华林路单位宿舍 3室2厅");

        System.out.println(JSON.toJSONString(model));
        boolean result = searchService.index(model);


        System.exit(0);

    }
}
