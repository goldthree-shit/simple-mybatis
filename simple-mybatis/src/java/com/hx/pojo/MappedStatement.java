package com.hx.pojo;

/**
 * 存放 mapper.xml 的解析内容
 */
public class MappedStatement {

    // namespace.id 唯一标识
    private String statementId;

    // 返回值类型
    private String resultType;

    // 参数类型
    private String parameterType;

    // sql 语句
    private String sqlStatement;

    private String setSqlCommandType;

    public String getSqlCommandType() {
        return setSqlCommandType;
    }

    public void setSqlCommandType(String setSqlCommandType) {
        this.setSqlCommandType = setSqlCommandType;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }


}
