package com.xiva.dao.base;

import java.io.Serializable;
import java.util.List;

import com.xiva.common.util.SearchContent;
import com.xiva.domain.base.BaseEntity;

public interface BaseDao<E extends BaseEntity, PK extends Serializable> {
    
    /**
     * @Description 
     * @param id
     * @return
     */
    public E getByPK(PK id);
    
    /**
     * 
     * @param property
     * @param value
     * @return
     */
    public E getByProperty(String property, Object value);
    
    /**
     * 
     * @param property
     * @param value
     * @return
     */
    public List<E> findByProperty(String property, Object value);
    
    /**
     * @Description 
     * @param properties
     * @param value
     * @return
     */
    public E getByProperties(SearchContent searchContent);
    
    /**
     * @Description 
     * @param properties
     * @param value
     * @return
     */
    public List<E> findByProperties(SearchContent searchContent);
    
    /**
     * @Description 
     * @param entity
     */
    public void delete(E entity);
    
    /**
     * @Description 
     * @param entity
     */
    public void add(E entity);
    
    /**
     * @Description 
     * @param entity
     */
    public void update(E entity);
    
    /**
     * @Description
     * @param search
     * @return
     */
    public Long getTotalCount(SearchContent searchContent);
}
