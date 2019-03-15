package live.betterman.house.dao.impl;

import live.betterman.core.dao.impl.BaseDaoImpl;
import live.betterman.core.entity.House;
import live.betterman.house.dao.HouseSourceDao;
import org.springframework.stereotype.Service;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
@Service
public class HouseSourceDaoImpl extends BaseDaoImpl<House> implements HouseSourceDao {
    @Override
    public House getByCode(String houseCode) {
        return this.getSingleByProperty("houseCode", houseCode);
    }
}
