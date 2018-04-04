package com.mt.upms.common.model;

import java.util.ArrayList;
import java.util.List;

public class ChildModuleBeans {
    private  String name;

    private  List<ChildModuleBeans> childModule ;

    private  PermissionBeans  permission ;

    private  int id;

    private  String identity;

    private  int parentId;
    public ChildModuleBeans() {
        childModule = new ArrayList<>(10);
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildModuleBeans> getChildModule() {
        return childModule;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setChildModule(List<ChildModuleBeans> childModule) {
        this.childModule = childModule;
    }

    public  PermissionBeans  getPermission() {
        return permission;
    }

    public void setPermission(PermissionBeans  permission) {
        this.permission = permission;
    }
}
