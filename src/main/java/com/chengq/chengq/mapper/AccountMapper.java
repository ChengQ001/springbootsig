package com.chengq.chengq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chengq.chengq.entity.AccountEntity;

import com.chengq.chengq.model.account.AccountQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;


public interface AccountMapper extends BaseMapper<AccountEntity> {
    AccountEntity getModel(Integer id);

    IPage getMyPage(IPage<AccountEntity> page, @Param("ew") AccountQuery username);
}
