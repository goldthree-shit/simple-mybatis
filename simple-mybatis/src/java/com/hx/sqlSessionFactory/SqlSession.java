package com.hx.sqlSessionFactory;

import java.util.List;

public interface SqlSession {


    /**
     * 定位到要执行的sql语句从而执行
     * @return
     * @param <E>
     */
    <E> List<E> selectList(String statementId, Object params) throws Exception;

    /**
     * 查询单个结果
     */
    <T> T selectOne(String statementId, Object params) throws Exception;

    /**
     * 清除资源
     */
    void close();

    /**
     * 生成代理对象。 任何方法最终都会转会到代理对象的invoke方法
     */
    <T> T getMapper(Class<?> mapperClass);

}
