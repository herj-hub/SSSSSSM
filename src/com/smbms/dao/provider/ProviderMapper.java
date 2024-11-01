package com.smbms.dao.provider;

import com.smbms.entity.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {
    /**
     * 统计供应商数量
     * @param proCode
     * @param proName
     * @return
     */
    int countProvider(@Param("proCode") String proCode,@Param("proName") String proName);

    /**
     * 查询供应商列表
     * @param proCode
     * @param proName
     * @param startNo
     * @param pageSize
     * @return
     */
    List<Provider> queryProvider(@Param("proCode") String proCode,
                                 @Param("proName") String proName,
                                 @Param("startNo") int startNo,
                                 @Param("pageSize") int pageSize);



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
    //TODO 注意：这里的方法名要加个1，和上面的查询供应商列表方法区分，否则会报错
    List<Provider> queryProvider1();

}
