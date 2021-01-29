package com.chengq.chengq.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengq.chengq.entity.AccountEntity;
import com.chengq.chengq.mapper.AccountMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Service
public class AccountService extends ServiceImpl<AccountMapper, AccountEntity> {
    public AccountEntity getModel(Integer id) {
        return this.baseMapper.getModel(id);
    }



}
