package com.hx.pojo;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    // 数据源对象
    private DataSource dataSource;


    // key: statementId:namespace.id
    // value: MappedStatement 封装好的MappedStatement 对象
    private Map<String, MappedStatement> mappedStatementMap = new HashMap();

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, MappedStatement> getMappedStatementMap() {
        return mappedStatementMap;
    }

    public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
        this.mappedStatementMap = mappedStatementMap;
    }
}
