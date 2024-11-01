package com.smbms.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 数据数据访问层基础类
 * 1. 加载驱动
 * 2. 创建连接
 *
 * 5. 关闭各种资源
 */
public class BaseDao {

    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    //经过static修饰的代码会自动执行
    static{
        init();
    }

    public static void init(){
        //1. 创建Properties对象
        Properties properties = new Properties();
        //2. 定义配置文件名
        String configFile = "database.properties";
        //3. 读取配置文件
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream(configFile);
        //4. 把文件读取到Properties对象中
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        user = properties.getProperty("username");
        password = properties.getProperty("password");

        try {
            //加载驱动
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return
     */
    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }



    /**
     * 关闭所有资源
     * @param connection
     * @param stmt
     * @param rs
     */
    public void closeAll(Connection connection, Statement stmt, ResultSet rs){
        try {
            if(rs!=null){
                rs.close();
            }
            if(stmt!=null){
                stmt.close();
            }
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行查询
     * @param sql SQL语句
     * @param params 参数数组
     * @return
     */
    public ResultSet executeQuery(String sql, Object[] params){
        //1. 加载驱动
        //2. 获取连接
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            for(int i=1;i<=params.length;i++){
                pstmt.setObject(i, params[i-1]);
            }
            rs = pstmt.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    /**
     * 执行增删改
     * @param sql
     * @param params
     * @return
     */
    public int executeUpdate(String sql, Object[] params){
        int num = 0;
        Connection conn = this.getConnection();
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(sql);
            for(int i=1;i<=params.length;i++){
                pstmt.setObject(i, params[i-1]);
            }
            num = pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            this.closeAll(conn, pstmt, null);
        }

        return num;
    }

}
