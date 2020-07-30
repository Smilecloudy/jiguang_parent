package com.it.yanxuan.seller.api;

import com.it.yanxuan.model.Account;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 17:13
 */
public interface IAccountService {

    //通过用户输入的username查询数据库是否有username
    public Account queryByUsername(String username);
}
