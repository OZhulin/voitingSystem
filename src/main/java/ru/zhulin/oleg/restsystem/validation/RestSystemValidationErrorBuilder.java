package ru.zhulin.oleg.restsystem.validation;

import org.springframework.validation.Errors;

public class RestSystemValidationErrorBuilder {
    private static final String message = "Rest system validation failed. %s";
    public static RestSystemValidationError fromBindingErrors(Errors errors){
        RestSystemValidationError error = new RestSystemValidationError(String.format(message, errors.getErrorCount()));
        errors.getAllErrors().forEach(errorObject -> {
            error.addValidationError(errorObject.getDefaultMessage());
        });
        return error;
    }

}
