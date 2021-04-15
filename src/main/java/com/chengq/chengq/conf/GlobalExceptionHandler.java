package com.chengq.chengq.conf;

import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import com.chengq.chengq.tools.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     *
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseModel bizExceptionHandler(ServiceException e) {
        log.error(e.getMessage(), e);

        return ResponseHelper.succeed(null,e.getErrorMessage(),e.getCode().toString());
    }
}
