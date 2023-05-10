package com.chengq.chengq.businessmodule.service;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.EnumUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengq.chengq.Enums.ChengQEnums;
import com.chengq.chengq.Enums.CoreExceptionEnum;
import com.chengq.chengq.businessmodule.entity.AccountEntity;
import com.chengq.chengq.businessmodule.mapper.mapping.AccountMapper;
import com.chengq.chengq.exception.ServiceException;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.model.account.AccountResp;
import com.chengq.chengq.ulit.EnumUtil.EnumUtilEx;
import com.chengq.chengq.ulit.PageListVO;
import com.chengq.chengq.ulit.treenode.TreeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Slf4j
@EnableAsync
public class AccountService extends ServiceImpl<AccountMapper, AccountEntity> {

    public PageListVO<AccountEntity> getMyPage(AccountQuery req) {
        Page<AccountEntity> page = new Page<>(req.getPageIndex(), req.getPageSize());
        IPage<AccountEntity> res = this.baseMapper.getMyPage(page, req);

        if (Validator.isEmpty(req.getUsername())) {
            log.error("异常啦");
            throw new ServiceException(CoreExceptionEnum.ALI_OSS_ERROR);
        }
        return new PageListVO<>(res.getRecords(), res.getCurrent(),
                res.getSize(),
                res.getTotal());
    }

    public AccountResp getModel(Integer id) {
        AccountResp accountResp = BeanUtil.toBean(this.baseMapper.getModel(id), AccountResp.class);
        accountResp.setStatus(1);
        EnumUtilEx.init(ChengQEnums.class).getEnumListByName("StatusEnum");
        return accountResp;
    }

}
