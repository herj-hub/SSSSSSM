package com.smbms.service.user;

import com.smbms.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    /**
     * 用户登录
     * @param userCode 用户编码
     * @param userPassword  用户密码
     * @return
     */
//    User login(@Param("userCode") String userCode,@Param("userPassword") String userPassword); //TODO 这种写法有问题吗
    User login(String userCode,String userPassword);

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
     * 根据useCode统计用户数量
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
