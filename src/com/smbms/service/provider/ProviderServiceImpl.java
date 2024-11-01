package com.smbms.service.provider;

import com.smbms.dao.provider.ProviderDao;
import com.smbms.dao.provider.ProviderDaoImpl;
import com.smbms.dao.provider.ProviderMapper;
import com.smbms.entity.Provider;
import com.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ProviderServiceImpl implements ProviderService {
    private ProviderDao providerDao=null;

    public ProviderServiceImpl(){
        providerDao=new ProviderDaoImpl();
    }

    private SqlSession sqlSession=null;

    /**
     * 统计供应商数量
     *
     * @param proCode
     * @param proName
     * @return
     */
    @Override
    public int countProvider(String proCode, String proName) {
        sqlSession= MybatisUtil.createSqlSession();
        int num = sqlSession.getMapper(ProviderMapper.class).countProvider(proCode, proName);
//        System.out.println(num);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return providerDao.countProvider(proCode,proName);
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
        sqlSession= MybatisUtil.createSqlSession();
        List<Provider> providerList= sqlSession.getMapper(ProviderMapper.class).queryProvider(proCode,proName,startNo,pageSize);
//        for(Provider provider:providerList){
//            System.out.println(provider.getProName());
//        }
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return providerList;
//        return providerDao.queryProvider(proCode,proName,startNo,pageSize);
    }

    /**
     * 新增供应商
     *
     * @param provider
     * @return
     */
    @Override
    public int addProvider(Provider provider) {
        sqlSession= MybatisUtil.createSqlSession();
        int num = sqlSession.getMapper(ProviderMapper.class).addProvider(provider);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return providerDao.addProvider(provider);
    }

    /**
     * 根据id删除供应商
     *
     * @param providerId
     * @return
     */
    @Override
    public int deleteProviderById(int providerId) {
        sqlSession= MybatisUtil.createSqlSession();
        int num = sqlSession.getMapper(ProviderMapper.class).deleteProviderById(providerId);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return providerDao.deleteProviderById(providerId);
    }

    /**
     * 根据id查询供应商
     *
     * @param id
     * @return
     */
    @Override
    public Provider queryById(int id) {
        sqlSession= MybatisUtil.createSqlSession();
        Provider provider= sqlSession.getMapper(ProviderMapper.class).queryById(id);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return provider;
//        return providerDao.queryById(id);
    }

    /**
     * 更新供应商
     *
     * @param provider
     * @return
     */
    @Override
    public int updateProvider(Provider provider) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(ProviderMapper.class).updateProvider(provider);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return providerDao.updateProvider(provider);
    }

    /**
     * 订单里面查看供应商列表
     *
     * @return
     */
    @Override
    public List<Provider> queryProvider() {
        sqlSession= MybatisUtil.createSqlSession();
        List<Provider> providerList= sqlSession.getMapper(ProviderMapper.class).queryProvider1();
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return providerList;
//        return providerDao.queryProvider();
    }
}
