package live.betterman.broker.dao;


import live.betterman.core.entity.Broker;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public interface BrokerDao {
    Broker getById(String brokerId);
}
