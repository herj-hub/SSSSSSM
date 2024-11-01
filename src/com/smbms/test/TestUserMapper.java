package com.smbms.test;

import com.smbms.dao.user.UserMapper;
import com.smbms.entity.User;
import com.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUserMapper {
    private SqlSession sqlSession=null;

    @Before
    public void before(){
        sqlSession= MybatisUtil.createSqlSession();
    }

    @After
    public void after(){
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
    }
    @Test
    public void testQuery1(){
        String userCode = "liming";
        String userPassword="1234567";

        User user = sqlSession.getMapper(UserMapper.class).login(userCode, userPassword);
        System.out.println(user.getUserName());

    }

}
