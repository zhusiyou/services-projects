package live.betterman.core.service;

import live.betterman.core.entity.House;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public interface HouseService {
    House getByCode(String houseCode);
    /**
     * 从房源系统同步数据到在线
     * @param house
     */
    void sync(House house);
    void sync(String houseCode);
    boolean resetIndex();
}
