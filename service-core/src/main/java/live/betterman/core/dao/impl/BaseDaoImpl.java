package live.betterman.core.dao.impl;

import live.betterman.core.dao.BaseDao;
import live.betterman.core.entity.DomainEntity;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * @author: zhudawei
 * @date: 2019/3/6
 * @description:
 */
public abstract class BaseDaoImpl<T extends DomainEntity> extends HibernateDaoSupport implements BaseDao<T> {
    private Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public T getById(Serializable id){
        return getHibernateTemplate().get(getTClass(), id);
    }

    protected T getSingleByProperty(String name, String value){
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(getTClass());
        criteria.add(Restrictions.eq(name, value));
        criteria.setMaxResults(1);
        return (T)criteria.uniqueResult();
    }

    protected List<T> getByProperty(String name, String value){
        Criteria criteria = this.getSessionFactory().getCurrentSession().createCriteria(getTClass());
        criteria.add(Restrictions.eq(name, value));
        return criteria.list();
    }

    @Override
    public boolean saveOrUpdate(T t){
        try {
            getHibernateTemplate().saveOrUpdate(getTClass().getName(), t);
            return true;
        }catch (DataAccessException ex){
            ex.printStackTrace();
            return false;
        }
    }
}
