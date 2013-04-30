package com.xiva.dao.base.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.stereotype.Repository;

import com.xiva.common.util.SearchContent;
import com.xiva.common.util.SearchItem;
import com.xiva.common.util.SearchContent.QueryJoinEnum;
import com.xiva.common.util.SearchContent.QueryWhereEnum;
import com.xiva.dao.base.BaseDao;
import com.xiva.domain.base.BaseEntity;
import com.xiva.service.user.impl.UserServiceImpl;

@Repository
@SuppressWarnings("unchecked")
public class BaseDaoImpl<E extends BaseEntity, PK extends Serializable> implements BaseDao<E, PK>
{

    private static Log log = LogFactory.getFactory().getInstance(UserServiceImpl.class);

    private Class<E> entityClass;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    protected EntityManager entityManager;

    protected JpaTemplate getJpaTemplate()
    {
        return new JpaTemplate(this.entityManagerFactory);

    }

    public BaseDaoImpl()
    {
        Class<?> c = this.getClass();
        Type type = c.getGenericSuperclass();
        if (type instanceof ParameterizedType)
        {
            this.entityClass = (Class<E>) ((ParameterizedType) type).getActualTypeArguments()[0];
        }
    }

    @Override
    public E getByPK(PK id)
    {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        return (E) jpaTemplate.find(entityClass, id);
    }

    @Override
    public List<E> findByProperty(String property, Object value)
    {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        String clazzName = entityClass.getName();
        String queryString = "from " + clazzName + " where " + property + " = :property";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("property", value);
        List<E> list = (List<E>) jpaTemplate.findByNamedParams(queryString, params);
        return list;
    }

    @Override
    public E getByProperty(String property, Object value)
    {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        E entity = null;
        String clazzName = entityClass.getName();
        String queryString = "from " + clazzName + " where " + property + " = :property";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("property", value);
        List list = jpaTemplate.findByNamedParams(queryString, params);
        if (list != null && !list.isEmpty())
        {
            entity = (E) list.get(0);
        }
        return entity;
    }

    @Override
    public void add(E entity)
    {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        jpaTemplate.persist(entity);
    }

    @Override
    public void delete(E entity)
    {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        jpaTemplate.remove(entity);
    }

    @Override
    public void update(E entity)
    {
        final JpaTemplate jpaTemplate = getJpaTemplate();
        jpaTemplate.merge(entity);
    }

    @Override
    public List<E> findByProperties(SearchContent searchContent)
    {
        final List<SearchItem> queryParams = searchContent.getQueryParams();
        final int listSize = queryParams.size();
        final JpaTemplate jpaTemplate = getJpaTemplate();
        final boolean isPage = searchContent.isUserPage();
        final int start = searchContent.getPageStart();
        final int pageSize = searchContent.getPageSize();
        List<E> list = jpaTemplate.executeFind(new JpaCallback()
        {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException
            {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<E> c = cb.createQuery(entityClass);
                Root<E> emp = c.from(entityClass);
                c.select(emp);
                c.distinct(true);
                Predicate predicateG = cb.conjunction();
                for (int i = 0; i < listSize; i++)
                {
                    Predicate predicate = null;
                    SearchItem searchItem = queryParams.get(i);
                    String attributeName = searchItem.getAttributeName();
                    Object value = searchItem.getValue();
                    QueryJoinEnum joinTypeEnum = searchItem.getQueryJoinEnum();
                    QueryWhereEnum whereTypeEnum = searchItem.getQueryWhere();
                    String[] attributeNames = attributeName.split("\\.");
                    Path path = emp.get(attributeName);
                    if (attributeNames.length == 1)
                    {
                        path = emp.get(attributeName);
                    }
                    else
                    {
                        for (int j = 0; j < attributeNames.length; j++)
                        {
                            path = emp.get(attributeNames[j]);
                        }
                    }
                    ParameterExpression<?> p = cb.parameter(value.getClass(), attributeName);
                    switch (whereTypeEnum)
                    {
                    case EQUAL:
                        predicate = cb.equal(path, p);
                        break;
                    case DIFFER:
                        predicate = cb.notEqual(path, p);
                        break;
                    }
                    if (joinTypeEnum.equals(QueryJoinEnum.JOIN_OR))
                    {
                        predicateG = cb.or(predicateG, predicate);
                    }
                    else
                    {
                        predicateG = cb.and(predicateG, predicate);
                    }
                }

                c.where(predicateG);
                TypedQuery<E> query = em.createQuery(c);
                for (int i = 0; i < listSize; i++)
                {
                    SearchItem searchItem = queryParams.get(i);
                    String attributeName = searchItem.getAttributeName();
                    Object value = searchItem.getValue();
                    query.setParameter(attributeName, value);
                }
                if (isPage)
                {
                    query.setFirstResult(start);
                    query.setMaxResults(pageSize);
                }
                return query.getResultList();
            }

        });
        return list;
    }

    @Override
    public E getByProperties(SearchContent searchContent)
    {
        E entity = null;
        final List<SearchItem> queryParams = searchContent.getQueryParams();
        final int listSize = queryParams.size();
        final JpaTemplate jpaTemplate = getJpaTemplate();
        List<E> list = jpaTemplate.executeFind(new JpaCallback()
        {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException
            {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<E> c = cb.createQuery(entityClass);
                Root<E> emp = c.from(entityClass);
                c.select(emp);
                c.distinct(true);
                Predicate predicateG = cb.conjunction();
                for (int i = 0; i < listSize; i++)
                {
                    Predicate predicate = null;
                    SearchItem searchItem = queryParams.get(i);
                    String attributeName = searchItem.getAttributeName();
                    Object value = searchItem.getValue();
                    QueryJoinEnum joinTypeEnum = searchItem.getQueryJoinEnum();
                    QueryWhereEnum whereTypeEnum = searchItem.getQueryWhere();
                    String[] attributeNames = attributeName.split("\\.");
                    Path path = emp.get(attributeName);
                    if (attributeNames.length == 1)
                    {
                        path = emp.get(attributeName);
                    }
                    else
                    {
                        for (int j = 0; j < attributeNames.length; j++)
                        {
                            path = emp.get(attributeNames[j]);
                        }
                    }
                    ParameterExpression<?> p = cb.parameter(value.getClass(), attributeName);
                    switch (whereTypeEnum)
                    {
                    case EQUAL:
                        predicate = cb.equal(path, p);
                        break;
                    case DIFFER:
                        predicate = cb.notEqual(path, p);
                        break;
                    }
                    if (joinTypeEnum.equals(QueryJoinEnum.JOIN_OR))
                    {
                        predicateG = cb.or(predicateG, predicate);
                    }
                    else
                    {
                        predicateG = cb.and(predicateG, predicate);
                    }
                }

                c.where(predicateG);
                TypedQuery<E> query = em.createQuery(c);
                for (int i = 0; i < listSize; i++)
                {
                    SearchItem searchItem = queryParams.get(i);
                    String attributeName = searchItem.getAttributeName();
                    Object value = searchItem.getValue();
                    query.setParameter(attributeName, String.valueOf(value));
                }
                return query.getResultList();
            }

        });
        if (list != null && !list.isEmpty())
        {
            entity = list.get(0);
        }
        return entity;
    }

    @Override
    public Long getTotalCount(SearchContent searchContent)
    {

        List<SearchItem> queryParams = searchContent.getQueryParams();
        int listSize = queryParams.size();
        StringBuffer querySql = new StringBuffer();
        querySql.append("select count(*)");
        querySql.append(" from ");
        querySql.append(entityClass.getName());
        StringBuilder whereCondition = new StringBuilder(" where ");

        Map<String, Object> params = new HashMap<String, Object>();
        for (int i = 0; i < listSize; i++)
        {
            SearchItem item = queryParams.get(i);

            if (i > 0)
            {
                whereCondition.append(SearchContent.getJoinValue(item.getQueryJoinEnum())).append(" ");
            }

            whereCondition.append(item.getAttributeName()).append(SearchContent.getWhereValue(item.getQueryWhere())).append(":").append(
                    item.getAttributeName()).append(" ");
            params.put(item.getAttributeName(), item.getValue());
        }
        if (listSize > 0)
        {
            querySql.append(whereCondition);
        }

        JpaTemplate jpaTemplate = getJpaTemplate();
        Long size = 0L;
        try
        {
            size = (Long) jpaTemplate.findByNamedParams(querySql.toString(), params).get(0);
        }
        catch (Exception e)
        {
            log.error(e);
        }

        return size;
    }

    protected String getCountField(Class entityClass)
    {
        String out = "o";
        try
        {
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(entityClass).getPropertyDescriptors();
            for (PropertyDescriptor propertydesc : propertyDescriptors)
            {
                Method method = propertydesc.getReadMethod();
                if (method != null && method.isAnnotationPresent(Id.class))
                {
                    PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
                    out = propertydesc.getName() + "." + (!ps[1].getName().equals("class") ? ps[1].getName() : ps[0].getName());
                    break;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return out;
    }

}
