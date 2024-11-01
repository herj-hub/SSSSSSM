package com.smbms.dao.bill;

import com.smbms.dao.BaseDao;
import com.smbms.entity.Bill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BillDaoImpl extends BaseDao implements BillDao {


    /**
     * 统计订单下的供应商个数
     *
     * @param providerId
     * @return
     */
    @Override
    public int countProvider(int providerId) {
        String sql="select count(1) as num from smbms_bill where providerId=?";
        Object[] params={providerId};
        ResultSet rs = this.executeQuery(sql, params);
        int num=0;
        try {
            while (rs.next()){
                num=rs.getInt("num");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return num;
    }

    /**
     * 统计账单数量
     *
     * @param productName
     * @param providerId
     * @param isPayment
     * @return
     */
    @Override
    public int countBill(String productName, Integer providerId, Integer isPayment) {
        String sql="select count(1) AS num from smbms_bill where 1=1 ";
        if(productName!=null){
            sql+=" AND productName LIKE '%"+productName+"%'";
        }
        if (providerId != null) {
            sql += " AND providerId =" +providerId;
        }
        if (isPayment != null) {
            sql += " AND isPayment =" +isPayment;
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
     * 查询订单列表
     *
     * @param productName
     * @param providerId
     * @param isPayment
     * @param startNo
     * @param pageSize
     * @return
     */
    @Override
    public List<Bill> queryBill(String productName, Integer providerId, Integer isPayment, int startNo, int pageSize) {
        String sql = "SELECT bill.id,bill.billCode,bill.productName,bill.productDesc,bill.productUnit,bill.productCount,bill.totalPrice,bill.isPayment,bill.createdBy,bill.creationDate,bill.modifyBy,bill.modifyDate,bill.`providerId`,provider.proName FROM smbms_bill bill INNER JOIN smbms_provider provider ON bill.`providerId` = provider.`id`";

        sql+=" where 1=1";

        if(productName!=null){
            sql+=" AND productName LIKE '%"+productName+"%'";
        }
        if (providerId != null) {
            sql += " AND providerId =" +providerId;
        }
        if (isPayment != null) {
            sql += " AND isPayment =" +isPayment;
        }

        sql += " ORDER BY bill.id DESC";
        //分页语句
        sql += " LIMIT ?,? ";

        System.out.println("拼接的语句是：" + sql);
        Object[] params = {startNo, pageSize};

        ResultSet rs = this.executeQuery(sql, params);
        List<Bill> billList=new ArrayList<>();
        try {
            while (rs.next()){
                int id=rs.getInt("id");
                String billCode= rs.getString("billCode");
                String productName1= rs.getString("productName");
                String productDesc= rs.getString("productDesc");
                String productUnit= rs.getString("productUnit");
                double  productCount= rs.getDouble("productCount");
                double  totalPrice= rs.getDouble("totalPrice");
                int isPayment1=rs.getInt("isPayment");
                int createdBy=rs.getInt("createdBy");
                Date creationDate=rs.getDate("creationDate");
                int modifyBy=rs.getInt("modifyBy");
                Date modifyDate=rs.getDate("modifyDate");
                int providerId1=rs.getInt("providerId");
                String providerName=rs.getString("proName");

                Bill bill=new Bill(id,billCode,productName1,productDesc,productUnit,productCount,totalPrice,isPayment1,providerId1,createdBy,creationDate,modifyBy,modifyDate,providerName);

                billList.add(bill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return billList;
    }

    /**
     * 新增订单
     *
     * @param bill
     * @return
     */
    @Override
    public int addBill(Bill bill) {
        String sql = "INSERT INTO smbms_bill (billCode,productName,productUnit,productCount,totalPrice,isPayment,createdBy,creationDate,providerId) VALUES(?,?,?,?,?,?,?,?,?);";
        Object[] params = {bill.getBillCode(), bill.getProductName(), bill.getProductUnit(), bill.getProductCount(), bill.getTotalPrice(), bill.getIsPayment(), bill.getCreatedBy(), bill.getCreationDate(),bill.getProviderId()};
        return this.executeUpdate(sql,params);
    }

    /**
     * 根据id删除订单
     *
     * @param id
     * @return
     */
    @Override
    public int deleteBillId(int id) {
        String sql="delete from smbms_bill where id=?";
        Object[] params={id};
        return this.executeUpdate(sql,params);
    }

    /**
     * 根据id查看订单
     *
     * @param id
     * @return
     */
    @Override
    public Bill queryById(int id) {
        String sql = "SELECT bill.id,bill.billCode,bill.productName,bill.productDesc,bill.productUnit,bill.productCount,bill.totalPrice,bill.isPayment,bill.createdBy,bill.creationDate,bill.modifyBy,bill.modifyDate,bill.`providerId`,provider.proName FROM smbms_bill bill INNER JOIN smbms_provider provider ON bill.`providerId` = provider.`id` where bill.id=?";
        Object[] params={id};
        ResultSet rs=this.executeQuery(sql,params);

        Bill bill=null;
        try {
            while (rs.next()){
                int id1=rs.getInt("id");
                String billCode= rs.getString("billCode");
                String productName1= rs.getString("productName");
                String productDesc= rs.getString("productDesc");
                String productUnit= rs.getString("productUnit");
                double  productCount= rs.getDouble("productCount");
                double  totalPrice= rs.getDouble("totalPrice");
                int isPayment1=rs.getInt("isPayment");
                int createdBy=rs.getInt("createdBy");
                Date creationDate=rs.getDate("creationDate");
                int modifyBy=rs.getInt("modifyBy");
                Date modifyDate=rs.getDate("modifyDate");
                int providerId1=rs.getInt("providerId");
                String providerName=rs.getString("proName");

                bill=new Bill(id1,billCode,productName1,productDesc,productUnit,productCount,totalPrice,isPayment1,providerId1,createdBy,creationDate,modifyBy,modifyDate,providerName);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeAll(null,null,rs);
        }

        return bill;
    }

    /**
     * 更新订单
     *
     * @param bill
     * @return
     */
    @Override
    public int updateBill(Bill bill) {
        String sql = "UPDATE smbms_bill SET billCode=?,productName=?,productUnit=?,productCount=?,totalPrice=?,isPayment=?,modifyBy=?,modifyDate=? WHERE id=?";
        Object[] params={bill.getBillCode(),bill.getProductName(),bill.getProductUnit(),bill.getProductCount(),bill.getTotalPrice(),bill.getIsPayment(),bill.getModifyBy(),bill.getModifyDate(),bill.getId()};
        return this.executeUpdate(sql,params);
    }
}
