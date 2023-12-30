package com.hx.sqlSessionFactory;

import com.hx.executor.Executor;
import com.hx.executor.SimpleExecutor;
import com.hx.pojo.Configuration;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;



    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession() {

        // 创建Executor对象
        Executor simpleExecutor = new SimpleExecutor();

        // 创建DefaultSqlSession对象
        SqlSession defaultSqlSession = new DefaultSqlSession(configuration, simpleExecutor);


        return defaultSqlSession;
    }
}
