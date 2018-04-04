package com.mt.order.common.model;

public class ComputeProd {
    private Long id;

    private Long computeId;

    private String cstMoldNo;

    private String cstProdName;

    private String cstProdNo;

    private Integer cavityTotal;

    private String prodModelPic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getComputeId() {
        return computeId;
    }

    public void setComputeId(Long computeId) {
        this.computeId = computeId;
    }

    public String getCstMoldNo() {
        return cstMoldNo;
    }

    public void setCstMoldNo(String cstMoldNo) {
        this.cstMoldNo = cstMoldNo == null ? null : cstMoldNo.trim();
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
}