package com.pranay.quartz.exception;


import com.pranay.quartz.models.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handleBadRequestException(BadRequestException exception) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), Boolean.FALSE);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ApiResponse> handleInternalServerException(InternalServerException exception) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DownStreamServerException.class)
    public ResponseEntity<ApiResponse> handleDownStreamServerException(DownStreamServerException exception) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), Boolean.FALSE);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
