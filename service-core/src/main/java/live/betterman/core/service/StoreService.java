package live.betterman.core.service;

import live.betterman.core.entity.Store;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public interface StoreService {
    /**
     * 获取指定门店
     * @param storeId
     * @return
     */
    Store getById(String storeId);
}
