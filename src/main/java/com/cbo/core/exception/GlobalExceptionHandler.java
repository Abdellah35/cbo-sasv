package com.cbo.core.exception;

import com.cbo.core.model.ErrorResponse;
import com.cbo.core.response.ImageUploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ImageUploadResponse<Object>>
    handleBindException(BindException bindException){

        Map<String,String> errorMap=new HashMap<>();

        bindException.getAllErrors().stream().
                forEach(objectError -> {
                    errorMap.put(
                            ((FieldError)objectError).getField(),
                            objectError.getDefaultMessage());
                });

        return new ResponseEntity(
                ImageUploadResponse.builder()
                        .response(errorMap.toString())
                        .message("Object validation failed")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value
            = NoSuchUserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse
    handleException(NoSuchUserExistsException ex)
    {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value
            = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse
    handleException(ResourceNotFoundException ex)
    {
        return new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(value
            = UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public @ResponseBody ErrorResponse
    handleException(UserAlreadyExistsException ex)
    {
        return new ErrorResponse(
                HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    @ExceptionHandler(value
            = ImageNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse
    handleException(ImageNotFoundException ex)
    {
        return new ErrorResponse(
                HttpStatus.NO_CONTENT.value(), ex.getMessage());
    }
    @ExceptionHandler(value
            = IncorrectUsernameOrPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorResponse
    handleException(IncorrectUsernameOrPasswordException ex)
    {
        return new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    //Exception Handler
}