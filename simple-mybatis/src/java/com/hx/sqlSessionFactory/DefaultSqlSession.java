package com.hx.sqlSessionFactory;

import com.hx.config.BoundSql;
import com.hx.config.ParameterMapping;
import com.hx.executor.Executor;
import com.hx.pojo.Configuration;
import com.hx.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @Override
    public <E> List<E> selectList(String statementId, Object params) throws Exception {
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list = executor.query(configuration, mappedStatement, params);
        return list;
    }

    @Override
    public <T> T selectOne(String statementId, Object params) throws Exception {
        // 去调用 selectList
        List<Object> list = this.selectList(statementId, params);
        if (list.size() == 1) {
            return (T) list.get(0);
        } else if (list.size() > 1) {
            throw new RuntimeException("查询结果为空或者多条");
        }
        return null;

    }

    @Override
    public void close() {
        executor.close();
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        // 访问代理对象的方法时，实际上会被交给 invocationHandler 来处理
        Object proxy = Proxy.newProxyInstance(mapperClass.getClassLoader(), new Class[]{mapperClass},
                /**
                 * proxy：代理对象的引用，很少有
                 * method：被调用方的字节码对象
                 * args：当前执行方法所需的参数
                 */
                (proxy1, method, args) -> {
                    // 具体逻辑，执行底层JDBC
                    String methodName = method.getName();
                    String className = method.getDeclaringClass().getName();
                    String statementId = className + "." + methodName;

                    // 方法调用
                    MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                    // select/update/delete/insert
                    String sqlCommandType = mappedStatement.getSqlCommandType();
                    if ("select".equals(sqlCommandType)) {
                        // 判断调用的是selectList还是selectOne
                        Type genericReturnType = method.getGenericReturnType();
                        // 判断是否实现了泛型类型参数化
                        if (genericReturnType instanceof ParameterizedType) {
                            if (args != null) {
                                return selectList(statementId, args[0]);
                            }
                            return selectList(statementId, null);
                        } else {
                            return selectOne(statementId, args[0]);
                        }
                    }
                    return null;
                }
        );
        return (T) proxy;
    }


}
