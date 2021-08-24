package com.thatguyalex.vaccineconverter.presentation;

import com.thatguyalex.vaccineconverter.presentation.classes.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError statNotFoundExceptionHandler(RuntimeException e) {
        return new ApiError(e.getMessage());
    }
}
