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
public class RetryHelper<T> {

    private Integer maxRetries;//最大重试次数
    private Long sleepSecond;//设置执行间隔睡眠

    public static RetryHelper init(Integer maxRetries, Long sleepSecond) {
        return new RetryHelper().setSleepSecond(sleepSecond).setMaxRetries(maxRetries);
    }

    /**
     * 判断异常即可以重试，有返回值
     * 如果不异常默认为执行成功
     *
     * @author ChengQ
     * @date 2023-06-14 10:40:44
     */
    public Map<Boolean, T> doTaskWithRetry(WorkResult<T> work) {
        int retryCount = 0;
        boolean success = false;
        T t = null;
        while (!success && retryCount < maxRetries) {
            try {
                // 执行任务的逻辑
                t = work.startResult();
                success = true; // 标记任务执行成功
            } catch (Exception e) {
                retryCount++;
                if (retryCount < maxRetries) {
                    if (sleepSecond != null && sleepSecond.longValue() > 0)
                        // 休眠一段时间后再次尝试
                        sleepBeforeRetry();
                }
            }
        }
        Map<Boolean, T> map = new HashMap<>();
        map.put(success, t);
        return map;
    }

    /**
     * 判断异常即可以重试，无返回值
     * 如果不异常默认为执行成功
     *
     * @author ChengQ
     * @date 2023-06-14 10:40:57
     */
    public Boolean doTaskWithRetry(Work work) {
        int retryCount = 0;
        boolean success = false;
        while (!success && retryCount < maxRetries) {
            try {
                // 执行任务的逻辑
                work.start();
                success = true; // 标记任务执行成功
            } catch (Exception e) {
                retryCount++;
                if (retryCount < maxRetries) {
                    if (sleepSecond != null && sleepSecond.longValue() > 0)
                        // 休眠一段时间后再次尝试
                        sleepBeforeRetry();
                }
            }
        }
        return success;
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
        ResT startResult();
    }

    @FunctionalInterface
    public interface Work {
        void start();
    }
}
