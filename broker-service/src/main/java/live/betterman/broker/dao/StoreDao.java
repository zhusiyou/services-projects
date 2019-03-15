package live.betterman.broker.dao;

import live.betterman.core.entity.Store;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public interface StoreDao {
    Store getById(String storeId);
}
