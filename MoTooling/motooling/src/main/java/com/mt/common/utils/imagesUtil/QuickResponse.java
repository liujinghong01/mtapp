package com.mt.common.utils.imagesUtil;


import com.google.zxing.WriterException;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Alnwick
 * @Date: create in 2018-01-31   13:23
 * @Description:    二维码 基类，
 * @Modified By:
 */
public class QuickResponse {
    protected final Logger log = Logger.getLogger(QuickResponse.class);
    // 单子类型； BillTypeEnum 查看
    protected String typeName;
    // 跳转url
    protected String url;
    // 生成二维码时间
    protected String date;
    // 单子唯一id
    protected int  id;
    // 单号
    protected String  billNo;

    public InputStream createQRCode(QuickResponse param) {
        try {
            return ZXingUtils.getQRCodeInputSterm(JSONObject.fromObject(param).toString(),0,0);
        } catch (IOException e) {
            e.printStackTrace();
             log.error(e.getMessage());
        } catch (WriterException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return null;
    }

    public QuickResponse(){};

    public QuickResponse(String typeName, String url, String date, int id, String billNo) {
        this.typeName = typeName;
        this.url = url;
        this.date = date;
        this.id = id;
        this.billNo = billNo;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
}
