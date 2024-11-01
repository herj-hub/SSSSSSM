package com.smbms.dao.bill;

import com.smbms.entity.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillMapper {
    /**
     * 统计订单下的供应商个数
     * @param providerId
     * @return
     */
    int countProvider(int providerId);

    /**
     * 统计账单数量
     * @param productName
     * @param providerId
     * @param isPayment
     * @return
     */
    int countBill(@Param("productName") String productName,
                  @Param("providerId") Integer providerId,
                  @Param("isPayment") Integer isPayment);

    /**
     * 查询订单列表
     * @param productName
     * @param providerId
     * @param isPayment
     * @param startNo
     * @param pageSize
     * @return
     */
    List<Bill> queryBill(@Param("productName") String productName,
                         @Param("providerId") Integer providerId,
                         @Param("isPayment") Integer isPayment,
                         @Param("startNo") int startNo,
                         @Param("pageSize") int pageSize);

    /**
     * 新增订单
     * @param bill
     * @return
     */
    int addBill(Bill bill);

    /**
     * 根据id删除订单
     * @param id
     * @return
     */
    int deleteBillId(int id);


    /**
     * 根据id查看订单
     * @param id
     * @return
     */
    Bill queryById(int id);

    /**
     * 更新订单
     * @param bill
     * @return
     */
    int updateBill(Bill bill);
}
