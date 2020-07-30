package com.it.yanxuan.model.com.it.yanxuan.viewmodel;

import com.it.yanxuan.model.SellerShop;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/1 14:47
 */

/*
    封装商家入驻信息
 */
public class SellerInfo extends SellerShop {
    private String loginName;
    private String password;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
