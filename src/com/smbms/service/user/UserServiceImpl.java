package com.smbms.service.user;

import com.smbms.dao.user.UserDao;
import com.smbms.dao.user.UserDaoImpl;
import com.smbms.dao.user.UserMapper;
import com.smbms.entity.User;
import com.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao=null;

    public UserServiceImpl(){ //为什么这样写，不能直接写，不用构造方法，那个原因忘记了
        userDao=new UserDaoImpl();
    }

    private SqlSession sqlSession=null;

//    @Before
//    public void before(){
//        sqlSession= MybatisUtil.createSqlSession();
//    }
//
//    @After
//    public void after(){
//        sqlSession.commit();
//        MybatisUtil.closeSqlSession(sqlSession);
//    }

    /**
     * 用户登录
     *
     * @param userCode     用户编码
     * @param userPassword 用户密码
     * @return
     */
    @Override
    public User login(String userCode, String userPassword) {
//        SqlSession sqlSession=null;
        sqlSession= MybatisUtil.createSqlSession();
        User user= sqlSession.getMapper(UserMapper.class).login(userCode,userPassword);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return user;

    }

    /**
     * 更新用户密码
     *
     * @param id
     * @param newPassword
     * @param id1
     * @param updateDate
     * @return
     */
    @Override
    public int updatePwd(long id, String newPassword, long id1, Date updateDate) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(UserMapper.class).updatePwd(id,newPassword,id1,updateDate);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return userDao.updatePwd(id,newPassword,id1,updateDate);
    }

    /**
     * 查询用户
     *
     * @return
     */
    @Override
    public List<User> queryUser() {
        return userDao.queryUser();
    }

    /**
     * 查询用户
     *
     * @param username
     * @param userRole
     * @return
     */
    @Override
    public List<User> queryUser(String username, Integer userRole) {
        return userDao.queryUser(username,userRole);
    }

    /**
     * 统计用户数量
     *
     * @param username
     * @param userRole
     * @return
     */
    @Override
    public int countUser(String username, Integer userRole) {
//        SqlSession sqlSession=null;
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(UserMapper.class).countUser(username,userRole);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
    }

    /**
     * 查询用户
     *
     * @param username
     * @param userRole
     * @param startNo
     * @param pageSize
     * @return
     */
    @Override
    public List<User> queryUser(String username, Integer userRole, int startNo, int pageSize) {
//        SqlSession sqlSession=null;
        sqlSession= MybatisUtil.createSqlSession();
        List<User> userList= sqlSession.getMapper(UserMapper.class).queryUser(username,userRole,startNo,pageSize);

//        测试专用
//        System.out.println("分页参数"+startNo+pageSize);
//        for(User user:userList){
//            System.out.println("角色名称："+user.getRole().getRoleName());
//        }
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return userList;
    }

    /**
     * 根据useCode统计用户数量
     *
     * @param userCode
     * @return
     */
    @Override
    public int countByUserCode(String userCode) {
//        SqlSession sqlSession=null;
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(UserMapper.class).countByUserCode(userCode);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @Override
    public int addUser(User user) {
//        SqlSession sqlSession=null;
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(UserMapper.class).addUser(user);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public int deleteUserById(Long id) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(UserMapper.class).deleteUserById(id);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User queryById(Long id) {
        sqlSession= MybatisUtil.createSqlSession();
        User user= sqlSession.getMapper(UserMapper.class).queryById(id);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return user;
//        return userDao.queryById(id);   用这个时去userview.jsp里把注释解开
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(UserMapper.class).updateUser(user);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return userDao.updateUser(user);
    }


}
