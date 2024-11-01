package com.smbms.dao.user;

import com.smbms.entity.User;

import java.util.Date;
import java.util.List;

public interface UserDao {
    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    User login(String userCode, String userPassword);

    /**
     * 更新用户密码
     * @param id
     * @param newPassword
     * @param id1
     * @param updateDate
     * @return
     */
    int updatePwd(long id, String newPassword, long id1, Date updateDate);

    /**
     * 查询用户
     * @return
     */
    List<User> queryUser();


    /**
     * 查询用户
     * @param username
     * @param userRole
     * @return
     */
    List<User> queryUser(String username, Integer userRole);

    /**
     * 统计用户数量
     * @param username
     * @param userRole
     * @return
     */
    int countUser(String username, Integer userRole);

    /**
     * 查询用户
     * @param username
     * @param userRole
     * @param startNo
     * @param pageSize
     * @return
     */
    List<User> queryUser(String username, Integer userRole, int startNo, int pageSize);

    /**
     * 根据userCode统计用户数量
     * @param userCode
     * @return
     */
    int countByUserCode(String userCode);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int addUser(User user);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    int deleteUserById(Long id);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User queryById(Long id);

    /**
     * 更新用户
     * @param user
     * @return
     */
    int updateUser(User user);
}
