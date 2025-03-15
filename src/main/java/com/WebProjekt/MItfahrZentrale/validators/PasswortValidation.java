package com.WebProjekt.MItfahrZentrale.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswortValidation implements ConstraintValidator <Passwort, String>{

    protected String key;
    protected String key2;


    public void initialize(Passwort gutPass){ //muss initialize heißen
        this.key = gutPass.value();
        this.key2 = gutPass.value2();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) { //ConstraintValidator führt Validierung tatsächlich aus
        if(value.contains(key) || value.toLowerCase().contains(key2) || value == ""){
            return true;
        }
        return false;
    }
    
}
