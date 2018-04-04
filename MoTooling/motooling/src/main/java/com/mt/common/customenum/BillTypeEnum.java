package com.mt.common.customenum;

/**
 * @Author: Alnwick
 * @Date: create in 2018-02-01   17:30
 * @Description:
 * @Modified By:
 */
public enum  BillTypeEnum {
    BA("报价核算单"),
    QT("报价单号"),
    CO("客户定单号"),
    PR("请购单号"),
    MI("物料询价单号"),
    AI("外协询价单号"),
    MP("物料采购定单号"),
    AP("外协采购定单号"),
    DA("部门领料单"),
    MA("模具领料单"),
    MS("下料单"),
    MC("盘点单"),
    SC("报废单"),
    PO("生产定单"),
    DM("送货单"),
    IR("询价反馈单"),
    CP("客户采购单"),
    MO("模号"),
    PZ("凭证单"),
    GX("基础工序"),
    IC("工序领取确认号"),
    PQ("工序质检单号"),
    MQ("来料质检单号");

    BillTypeEnum(String typeName) {
        this.typeName = typeName;
    }

    private String typeName;

}
