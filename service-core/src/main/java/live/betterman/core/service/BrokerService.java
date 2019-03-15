package live.betterman.core.service;

import live.betterman.core.entity.Broker;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public interface BrokerService {
    /**
     * 获取指定经纪人
     * @param brokerId
     * @return
     */
    Broker getById(String brokerId);

}
