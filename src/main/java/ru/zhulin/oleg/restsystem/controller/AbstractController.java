package ru.zhulin.oleg.restsystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.zhulin.oleg.restsystem.model.ParentEntity;
import ru.zhulin.oleg.restsystem.validation.RestSystemValidationError;

public class AbstractController<T extends ParentEntity>{
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestSystemValidationError handleException(Exception exception){
        return new RestSystemValidationError(exception.getMessage());
    }
}
