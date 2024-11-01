package com.smbms.util;

/**
 * 分页工具对象
 */
public class PageUtil {

    //当前页码，默认值是：1
    private int currentPage = 1;
    //总数据量
    private int total = 0;
    //总页数= 总数据量/每页显示数据量 或者 总数量/每页显示数据量+1
    private int totalPage = 0;
    //分页参数：起始偏移量 = (当前页码-1)*每页显示数据量
    private int startNo = 0;
    //分页参数：每页显示数据量，默认值是：5
    private int pageSize = 5;

    /**
     * 分页构造器
     * @param currentPage 当前页码
     * @param total 总数据量
     * @param pageSize 每页显示数据量
     */
    public PageUtil(int currentPage, int total, int pageSize) {
        if(currentPage<1){//避免向前超过第一页，负值问题
            currentPage = 1;
        }
        if(total<0){
            total = 0;
        }
        if(pageSize<0){
            pageSize = 5;
        }

        //计算总页数
        if(total%pageSize==0){
            this.totalPage = total/pageSize;
        }else{
            this.totalPage = total/pageSize+1;
        }
        if(currentPage>totalPage && totalPage!=0){//避免向后超过最后一页
            currentPage = totalPage;
        }
        //设置当前页码
        this.currentPage = currentPage;
        //设置起始偏移量
        this.startNo = (currentPage-1)*pageSize;
        //设置每页显示数据量
        this.pageSize = pageSize;
        //设置总数据量
        this.total = total;

    }
    //只有get方法
    public int getCurrentPage() {
        return currentPage;
    }

    public int getTotal() {
        return total;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getStartNo() {
        return startNo;
    }

    public int getPageSize() {
        return pageSize;
    }
}
