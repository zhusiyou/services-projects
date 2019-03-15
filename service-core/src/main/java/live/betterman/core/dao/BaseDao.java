package live.betterman.core.dao;

import live.betterman.core.entity.DomainEntity;

import java.io.Serializable;

/**
 * @author: zhudawei
 * @date: 2019/2/26
 * @description:
 */
public interface BaseDao<T extends DomainEntity>{
    T getById(Serializable id);
    boolean saveOrUpdate(T t);
}
