package com.smbms.service.role;

import com.smbms.dao.role.RoleDao;
import com.smbms.dao.role.RoleDaoImpl;
import com.smbms.dao.role.RoleMapper;
import com.smbms.entity.Role;
import com.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class RoleServiceImpl implements RoleService {
    private RoleDao roleDao=null;

    public RoleServiceImpl(){
        roleDao=new RoleDaoImpl();
    }

    /**
     * 查询所有角色
     *
     * @return
     */
    @Override
    public List<Role> queryRole() {
        SqlSession sqlSession=null;
        sqlSession= MybatisUtil.createSqlSession();
        List<Role> roleList = sqlSession.getMapper(RoleMapper.class).queryRole();
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return roleList;
    }
}
