package com.mt.upms.common.model;

import com.mt.cms.common.model.Company;

public class UserCompany extends Company {

    //自定义
    private String  roleCode;
    public String getRoleCode() { return this.roleCode; }
    public void setRoleCode( String roleCode ) { this.roleCode = roleCode; }


}


