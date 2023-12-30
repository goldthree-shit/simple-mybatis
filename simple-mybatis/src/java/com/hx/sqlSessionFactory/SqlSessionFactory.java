package com.hx.sqlSessionFactory;

public interface SqlSessionFactory {

    /**
     * 1. 生产 SqlSession 对象
     * 2. 创建 Executor 对象
     * @return
     */
    SqlSession openSession();
}
