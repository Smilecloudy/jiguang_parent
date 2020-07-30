package com.it.yanxuan.seller.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.it.yanxuan.mapper.AccountMapper;
import com.it.yanxuan.mapper.SellerShopMapper;
import com.it.yanxuan.model.Account;
import com.it.yanxuan.model.SellerShop;
import com.it.yanxuan.model.SellerShopExample;
import com.it.yanxuan.model.com.it.yanxuan.viewmodel.SellerInfo;
import com.it.yanxuan.result.PageResult;
import com.it.yanxuan.seller.api.ISellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @auther: 曹云博
 * @create: 2020-07-2020/7/7 12:58
 */
@Service
@Transactional
public class SellerInfoServiceImpl implements ISellerInfoService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private SellerShopMapper sellerShopMapper;

    //保存商家入驻信息
    @Override
    public int save(SellerInfo sellerInfo) {

        //因为在表seller_shop中有一个account_id，所以先创建一个Account对象
        Account account = new Account();
        account.setLoginName(sellerInfo.getLoginName());
        //密码加密器
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String bcPassword = passwordEncoder.encode(sellerInfo.getPassword());
        account.setPassword(bcPassword);
        account.setPhone(sellerInfo.getLinkmanPhone());
        account.setEmail(sellerInfo.getLinkmanEmail());
        account.setStatus("0");
        account.setCreateDate(new Date());
        //保存account到account表中
        accountMapper.insert(account);

        //在seller_shop表中，只需要保存account表中的id到account_id列中
        sellerInfo.setAccountId(account.getId());
        /*
        状态：
            0-待审核；
            1-审核通过；
            2-审核退回；
            3-已关闭
            商家刚提交入驻信息，设置成“待审核”
         */
        sellerInfo.setStatus("0");

        //将sellerInfo保存到seller_shop中
        int result = sellerShopMapper.insert(sellerInfo);
        return result;
    }


    @Override
    public PageResult<SellerShop> query(Integer currentPage, Integer pageSize, SellerInfo sellerInfo) {
        //构建查询条件
        SellerShopExample sellerShopExample = new SellerShopExample();
        SellerShopExample.Criteria criteria = sellerShopExample.or();
        if (sellerInfo != null) {
            if (sellerInfo.getName() != null && !"".equals(sellerInfo.getName())) {
                criteria.andNameLike("%" + sellerInfo.getName() + "%");
            }
            if (sellerInfo.getStatus() != null && !"".equals(sellerInfo.getStatus())) {
                criteria.andStatusEqualTo(sellerInfo.getStatus());
            }
        }
        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        //根据条件查询并分页
        Page<SellerShop> pageData = (Page<SellerShop>) sellerShopMapper.selectByExample(sellerShopExample);
        //构建返回结果
        PageResult<SellerShop> pageResult = new PageResult<>();
        pageResult.setResult(pageData.getResult());
        pageResult.setTotal(pageData.getTotal());
        return pageResult;
    }

    //根据id修改状态码
    @Override
    public int update(SellerShop sellerShop) {
        int result = sellerShopMapper.updateByPrimaryKey(sellerShop);
        return result;
    }
}
