package com.smbms.dao.provider;

import com.smbms.dao.BaseDao;
import com.smbms.entity.Provider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProviderDaoImpl extends BaseDao implements ProviderDao {

    /**
     * 统计供应商数量
     *
     * @param proCode
     * @param proName
     * @return
     */
    @Override
    public int countProvider(String proCode, String proName) {
        String sql="SELECT COUNT(1) AS num FROM smbms_provider where 1=1 ";
        if(proCode!=null){
            sql+=" and proCode LIKE '%"+proCode+"%'";
        }
        if(proName!=null){
            sql+=" and proName LIKE '%"+proName+"%'";
        }

        System.out.println("统计供应商数量，拼接SQL:"+sql);

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
     * 查询供应商列表
     *
     * @param proCode
     * @param proName
     * @param startNo
     * @param pageSize
     * @return
     */
    @Override
    public List<Provider> queryProvider(String proCode, String proName, int startNo, int pageSize) {
        String sql = "select id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate from smbms_provider ";
        sql += " where 1=1 ";

        if(proCode!=null){
            sql+=" and proCode LIKE '%"+proCode+"%'";
        }
        if(proName!=null){
            sql+=" and proName LIKE '%"+proName+"%'";
        }

        sql+="order by id desc";
        //分页语句
        sql += " LIMIT ?,? ";

        System.out.println("拼接的语句是:"+sql);
        Object[] params={startNo,pageSize};

        ResultSet rs=this.executeQuery(sql,params);

        List<Provider> providerList=new ArrayList<>();

        try {
            while (rs.next()){
                int id=rs.getInt("id");
                String proCode1=rs.getString("proCode");
                String proName1=rs.getString("proName");
                String proDesc=rs.getString("proDesc");
                String proContact=rs.getString("proContact");
                String proPhone=rs.getString("proPhone");
                String proAddress=rs.getString("proAddress");
                String proFax=rs.getString("proFax");
                int createdBy=rs.getInt("createdBy");
                Date creationDate=rs.getDate("creationDate");
                int modifyBy=rs.getInt("modifyBy");
                Date modifyDate= rs.getDate("modifyDate");

                Provider provider=new Provider(id,proCode1,proName1,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate);
                providerList.add(provider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return providerList;

    }

    /**
     * 新增供应商
     *
     * @param provider
     * @return
     */
    @Override
    public int addProvider(Provider provider) {
        String sql = "INSERT INTO smbms_provider (proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate) VALUES(?,?,?,?,?,?,?,?,?)";
        Object[] params={provider.getProCode(),provider.getProName(),provider.getProDesc(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getCreatedBy(),provider.getCreationDate()};
        return this.executeUpdate(sql,params);
    }

    /**
     * 根据id删除供应商
     *
     * @param providerId
     * @return
     */
    @Override
    public int deleteProviderById(int providerId) {
        String sql="DELETE FROM smbms_provider WHERE id=?";
        Object[] params={providerId};
        return this.executeUpdate(sql,params);
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public Provider queryById(int id) {
        String sql = "select id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate from smbms_provider WHERE id=?";
        Object[] params={id};
        ResultSet rs=this.executeQuery(sql,params);

        Provider provider=null;

        try {
            while (rs.next()){
                int id1=rs.getInt("id");
                String proCode1=rs.getString("proCode");
                String proName1=rs.getString("proName");
                String proDesc=rs.getString("proDesc");
                String proContact=rs.getString("proContact");
                String proPhone=rs.getString("proPhone");
                String proAddress=rs.getString("proAddress");
                String proFax=rs.getString("proFax");
                int createdBy=rs.getInt("createdBy");
                Date creationDate=rs.getDate("creationDate");
                int modifyBy=rs.getInt("modifyBy");
                Date modifyDate= rs.getDate("modifyDate");

                provider=new Provider(id,proCode1,proName1,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return provider;
    }

    /**
     * 更新供应商
     *
     * @param provider
     * @return
     */
    @Override
    public int updateProvider(Provider provider) {
        String sql="UPDATE smbms_provider SET proCode=?,proName=?,proContact=?,proPhone=?,proAddress=?,proFax=?,proDesc=?,modifyBy=?,modifyDate=? WHERE id=?";
        Object[] params={provider.getProCode(),provider.getProName(),provider.getProContact(),provider.getProPhone(),provider.getProAddress(),provider.getProFax(),provider.getProDesc(),provider.getModifyBy(),provider.getModifyDate(),provider.getId()};

        return this.executeUpdate(sql,params);
    }

    /**
     * 订单里查看供应商列表
     *
     * @return
     */
    @Override
    public List<Provider> queryProvider() {
        String sql="select id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate from smbms_provider;";
        Object[] params={};
        ResultSet rs=this.executeQuery(sql,params);

        List<Provider> providerList=new ArrayList<>();

        try {
            while (rs.next()){
                int id=rs.getInt("id");
                String proCode=rs.getString("proCode");
                String proName=rs.getString("proName");
                String proDesc=rs.getString("proDesc");
                String proContact=rs.getString("proContact");
                String proPhone=rs.getString("proPhone");
                String proAddress=rs.getString("proAddress");
                String proFax=rs.getString("proFax");
                int createdBy=rs.getInt("createdBy");
                Date creationDate=rs.getDate("creationDate");
                int modifyBy=rs.getInt("modifyBy");
                Date modifyDate= rs.getDate("modifyDate");

                Provider provider=new Provider(id,proCode,proName,proDesc,proContact,proPhone,proAddress,proFax,createdBy,creationDate,modifyBy,modifyDate);
                providerList.add(provider);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return providerList;
    }
}
