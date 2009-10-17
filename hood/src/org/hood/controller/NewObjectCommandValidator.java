package org.hood.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates the new object data
 * 
 * @author shelmberger
 *
 */
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
        
        if (cmd.getLocationType() == null)
        {
            errors.rejectValue("type", "NoType", "You need to select a type");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NoName", "You must enter a name");
    }

}
