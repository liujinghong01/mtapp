package com.mt.common.customenum;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-17   10:45
 * @Description:
 * @Modified By:
 */
public enum RoleEnum {
    MT_ADMIN("平台admin",1,"mt_admin"),
    GROUP_ADMIN("集团admin",2,"group_admin"),
    ENTERPRISE_ADMIN("企业admin",3,"enterprise_admin"),
    DEPARTMENT_LEADER("部门主管（一级）",4,"department_leader"),
    DEPARTMENT_OPERATOR("部门操作员",5,"department_operator"),
    SUB_DEPARTMENT_LEADER("子部门主管或小组长（二级)",6,"sub_department_leader"),
    EMPLOYEE("普通员工",7,"employee"),
    PARTNER("企业外部协同人员",8,"partner");


    private int roldCode;
    private String name;
    private String code;



    RoleEnum() {
    }

    RoleEnum(String name ,int roldCode,String code) {
        this.roldCode = roldCode;
        this.name=name;
        this.code=code;
    }

    public int getRoldCode() {
        return roldCode;
    }

    public void setRoldCode(int roldCode) {
        this.roldCode = roldCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoldCode(int roldCode){
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    //获取小于自己权限的Map 集合
    public  List<Map<String,Object>> getRoleMap(int roldCode){
        List<Map<String,Object>> enumList = new  ArrayList(10);
        for (RoleEnum r :RoleEnum.values()){
            Map<String,Object> map = new LinkedHashMap<>(2);
            if(roldCode<r.getRoldCode()){
                map.put("code",r.getCode());
                map.put("name",r.getName());
                enumList.add(map);
            }
        }
        return enumList;
    }

}
