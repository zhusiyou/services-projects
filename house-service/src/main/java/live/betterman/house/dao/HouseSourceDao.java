package live.betterman.house.dao;

import live.betterman.core.entity.House;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public interface HouseSourceDao {
    House getByCode(String houseCode);
}
