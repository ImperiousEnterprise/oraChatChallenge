package ora.chat.application.models.validations;

/**
 * Created by zstaff on 2017/06/30.
 */
import ora.chat.application.globalerrors.SamePasswords;
import ora.chat.application.models.Users;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SamePasswordsValidator implements ConstraintValidator<SamePasswords, Users> {

    @Override
    public void initialize(SamePasswords constraintAnnotation) {}

    @Override
    public boolean isValid(Users value, ConstraintValidatorContext context) {
        if(value.getPassword_confirmation() == null) {
            return true;
        }
        return value.getPassword_confirmation().equals(value.getPassword());
    }
}