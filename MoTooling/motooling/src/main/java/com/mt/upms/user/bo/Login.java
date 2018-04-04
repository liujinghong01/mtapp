package com.mt.upms.user.bo;

import com.mt.upms.common.model.User;

import java.io.Serializable;

/**
 * Created by lrd on 2017/8/31.
 */
public class Login implements Serializable {

    private User user;
    private Boolean rememberMe;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }
    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

}
