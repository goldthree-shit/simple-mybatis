package com.hx.io;

import java.io.InputStream;

public class Resource {

    /**
     * 根据配置文件路径加载配置文件
     * @param path
     * @return
     */
    public static InputStream getResourceAsStream(String path){
        return Resource.class.getClassLoader().getResourceAsStream(path);
    }
}
