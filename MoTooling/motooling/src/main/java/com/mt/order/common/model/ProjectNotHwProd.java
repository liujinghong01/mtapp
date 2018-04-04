package com.mt.order.common.model;

public class ProjectNotHwProd {
    private Long id;

    private Long projectNotHwId;

    private String cstProdName;

    private String cstProdNo;

    private Integer cavityTotal;

    private String prodModelPic;

    private Byte status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProjectNotHwId() {
        return projectNotHwId;
    }

    public void setProjectNotHwId(Long projectNotHwId) {
        this.projectNotHwId = projectNotHwId;
    }

    public String getCstProdName() {
        return cstProdName;
    }

    public void setCstProdName(String cstProdName) {
        this.cstProdName = cstProdName == null ? null : cstProdName.trim();
    }

    public String getCstProdNo() {
        return cstProdNo;
    }

    public void setCstProdNo(String cstProdNo) {
        this.cstProdNo = cstProdNo == null ? null : cstProdNo.trim();
    }

    public Integer getCavityTotal() {
        return cavityTotal;
    }

    public void setCavityTotal(Integer cavityTotal) {
        this.cavityTotal = cavityTotal;
    }

    public String getProdModelPic() {
        return prodModelPic;
    }

    public void setProdModelPic(String prodModelPic) {
        this.prodModelPic = prodModelPic == null ? null : prodModelPic.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}