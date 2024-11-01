package com.smbms.dao.role;

import com.smbms.dao.BaseDao;
import com.smbms.entity.Role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoleDaoImpl extends BaseDao implements RoleDao {
    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Role> queryRole() {
        String sql="SELECT id,roleCode,roleName,createdBy,creationDate,modifyBy,modifyDate FROM smbms_role ;";
        Object[] params={};
        ResultSet rs=this.executeQuery(sql,params);

        List<Role> roleList=new ArrayList<>();

        try {
            while (rs.next()){
                long id=rs.getLong("id");
                String roleCode=rs.getString("roleCode");
                String roleName=rs.getString("roleName");
                long createBy=rs.getLong("createdBy");
                Date creationDate=rs.getDate("creationDate");
                long modifyBy=rs.getLong("modifyBy");
                Date modifyDate=rs.getDate("modifyDate");

                Role role=new Role(id,roleCode,roleName,createBy,creationDate,modifyBy,modifyDate);

                roleList.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return roleList;
    }
}
