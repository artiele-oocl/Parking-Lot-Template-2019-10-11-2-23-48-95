package com.thoughtworks.parking_lot.handler;

import com.thoughtworks.parking_lot.error.CustomError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public CustomError NotFoundExceptionHandler(NotFoundException e) {
        CustomError customError = new CustomError();
        customError.setErrCode(HttpStatus.NOT_FOUND.value());
        customError.setErrMessage(e.getMessage());
        return customError;
    }
}
