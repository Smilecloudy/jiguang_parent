package com.it.yanxuan.seller.service;

import com.it.yanxuan.model.Account;
import com.it.yanxuan.seller.api.IAccountService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 16:49
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    //远程服务
    private IAccountService accountService;

    public void setAccountService(IAccountService accountService) {
        this.accountService = accountService;
    }

    //根据登录名加载User
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=============username==============="+username);

        //根据登录名查询Account信息
        Account account = accountService.queryByUsername(username);
        //如果查询不到就返回null
        if (account == null) {
            return null;
        }

        //创建权限
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new User(account.getLoginName(), account.getPassword(), list);
    }
}
