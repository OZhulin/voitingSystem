package ru.zhulin.oleg.restsystem.validation;

import java.util.ArrayList;
import java.util.List;

public class RestSystemValidationError {
    private List<String> errors = new ArrayList<>();
    private final String errorMessage;

    public RestSystemValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addValidationError(String error){
        errors.add(error);
    }

    public  List<String> getErrors(){
        return errors;
    }

    public String getErrorMessage(){
        return errorMessage;
    }


}
