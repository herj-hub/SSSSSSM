package com.smbms.service.provider;

import com.smbms.entity.Provider;

import java.util.List;

public interface ProviderService {

    /**
     * 统计供应商数量
     * @param proCode
     * @param proName
     * @return
     */
    int countProvider(String proCode, String proName);

    /**
     * 查询供应商列表
     * @param proCode
     * @param proName
     * @param startNo
     * @param pageSize
     * @return
     */
    List<Provider> queryProvider(String proCode, String proName, int startNo, int pageSize);

    /**
     * 新增供应商
     * @param provider
     * @return
     */
    int addProvider(Provider provider);

    /**
     * 根据id删除供应商
     * @param providerId
     * @return
     */
    int deleteProviderById(int providerId);

    /**
     * 根据id查询供应商
     * @param id
     * @return
     */
    Provider queryById(int id);

    /**
     * 更新供应商
     * @param provider
     * @return
     */
    int updateProvider(Provider provider);

    /**
     * 订单里面查看供应商列表
     * @return
     */
    List<Provider> queryProvider();
}
