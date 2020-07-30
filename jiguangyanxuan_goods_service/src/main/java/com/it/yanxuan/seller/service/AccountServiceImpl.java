package com.it.yanxuan.seller.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.it.yanxuan.mapper.AccountMapper;
import com.it.yanxuan.mapper.SellerShopMapper;
import com.it.yanxuan.model.Account;
import com.it.yanxuan.model.AccountExample;
import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.SellerShopExample;
import com.it.yanxuan.seller.api.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 17:13
 */
@Service
@Transactional
public class AccountServiceImpl implements IAccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private SellerShopMapper sellerShopMapper;


    @Override
    public Account queryByUsername(String username) {
         //创建查询条件
        // 查询登录者的账户信息
        AccountExample accountExample = new AccountExample();
        AccountExample.Criteria criteria = accountExample.createCriteria();
        criteria.andLoginNameEqualTo(username);
        List<Account> accounts = accountMapper.selectByExample(accountExample);
        // 查询账户是否存在关联的商铺信息
        for (Account account : accounts) {
            SellerShopExample example = new SellerShopExample();
            example.createCriteria().andAccountIdEqualTo(account.getId());
            List<SellerShop> sellerShops = sellerShopMapper.selectByExample(example);

            if(sellerShops!=null && sellerShops.size()>0){
                return account;
            }
        }
        return null;
    }
}
