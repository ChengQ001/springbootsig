package com.chengq.businessmodule.mapper.mapping;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chengq.businessmodule.model.entity.AccountEntity;
import com.chengq.businessmodule.model.account.AccountQuery;
import org.apache.ibatis.annotations.Param;


public interface AccountMapper extends BaseMapper<AccountEntity> {
    AccountEntity getModel(Integer id);

    IPage getMyPage(IPage<AccountEntity> page, @Param("ew") AccountQuery username);
}
