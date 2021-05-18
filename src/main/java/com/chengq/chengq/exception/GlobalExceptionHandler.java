package com.chengq.chengq.exception;

import com.chengq.chengq.ulit.ResponseHelper;
import com.chengq.chengq.ulit.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
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


    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseModel handle401(ShiroException e) {
        return ResponseHelper.succeed("", e.getMessage(), "401");
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseModel handle401() {
        return ResponseHelper.succeed("", "Unauthorized", "401");
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseModel globalException(HttpServletRequest request, Throwable ex) {
        return ResponseHelper.succeed("", ex.getMessage(), String.valueOf(getStatus(request).value()));
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }


}
