package com.smbms.service.bill;

import com.smbms.dao.bill.BillDao;
import com.smbms.dao.bill.BillDaoImpl;
import com.smbms.dao.bill.BillMapper;
import com.smbms.entity.Bill;
import com.smbms.util.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class BillServiceImpl implements BillService {
    private BillDao billDao=null;

    public BillServiceImpl(){
        billDao=new BillDaoImpl();
    }


    private SqlSession sqlSession=null;
    /**
     * 统计订单下的供应商个数
     *
     * @param providerId
     * @return
     */
    @Override
    public int countProvider(int providerId) {
        sqlSession= MybatisUtil.createSqlSession();
        int num = sqlSession.getMapper(BillMapper.class).countProvider(providerId);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return billDao.countProvider(providerId);
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
        sqlSession= MybatisUtil.createSqlSession();
        int num = sqlSession.getMapper(BillMapper.class).countBill(productName, providerId, isPayment);
//        System.out.println(num);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return billDao.countBill(productName,providerId,isPayment);
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
        sqlSession= MybatisUtil.createSqlSession();
        List<Bill>  billList= sqlSession.getMapper(BillMapper.class).queryBill(productName,providerId,isPayment,startNo,pageSize);
//        System.out.println(num);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return billList;

//        return billDao.queryBill(productName,providerId,isPayment,startNo,pageSize);
    }

    /**
     * 新增订单
     *
     * @param bill
     * @return
     */
    @Override
    public int addBill(Bill bill) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(BillMapper.class).addBill(bill);
//        System.out.println(num);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return billDao.addBill(bill);
    }

    /**
     * 根据id删除订单
     *
     * @param id
     * @return
     */
    @Override
    public int deleteBillById(int id) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(BillMapper.class).deleteBillId(id);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return billDao.deleteBillId(id);
    }

    /**
     * 根据id查看订单
     *
     * @param id
     * @return
     */
    @Override
    public Bill queryById(int id) {
        sqlSession= MybatisUtil.createSqlSession();
        Bill bill= sqlSession.getMapper(BillMapper.class).queryById(id);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return bill;
//        return billDao.queryById(id);
    }

    /**
     * 更新订单
     *
     * @param bill
     * @return
     */
    @Override
    public int updateBill(Bill bill) {
        sqlSession= MybatisUtil.createSqlSession();
        int num= sqlSession.getMapper(BillMapper.class).updateBill(bill);
        sqlSession.commit();
        MybatisUtil.closeSqlSession(sqlSession);
        return num;
//        return billDao.updateBill(bill);
    }


}
