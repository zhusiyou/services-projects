package live.betterman.house.test;

import live.betterman.core.entity.House;
import live.betterman.core.service.HouseService;
import live.betterman.house.service.HouseSyncDataService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public class AppTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        context.start();

        HouseService houseService = context.getBean(HouseService.class);
        House house = houseService.getByCode("FY00322309");
        System.out.println(house);


        HouseSyncDataService syncDataService = context.getBean(HouseSyncDataService.class);
        house = syncDataService.getResource("FY00028316");
        System.out.println(house);

//        try {
//            // 按任意键退出
//            int input = System.in.read();
//            if(input == 1){
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
