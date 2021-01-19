package com.chengq.chengq.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.chengq.chengq.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AccountMapper extends BaseMapper<AccountEntity> {


}