package com.hx.config;

import com.hx.pojo.Configuration;
import com.hx.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;


public class XMLMapperBuilder {

    private Configuration configuration;
    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }



    public void parse(InputStream inputStream) throws DocumentException {

        Document document = new SAXReader().read(inputStream);
        Element mapper = document.getRootElement();
        List<Node> selectList = mapper.selectNodes("//select");
        String namespace = mapper.attributeValue("namespace");
        for (Node node : selectList) {
            Element element = (Element) node;
            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sql = element.getTextTrim();


            // 封装 MappedStatement对象
            MappedStatement mappedStatement = new MappedStatement();

            mappedStatement.setStatementId(namespace + "." + id);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSqlStatement(sql);
            mappedStatement.setSqlCommandType("select");

            // 讲封装好的 MappedStatement对象 封装到Configuration的map集合中
            configuration.getMappedStatementMap().put(mappedStatement.getStatementId(), mappedStatement);
        }

   }
}
