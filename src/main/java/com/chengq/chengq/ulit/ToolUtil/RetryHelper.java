package com.chengq.chengq.ulit.ToolUtil;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 重试执行器工具类
 *
 * @author ChengQ
 * @date 2023-06-14 10:28:17
 */
@Accessors(chain = true)
@Data
public class RetryHelper {

    private Integer maxRetries;//最大重试次数
    private Long sleepSecond;//设置执行间隔睡眠

    public static RetryHelper init(Integer maxRetries) {
        if (ToolUtil.isEmpty(maxRetries)) {
            maxRetries = 1;
        }
        return new RetryHelper().setMaxRetries(maxRetries);
    }

    /**
     * 执行任务的逻辑，必须返回是否成功标识，否则默认为未成功
     * Map<Boolean, T> Boolean是否成功，T 泛型返回值
     *
     * @author ChengQ
     * @date 2023-06-14 10:40:44
     */
    public <T> Map<Boolean, T> doTaskWithRetry(WorkResult work) {
        int retryCount = 0;
        boolean success = false;
        Map<Boolean, T> booleanTMap = null;
        while (!success && retryCount < maxRetries) {
            try {
                booleanTMap = work.startResult();
                if (ToolUtil.isEmpty(booleanTMap)) {
                    booleanTMap = new HashMap<>();
                    booleanTMap.put(false, null);
                }
            } catch (Exception exception) {
                booleanTMap = new HashMap<>();
                booleanTMap.put(false, null);
            }
            if (ToolUtil.isNotEmpty(booleanTMap.keySet())) {
                Boolean next = booleanTMap.keySet().iterator().next();
                success = next;
            }
            if (!success) {
                retryCount++;
                if (retryCount < maxRetries) {
                    if (sleepSecond != null && sleepSecond.longValue() > 0)
                        // 休眠一段时间后再次尝试
                        sleepBeforeRetry();
                }
            }
        }
        return booleanTMap;
    }


    private void sleepBeforeRetry() {
        //设置休眠时间
        try {
            Thread.sleep(sleepSecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FunctionalInterface
    public interface WorkResult<ResT> {
        Map<Boolean, ResT> startResult();
    }
}
