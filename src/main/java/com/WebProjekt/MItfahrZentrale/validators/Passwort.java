package com.WebProjekt.MItfahrZentrale.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD }) // Annotation darf nur auf Felder angewendet werden. Kann also nicht mehr auf Methoden oder Klasse angewendet werden.
@Retention(RetentionPolicy.RUNTIME) // zur Laufzeit verfügbar und kann vom Java-Reflection-Mechanismus genutzet werden.

//RetentionPolicy gibt an, wie lange eine Annotation in Java erhalten bleibt und wo sie verwendet werden kann. Ist Teil des Java-Reflection-Mechanismus
@Constraint(validatedBy=PasswortValidation.class) // Verweis auf Validator-Implementierung
//@Min(1) // anderere Constraints optional "dazukombinierbar"
//@Documented 

public @interface Passwort {

    String message() default "Das Passwort muss eine 17 oder 'Siebzehn' enthalten";

    Class<? extends Payload>[] payload() default { };
    // Zuordnung zu Validierungsgruppen?
    Class<?>[] groups() default { };
    // Annotation hat int-wertigen "value"-Parameter fuer Teiler
    String value();
    String value2();
    
}
