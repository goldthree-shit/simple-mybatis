package com.hx.executor;

import com.hx.pojo.Configuration;
import com.hx.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

public interface Executor {

    <E>List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception;

    void close();
}
