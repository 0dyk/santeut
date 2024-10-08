package com.santeut.guild.common.exception;

import com.santeut.guild.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;
@Slf4j
@RestControllerAdvice
public class ApiExceptionController {

    // 잘못된 인수값 전달
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalExHandle(IllegalArgumentException e) {
        return constructErrorResponse(e,HttpStatus.BAD_REQUEST,"IllegalArgumentException");
    }

    // 부적절한 객체상태오류
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalExHandle(IllegalStateException e) {
        return constructErrorResponse(e,HttpStatus.BAD_REQUEST, "IllegalStateException");
    }


    // 잘못된 요청 데이터
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        return constructErrorResponse(e,HttpStatus.BAD_REQUEST, "handleValidationExceptions");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponse responseStatusException(ResponseStatusException e) {
        return constructErrorResponse(e,HttpStatus.BAD_REQUEST, "ResponseStatusException");
    }

    // 리소스를 찾을 수 없음
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNoHandlerFoundException(NoHandlerFoundException e) {
        return constructErrorResponse(e,HttpStatus.NOT_FOUND, "handleNoHandlerFoundException");
    }

    // 권한없음(접근거부)
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        return constructErrorResponse(e,HttpStatus.FORBIDDEN, "AccessDeniedException");
    }

    // Open Feign 호출 에러
    @ExceptionHandler(FeignClientException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleAccessDeniedException(FeignClientException e) {
        return constructErrorResponse(e,HttpStatus.NOT_FOUND, "feignClientException");
    }
    // 알맞은 카테고리 찾지 못함
    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleAccessDeniedException(CategoryNotFoundException e) {
        return constructErrorResponse(e,HttpStatus.NOT_FOUND, "categoryNotFoundException");
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ErrorResponse CustomErrorResponse(DataNotFoundException e) {

        return constructErrorResponse2(e, e.getCode());
    }


    private ErrorResponse constructErrorResponse(Exception e, HttpStatus status, String errorType) {
        log.error("[exceptionHandle] ex={}", e.getMessage(), e);
        return new ErrorResponse(status.value(), errorType, e.getMessage());
    }

    private ErrorResponse constructErrorResponse2(Exception e, int status) {
        log.error("[exceptionHandle] ex={}", e.getMessage(), e);
        return new ErrorResponse(status, e.getMessage());
    }
}
