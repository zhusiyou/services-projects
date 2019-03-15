package live.betterman.core.service;

import live.betterman.core.entity.DomainEntity;
import live.betterman.core.exception.SyncDataException;

/**
 * @author: zhudawei
 * @date: 2019/2/22
 * @description:
 */
public interface SyncDataService<T extends DomainEntity> {
    boolean sync(T t) throws SyncDataException;
}
