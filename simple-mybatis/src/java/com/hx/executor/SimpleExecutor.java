package com.hx.executor;

import com.hx.config.BoundSql;
import com.hx.config.ParameterMapping;
import com.hx.pojo.Configuration;
import com.hx.pojo.MappedStatement;
import com.hx.utils.GenericTokenParser;
import com.hx.utils.ParameterMappingTokenHandler;

import javax.sql.DataSource;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor {
    private Connection connection  = null;
    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;

    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        // 获取数据库连接
        connection = configuration.getDataSource().getConnection();
        // 获取要执行的sql语句
        // select * from user where id = #{} and username = #{}
        String sqlStatement = mappedStatement.getSqlStatement();
        BoundSql boundSql = getBoundSql(sqlStatement);
        String finalSql = boundSql.getFinalSql();
        preparedStatement = connection.prepareStatement(finalSql);

        // 设置参数
        String parameterType = mappedStatement.getParameterType();
        // 等于null没必要设置参数
        if (parameterType != null) {
            Class<?> parameterTypeClass = Class.forName(parameterType);
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                // id || username
                String paramName = parameterMapping.getContent();
                // 反射
                Field declaredField = parameterTypeClass.getDeclaredField(paramName);
                // 暴力访问
                declaredField.setAccessible(true);
                Object value = declaredField.get(param);
                preparedStatement.setObject(i + 1, value);
            }
        }

        // 执行sql
        resultSet = preparedStatement.executeQuery();


        // 处理返回结果集
        ArrayList<E> list = new ArrayList<>();
        while (resultSet.next()) {
            // 元数据信息 包含了字段名以及字段的值
            ResultSetMetaData metaData = resultSet.getMetaData();

            String resultType = mappedStatement.getResultType();
            Class<?> resultTypeClass = Class.forName(resultType);
            Object o = resultTypeClass.newInstance();
            // 下标是从1开始的
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                // 字段名
                String columnName = metaData.getColumnName(i);
                // 字段值
                Object columnValue = resultSet.getObject(columnName);

                // 封装
                // 反射, 获取属性描述器。通过API方法获取某个属性的读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 实例化对象. 参数1 示例对象， 参数2，要设置的值
                writeMethod.invoke(o, columnValue);
            }
            // 封装到list中
            list.add((E)o);
        }

        return list;
    }

    @Override
    public void close() {

        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 完成占位符的替换
     * @return
     */
    BoundSql getBoundSql(String sqlStatement) {
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String finalSql = genericTokenParser.parse(sqlStatement);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        return new BoundSql(finalSql, parameterMappings);
    }
}
