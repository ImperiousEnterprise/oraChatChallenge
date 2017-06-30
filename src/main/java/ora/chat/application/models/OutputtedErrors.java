package ora.chat.application.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MR.F on 6/30/2017.
 */
@Getter
@Setter
public class OutputtedErrors {
    private String message;
    private HashMap<String,ArrayList<String>> errors;

    public OutputtedErrors(String message){
        this.message = message;
        this.errors = new HashMap<String,ArrayList<String>>();;
    }

    public void addFieldError(String field, String errormessage) {
        if(errors.get(field) == null){
            errors.put(field, new ArrayList<String>());
            errors.get(field).add(errormessage);
        }else{
            errors.get(field).add(errormessage);
        }
    }
}
