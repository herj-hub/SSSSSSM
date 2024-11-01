package com.smbms.dao.user;

import com.smbms.dao.BaseDao;
import com.smbms.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {
    /**
     * 登录
     *
     * @param userCode
     * @param userPassword
     * @return
     */
    @Override
    public User login(String userCode, String userPassword) {
        String sql = "SELECT id,userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate,modifyBy,modifyDate FROM smbms_user WHERE userCode=? AND userPassword=?";
        Object[] params = {userCode, userPassword};
        ResultSet rs = this.executeQuery(sql, params);

        User user = null;
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String userCode1 = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword1 = rs.getString("userPassword");
                Integer gender = rs.getInt("gender");
                Date birthday = rs.getDate("birthday");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Integer userRole = rs.getInt("userRole");
                Long createdBy = rs.getLong("createdBy");
                Date creationDate = rs.getDate("creationDate");
                Long modifyBy = rs.getLong("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");

                user = new User(id, userCode1, userName, userPassword1, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }
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
        String sql = "UPDATE smbms_user SET userPassword=?,modifyBy=?,modifyDate=? WHERE id=?";
        Object[] params = {newPassword, id1, updateDate, id};
        return this.executeUpdate(sql, params);
    }

    /**
     * 查询用户
     *
     * @return
     */
    @Override
    public List<User> queryUser() {
        String sql = "SELECT user.id,user.userCode,user.userName,user.userPassword,user.gender,user.birthday,user.phone,user.address,user.userRole,user.createdBy,user.creationDate,user.modifyBy,user.modifyDate,role.`roleName` FROM smbms_user USER INNER JOIN smbms_role role ON user.userRole=role.id ORDER BY id DESC";
        Object[] params = {};
        ResultSet rs = this.executeQuery(sql, params);

        List<User> userList = new ArrayList<>();
        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String userCode1 = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword1 = rs.getString("userPassword");
                Integer gender = rs.getInt("gender");
                Date birthday = rs.getDate("birthday");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Integer userRole = rs.getInt("userRole");
                Long createdBy = rs.getLong("createdBy");
                Date creationDate = rs.getDate("creationDate");
                Long modifyBy = rs.getLong("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
                String userRoleName = rs.getString("roleName");

                User user = new User(id, userCode1, userName, userPassword1, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate, userRoleName);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }

        return userList;
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
        String sql = "SELECT user.id,user.userCode,user.userName,user.userPassword,user.gender,user.birthday,user.phone,user.address,user.userRole,user.createdBy,user.creationDate,user.modifyBy,user.modifyDate,role.`roleName` FROM smbms_user USER INNER JOIN smbms_role role ON user.userRole=role.id ";
        sql += " where 1=1";

        if (username != null) {
            sql += " AND user.userName LIKE '%" + username + "%'";
        }
        if (userRole != null) {
            sql += " AND user.userRole = " + userRole;
        }

        sql += " ORDER BY user.id DESC";
        System.out.println("拼接的语句是：" + sql);
        Object[] params = {};
        ResultSet rs = this.executeQuery(sql, params);
        List<User> userList = new ArrayList<>();

        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String userCode1 = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword1 = rs.getString("userPassword");
                Integer gender = rs.getInt("gender");
                Date birthday = rs.getDate("birthday");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Integer userRole2 = rs.getInt("userRole");
                Long createdBy = rs.getLong("createdBy");
                Date creationDate = rs.getDate("creationDate");
                Long modifyBy = rs.getLong("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
                String userRoleName = rs.getString("roleName");

                User user = new User(id, userCode1, userName, userPassword1, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate, userRoleName);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }

        return userList;
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
        String sql = "SELECT COUNT(1) AS num FROM smbms_user WHERE 1 = 1 ";
        if (username != null) {
            sql += " and userName like '%" + username + "%'";
        }
        if (userRole != null) {
            sql += " and  userRole = " + userRole;
        }
        System.out.println("统计用户数量，拼接SQL:" + sql);

        Object[] params = {};
        ResultSet rs = this.executeQuery(sql, params);

        int num = 0;
        try {
            while (rs.next()) {
                num = rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }

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
        String sql = "SELECT user.id,user.userCode,user.userName,user.userPassword,user.gender,user.birthday,user.phone,user.address,user.userRole,user.createdBy,user.creationDate,user.modifyBy,user.modifyDate,role.`roleName` FROM smbms_user USER INNER JOIN smbms_role role ON user.userRole=role.id ";
        sql += " where 1=1";

        if (username != null) {
            sql += " AND user.userName LIKE '%" + username + "%'"; //为什么不能传问号
        }
        if (userRole != null) {
            sql += " AND user.userRole = " + userRole;
        }

        sql += " ORDER BY user.id DESC";
        //分页语句
        sql += " LIMIT ?,? ";

        System.out.println("拼接的语句是：" + sql);
        Object[] params = {startNo, pageSize};

        ResultSet rs = this.executeQuery(sql, params);
        List<User> userList = new ArrayList<>();

        try {
            while (rs.next()) {
                Long id = rs.getLong("id");
                String userCode1 = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword1 = rs.getString("userPassword");
                Integer gender = rs.getInt("gender");
                Date birthday = rs.getDate("birthday");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Integer userRole2 = rs.getInt("userRole");
                Long createdBy = rs.getLong("createdBy");
                Date creationDate = rs.getDate("creationDate");
                Long modifyBy = rs.getLong("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
                String userRoleName = rs.getString("roleName");

                User user = new User(id, userCode1, userName, userPassword1, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate, userRoleName);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }

        return userList;
    }

    /**
     * 根据userCode统计用户数量
     *
     * @param userCode
     * @return
     */
    @Override
    public int countByUserCode(String userCode) {
        String sql = "SELECT COUNT(1) AS num FROM  smbms_user WHERE userCode=?";
        Object[] params = {userCode};
        ResultSet rs = this.executeQuery(sql, params);

        int num = 0;

        try {
            while (rs.next()) {
                num = rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }

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
        String sql = "INSERT INTO smbms_user (userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate) VALUES (?,?,?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(), user.getGender(), user.getBirthday(), user.getPhone(), user.getAddress(), user.getUserRole(), user.getCreatedBy(), user.getCreationDate()};
        return this.executeUpdate(sql, params);
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public int deleteUserById(Long id) {
        String sql="DELETE FROM smbms_user WHERE id=?";
        Object[] params={id};
        return this.executeUpdate(sql,params);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User queryById(Long id) {
        String sql = "SELECT user.id,user.userCode,user.userName,user.userPassword,user.gender,user.birthday,user.phone,user.address,user.userRole,user.createdBy,user.creationDate,user.modifyBy,user.modifyDate,role.`roleName` FROM smbms_user USER INNER JOIN smbms_role role ON user.userRole=role.id where user.id=?";
        Object[] params={id};
        ResultSet rs=this.executeQuery(sql,params);
        User user=null;

        try {
            while (rs.next()) {
                Long id2 = rs.getLong("id");
                String userCode1 = rs.getString("userCode");
                String userName = rs.getString("userName");
                String userPassword1 = rs.getString("userPassword");
                Integer gender = rs.getInt("gender");
                Date birthday = rs.getDate("birthday");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                Integer userRole = rs.getInt("userRole");
                Long createdBy = rs.getLong("createdBy");
                Date creationDate = rs.getDate("creationDate");
                Long modifyBy = rs.getLong("modifyBy");
                Date modifyDate = rs.getDate("modifyDate");
                String userRoleName = rs.getString("roleName");

                user = new User(id2, userCode1, userName, userPassword1, gender, birthday, phone, address, userRole, createdBy, creationDate, modifyBy, modifyDate, userRoleName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null, null, rs);
        }

        return user;
    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public int updateUser(User user) {
        String sql="UPDATE smbms_user SET userName=?,gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id=?";
        Object[] params={user.getUserName(),user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getModifyBy(),user.getModifyDate(),user.getId()};
        return this.executeUpdate(sql,params);
    }
}
