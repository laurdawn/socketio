package com.neo.dao;

import java.util.List;

public interface BaseDao<T> {


    /**
     * 根据Id查询实体
     */
    public T findEntityById(String id);

    /**
     * 新增实体
     */
    public void saveEntity(T entity);

    /**
     * 更新实体
     */
    public T updateEntityById(String id,T entity);

    /**
     * 根据Id删除实体
     */
    public int deleteEntityById(String id);

    /**
     * 查询全部
     */
    public List<T> selectAll();


}
