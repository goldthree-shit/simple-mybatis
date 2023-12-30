package com.hx.dao;

import com.hx.pojo.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll() throws Exception;

    User findByCondition(User user) throws Exception;
}
