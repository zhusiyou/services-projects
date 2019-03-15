package live.betterman.broker.service.impl;

import live.betterman.broker.dao.BrokerDao;
import live.betterman.core.entity.Broker;
import live.betterman.core.service.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public class BrokerServiceImpl implements BrokerService {

    @Autowired
    private BrokerDao brokerDao;

    @Override
    public Broker getById(String brokerId) {
        if(!StringUtils.hasText(brokerId)){
            return null;
        }
        return brokerDao.getById(brokerId);
    }

}
