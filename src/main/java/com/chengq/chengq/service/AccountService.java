package com.chengq.chengq.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengq.chengq.Enums.CoreExceptionEnum;
import com.chengq.chengq.tools.ServiceException;
import com.chengq.chengq.entity.AccountEntity;
import com.chengq.chengq.mapper.AccountMapper;
import com.chengq.chengq.model.account.AccountQuery;
import com.chengq.chengq.ulit.PageListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountService extends ServiceImpl<AccountMapper, AccountEntity> {

    public PageListVO<AccountEntity> getMyPage(AccountQuery req) {
        Page<AccountEntity> page = new Page<>(req.getCurrentPage(), req.getPageSize());
        IPage<AccountEntity> res = this.baseMapper.getMyPage(page, req);
        if ( true) {
            log.error("异常啦");
            throw new ServiceException(CoreExceptionEnum.ALI_OSS_ERROR);
        }
        return new PageListVO<>(res.getRecords(), res.getCurrent(),
                res.getSize(),
                res.getTotal());
    }

    public AccountEntity getModel(Integer id) {
        return this.baseMapper.getModel(id);
    }

}
