package live.betterman.broker.dao.impl;

import live.betterman.broker.dao.StoreDao;
import live.betterman.core.entity.Store;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public class StoreDaoImpl implements StoreDao {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Store getById(String storeId) {
        Query query = getSession().createQuery("from store where id = '" + storeId +"'");
        return (Store) query.uniqueResult();
    }
}
