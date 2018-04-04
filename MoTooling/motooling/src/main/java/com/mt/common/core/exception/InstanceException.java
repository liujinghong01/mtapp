/**
 * 
 */
package com.mt.common.core.exception;


import com.mt.common.core.support.HttpCode;

/**
 * 实例化异常
 *
 */
@SuppressWarnings("serial")
public class InstanceException extends BaseException {
    public InstanceException() {
        super();
    }

    public InstanceException(Throwable t) {
        super(t);
    }

    protected HttpCode getHttpCode() {
        return HttpCode.INTERNAL_SERVER_ERROR;
    }
}
