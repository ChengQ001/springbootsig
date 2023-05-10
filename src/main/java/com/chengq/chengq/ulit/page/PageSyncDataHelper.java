package com.chengq.chengq.ulit.page;

import com.chengq.chengq.exception.ServiceException;
import com.chengq.chengq.ulit.ToolUtil.ToolUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.concurrent.CompletableFuture;

/**
 * 封装分页取数据工具类
 */
@Accessors(chain = true)
@Data
public class PageSyncDataHelper {

    private Integer pageIndex;// [分页第几页]
    private Integer pageSize;//参数必传[分页每页数]
    private Integer totalRows;//参数必传[总条数 不能传0]

    public static PageSyncDataHelper build(Integer pageIndex, Integer pageSize, Integer totalRows) {
        PageSyncDataHelper pageSyncDataHelper = new PageSyncDataHelper();
        pageSyncDataHelper.pageIndex = ToolUtil.isEmpty(pageIndex) ? 0 : pageIndex;
        pageSyncDataHelper.pageSize = ToolUtil.isEmpty(pageSize) ? 100 : pageSize;
        if (ToolUtil.isEmpty(totalRows) || totalRows <= 0) {
            throw new ServiceException("100", "totalRows参数不能为空,或者小于等于0");
        }
        pageSyncDataHelper.totalRows = totalRows;
        return pageSyncDataHelper;
    }

    public void start(PageWork pageWork) {
        Integer i = totalRows / pageSize;
        Integer remainder = totalRows % pageSize;
        i = remainder > 0 ? i + 1 : i;
        for (int j = pageIndex; j <= i; j++) {
            pageWork.listData(j, pageSize);
        }
    }

    public void syncStart(PageWork pageWork) {
        CompletableFuture.runAsync(() -> start(pageWork));
    }

    @FunctionalInterface
    public interface PageWork {
        void listData(Integer pageIndex, Integer pageSize);
    }
}
