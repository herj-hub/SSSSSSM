package com.smbms.dao.user;

import com.smbms.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface UserMapper {

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @return
     */
    User login(@Param("userCode") String userCode, @Param("userPassword") String userPassword);


    /**
     * 更新用户密码
     * @param id 用户id
     * @param newPassword
     * @param id1 modifyBy
     * @param updateDate
     * @return
     */
    int updatePwd(@Param("id") long id,
                  @Param("newPassword") String newPassword,
                  @Param("id1") long id1,
                  @Param("updateDate") Date updateDate);

    /**
     * 统计用户数量
     * @param username
     * @param userRole
     * @return
     */
    int countUser(@Param("username") String username,@Param("userRole") Integer userRole);

    /**
     * 查询用户
     * @param username
     * @param userRole
     * @param startNo
     * @param pageSize
     * @return
     */
    List<User> queryUser(@Param("username") String username,
                         @Param("userRole") Integer userRole,
                         @Param("startNo") int startNo,
                         @Param("pageSize") int pageSize);



    /**
     * 根据useCode统计用户数量
     * @param userCode
     * @return
     */
    int countByUserCode(@Param("userCode") String userCode);

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
