package com.smbms.dao.role;

import com.smbms.entity.Role;

import java.util.List;

public interface RoleDao {
    /**
     * 查询所有角色
     * @return
     */
    List<Role> queryRole();
}
