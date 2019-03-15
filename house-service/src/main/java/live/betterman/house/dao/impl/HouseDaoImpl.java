package live.betterman.house.dao.impl;

import live.betterman.core.dao.impl.BaseDaoImpl;
import live.betterman.core.entity.House;
import live.betterman.core.dao.BaseDao;
import live.betterman.house.dao.HouseDao;
import lombok.var;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
@Component
public class HouseDaoImpl extends BaseDaoImpl<House> implements HouseDao {

    @Override
    public House getByCode(String houseCode) {
        return this.getSingleByProperty("houseCode", houseCode);
    }

    @Override
    public List<House> getAll() {
        var template = this.getHibernateTemplate();
        template.setMaxResults(20000);

        return template.loadAll(House.class);
    }

//    @Override
//    public boolean saveOrUpdate(House house){
//        return super.saveOrUpdate(house);
//    }
}
