package live.betterman.house.dao;

import live.betterman.core.dao.BaseDao;
import live.betterman.core.entity.House;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public interface HouseDao extends BaseDao<House> {
    House getByCode(String houseCode);
    List<House> getAll();
}
