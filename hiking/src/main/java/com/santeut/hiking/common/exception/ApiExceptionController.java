package com.santeut.hiking.common.exception;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.santeut.hiking.common.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;


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
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse illegalExHandle(JsonProcessingException  e) {
        return constructErrorResponse(e,HttpStatus.BAD_REQUEST, "JsonProcessingException ");
    }

    //객체가 json으로 변환이 안되는 오류
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
        return constructErrorResponse(e,HttpStatus.FORBIDDEN, "handleAccessDeniedException");
    }

    // Fegin실패 요청
    @ExceptionHandler(FeginFailerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFeginFailerException(FeginFailerException e) {
        return constructErrorResponse(e,HttpStatus.FORBIDDEN, "handleFeginFailerException");
    }


    private ErrorResponse constructErrorResponse(Exception e, HttpStatus status, String errorType) {
        log.error("[exceptionHandle] ex={}", e.getMessage(), e);
        return new ErrorResponse(status.value(), errorType, e.getMessage());
    }
}
