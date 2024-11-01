package com.smbms.service.role;

import com.smbms.entity.Role;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有角色
     * @return
     */
    List<Role> queryRole();
}
