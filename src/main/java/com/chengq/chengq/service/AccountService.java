package com.chengq.chengq.service;


import cn.stylefeng.roses.core.page.PageFactory;
import cn.stylefeng.roses.core.page.PageResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengq.chengq.entity.AccountEntity;
import com.chengq.chengq.mapper.AccountMapper;
import com.chengq.chengq.model.account.AccountQuery;
import org.springframework.stereotype.Service;


@Service
public class AccountService extends ServiceImpl<AccountMapper, AccountEntity> {
    public AccountEntity getModel(Integer id) {
        return this.baseMapper.getModel(id);
    }


    public PageResult<AccountEntity> getPage(AccountQuery query) {
        query.setOrderByField("id");
        query.setSort("desc");
        IPage<AccountEntity> page = PageFactory.createPage(query);
        this.page(page, query.buildWrapper(AccountEntity.class));
        PageResult<AccountEntity> pageResult = new PageResult<>(page.getRecords(), page.getCurrent(), page.getSize(),
                page.getTotal(), page.getPages());
        return pageResult;
    }

}
