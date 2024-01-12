package com.example.fitness;


import com.example.fitness.fellow.fellow;

import java.sql.SQLException;
import java.util.List;
/**
 * 定义通用的数据库操作接口，包括插入、更新、删除、查询等操作。
 *
 * @param <T> 数据库实体类型
 * @param <K> 主键类型
 */
public interface JDBCDAO<T,K> {
    /**
     * 根据主键查询数据库中的实体数据。
     *
     * @param primaryKey 主键值
     * @return 查询到的实体对象，如果未找到返回null
     * @throws SQLException 如果数据库操作发生异常
     */
    static fellow select(String primaryKey) throws SQLException {
        return null;
    }

    /**
     * 向数据库中插入单个实体数据。
     *
     * @param entity 要插入的实体对象
     * @throws SQLException 如果数据库操作发生异常
     */

    void insert(T entity) throws SQLException;
    /**
     * 向数据库中批量插入实体数据。
     *
     * @param entities 要插入的实体对象列表
     * @throws SQLException 如果数据库操作发生异常
     */
    void insertBatch(List<T> entities) throws SQLException;
    /**
     * 根据主键更新数据库中的实体数据。
     *
     * @param entity   要更新的实体对象
     * @param key      主键值
     * @throws SQLException 如果数据库操作发生异常
     */
    void updateByid(T entity, String key) throws SQLException;

    void updateByCondition(String condition, Object[]params, T entity) throws SQLException;
    /**
     * 根据主键删除数据库中的实体数据。
     *
     * @param primaryKey 主键值
     * @throws SQLException 如果数据库操作发生异常
     */
    void delete(K primaryKey)throws SQLException;

    void deleteBatch(List<K> primaryKeys) throws  SQLException;
    void deleteByCondition(String condition,Object[]params) throws SQLException;

    /**
     * 查询数据库中的所有实体数据。
     *
     * @return 所有实体对象的列表
     * @throws SQLException 如果数据库操作发生异常
     */
    List<T> selectAll() throws SQLException;

    /**
     * 根据条件查询数据库中的实体数据。
     *
     * @param conditions 查询条件
     * @return 符合条件的实体对象列表
     * @throws SQLException 如果数据库操作发生异常
     */
    List<T> selectByCondition(String conditions) throws SQLException;
    /**
     * 分页查询数据库中的实体数据。
     *
     * @param page     查询页码
     * @param pagesize 每页数据数量
     * @return 分页查询到的实体对象列表
     * @throws SQLException 如果数据库操作发生异常
     */
    List<T> selectPaged(int page, int pagesize)throws SQLException;
    /**
     * 统计指定楼宇的学生数量。
     *
     * @param building 楼宇名称
     * @return 学生数量
     * @throws SQLException 如果数据库操作发生异常
     */
    int countStudentsByBuilding(String building) throws SQLException;
    /**
     * 获取数据库中总记录数。
     *
     * @return 数据库中总记录数
     * @throws Exception 如果数据库操作发生异常
     */
    int gettotalRecords() throws Exception;
}
