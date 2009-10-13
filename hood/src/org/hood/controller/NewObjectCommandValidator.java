package org.hood.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NewObjectCommandValidator
    implements Validator
{

    public boolean supports(Class cls)
    {
        return NewObjectCommand.class.isAssignableFrom(cls);
    }


    public void validate(Object o, Errors errors)
    {
        NewObjectCommand cmd = (NewObjectCommand)o;
        
        if (cmd.getType() == null)
        {
            errors.rejectValue("type", "NoType", "You need to select a type");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NoName", "You must enter a name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NoDesc", "You must enter a description");
    }

}
