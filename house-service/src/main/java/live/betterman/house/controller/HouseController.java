package live.betterman.house.controller;

import live.betterman.core.entity.House;
import live.betterman.core.service.HouseService;
import live.betterman.house.service.HouseSyncDataService;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
@RestController
public class HouseController {
    @Autowired
    private HouseService houseService;

    @Autowired
    private HouseSyncDataService houseSyncDataService;

    @GetMapping("/house/code/{houseCode}")
    public House get(@PathVariable String houseCode){
        return houseService.getByCode(houseCode);
    }

    @GetMapping("/houseSource/code/{houseCode}")
    public House getHouseSource(@PathVariable String houseCode){
        return houseSyncDataService.getResource(houseCode);
    }

    @PostMapping("/house/sync/{houseCode}")
    public House sync(@PathVariable String houseCode){
        var house = houseService.getByCode(houseCode);
        houseService.sync(house);
        return house;
    }
    @GetMapping("/house/index/reset")
    public String resetIndex(){
        boolean result = houseService.resetIndex();
        return result?"success":"failed";
    }
}
