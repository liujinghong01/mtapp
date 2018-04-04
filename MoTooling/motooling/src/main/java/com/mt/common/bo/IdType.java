package com.mt.common.bo;


/**
 * 定义生成系统中业务Id的类型：如用户，公司
 *
 */

/**
* @Author: Fhk
* @Description:
* @create    2017/11/25 13:20
* @Modified By:
*/
public enum IdType {
    /** 用户 */
    USER("001"),

    /** 公司 */
    COMPANY("002"),

    /** 部门 */
    DEPARTMENT("003"),

    /** 供应商 */
    SUPPLIER("004"),

    /** 用户 */
    CUSTOMER("005");

    private final String value;

    IdType(String value) {
        this.value = value;
    }

    /**
     * Return the integer value of this status code.
     */
    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}

