package com.mt.common.base;

/**
 * @Author:Wujie
 * @Date: Create in 2018-03-14 13:47
 * @Description:
 * @Modified By:
 */
public class BasePojo {

    //终端类型
    private Integer clientType;
    //公司id
    private Integer companyId;
    //token
    private String token;
    //用户id
    private Integer uid;
    //当前页
    private Integer currPage;
    //每页显示条数
    private Integer pageSize;
    //当前页码数
    private Integer page;

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
