package com.chengq.chengq.exception;

import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseModel bizExceptionHandler(ServiceException e) {
        log.error(e.getMessage(), e);
        return ResponseHelper.succeed(null, e.getErrorMessage(), e.getCode());
    }


    /**
     * 验证字段异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseModel validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder stringBuilder = new StringBuilder();
        fieldErrors.forEach(x -> {
            stringBuilder.append("【" + x.getField() + x.getDefaultMessage() + "】,");
        });
        return ResponseHelper.succeed(e.getParameter().getParameterType().getName(), stringBuilder.toString(),
                ((Integer) e.hashCode()).toString());
    }

}
