package live.betterman.broker.dao.impl;

import live.betterman.broker.dao.BrokerDao;
import live.betterman.core.entity.Broker;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public class BrokerDaoImpl implements BrokerDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession(){
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Broker getById(String brokerId) {
        Query query = getSession().createQuery("from broker where brokerId = '" + brokerId +"'");
        return (Broker)query.uniqueResult();
    }
}
