package com.hx.sqlSessionFactory;

import com.hx.config.XMLConfigurationBuilder;
import com.hx.pojo.Configuration;
import org.dom4j.DocumentException;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    /**
     * 1. 解析配置文件，封装容器对象
     * 2. 创建SqlSessionFactory对象
     * @param inputStream
     * @return
     */
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException {
        // 1. 解析配置文件，封装容器对象
        XMLConfigurationBuilder xmlConfigurationBuilder = new XMLConfigurationBuilder();
        Configuration configuration = xmlConfigurationBuilder.parse(inputStream);
        // 创建SqlSessionFactory工厂对象
        return new DefaultSqlSessionFactory(configuration);
    }
}
