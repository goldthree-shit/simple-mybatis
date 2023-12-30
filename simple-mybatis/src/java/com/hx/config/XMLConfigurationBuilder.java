package com.hx.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hx.io.Resource;
import com.hx.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigurationBuilder {

    private Configuration configuration;

    public XMLConfigurationBuilder() {
        this.configuration = new Configuration();
    }

    /**
     * 解析配置文件，封装 Configuration 对象
     * @param inputStream
     * @return
     */
    public Configuration parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        // <property name="driverClassName" value="com.mysql.jdbc.Driver" ></property>
        List<Node> nodes = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Node node : nodes) {
            Element element = (Element) node;
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name, value);

        }
        // 创建数据源对象
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getProperty("driverClassName"));
        druidDataSource.setUrl(properties.getProperty("url"));
        druidDataSource.setUsername(properties.getProperty("username"));
        druidDataSource.setPassword(properties.getProperty("password"));

        // 设置数据源
        configuration.setDataSource(druidDataSource);

        // 解析mapper标签
        // <mapper resourse="mapper/UserMapper.xml"></mapper>
        List<Node> mapperList = rootElement.selectNodes("//mapper");
        for (Node node : mapperList) {
            Element element = (Element) node;
            String resource = element.attributeValue("resource");
            // 加载mapper.xml文件
            InputStream resourceAsStream = Resource.getResourceAsStream(resource);
            // 解析mapper.xml文件
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourceAsStream);
        }

        return configuration;
    }

}
