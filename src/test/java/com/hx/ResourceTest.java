package com.hx;

import com.hx.dao.IUserDao;
import com.hx.io.Resource;
import com.hx.pojo.User;
import com.hx.sqlSessionFactory.SqlSession;
import com.hx.sqlSessionFactory.SqlSessionFactory;
import com.hx.sqlSessionFactory.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class ResourceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User(1, "test");
        User user2 = sqlSession.selectOne("user.selectOne", user);
        System.out.println(user2);

    }

    @Test
    public void testList() throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        List<User> user = sqlSession.selectList("user.selectList", null);
        System.out.println(user);

    }

    @Test
    public void testProxy() throws Exception {
        InputStream resourceAsStream = Resource.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        User user = userDao.findByCondition(new User(1, "test"));
        System.out.println(user);
        sqlSession.close();

    }
}
