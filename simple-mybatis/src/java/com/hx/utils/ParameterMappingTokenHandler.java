package com.hx.utils;

import com.hx.config.ParameterMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterMappingTokenHandler implements TokenHandler{

    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();
    @Override
    public String handleToken(String content) {
        //先构建参数映射
        parameterMappings.add(buildParameterMapping(content));
        //如何替换很简单，永远是一个问号，但是参数的信息要记录在parameterMappings里面供后续使用
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        return new ParameterMapping(content);
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }
}
