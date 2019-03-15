package live.betterman.house.service;

import live.betterman.core.entity.House;
import live.betterman.core.service.SyncDataService;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public interface HouseSyncDataService extends SyncDataService<House> {
    /**
     * 获取房源系统房子信息
     * @param houseCode
     * @return
     */
    House getResource(String houseCode);
    boolean resetIndex();
}
