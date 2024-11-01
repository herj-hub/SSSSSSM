package com.smbms.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUtil {
    //提供会话工厂
    public static SqlSessionFactory sessionFactory;

    //静态代码块生成工厂对象
    static {
        try {
            //配置文件
            String resource = "mybatis-config.xml";
            //读取配置文件
            InputStream is = Resources.getResourceAsStream(resource);
            //创建SqlSessionFactory对象
            sessionFactory = new SqlSessionFactoryBuilder().build(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取会话对象
     * @return
     */
    public static SqlSession createSqlSession(){
        //不写，默认是true，表示自动提交事务
        //           false，需要手动提交
        return sessionFactory.openSession(false);
    }

    /**
     * 关闭sqlSession对象
     * @param sqlSession
     */
    public static void closeSqlSession(SqlSession sqlSession){
        if(sqlSession!=null){
            sqlSession.close();
        }
    }
}
