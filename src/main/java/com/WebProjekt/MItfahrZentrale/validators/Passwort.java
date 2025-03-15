package com.WebProjekt.MItfahrZentrale.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD }) // Annotation darf nur auf Felder angewendet werden. Kann also nicht mehr auf Methoden oder Klasse angewendet werden.
@Retention(RetentionPolicy.RUNTIME) // zur Laufzeit verfügbar und kann vom Java-Reflection-Mechanismus genutzet werden.

@Constraint(validatedBy=PasswortValidation.class) // Verweis auf Validator-Implementierung
//@Min(1) // anderere Constraints optional "dazukombinierbar"
@Documented //Annotation in der Javadoc-Dokumentation erscheint und somit für Entwickler sichtbar ist.

public @interface Passwort {

    String message() default "Das Passwort muss eine 17 oder 'Siebzehn' enthalten";

    /**Payload() ist Methode, die Metadaten an Contraint-Annotation anhängt.
    * default hängt leeres Array an, falls es keine anzuhängenden Metadaten gibt.
    * ? --> Klasse, die von Payload erbt bzw Payload erweitert.
    * Class wird durch die eckigen Klammern zu einem Array von Klassen - Class gibt Typ an.
    * --> also wird hier ein Array von Klassen erstellt, die mit Metadaten durhc Payload gefüllt werden, bzw mit einem
    * leeren Array, falls keine Metadaten vorhanden.
    */
    Class<? extends Payload>[] payload() default { };

    /*groups() definiert Validierungsgruppen, die verwendet werden, um bestimmte
    * Validierungsregeln zu aktivieren oder deaktivieren
    * Validierungen werden dann nur für bestimmte Gruppen durchgeführt, sofern es relevant ist: @NotNull(groups=Create.class)
    */
    Class<?>[] groups() default { };
    String value();
    String value2();
    
}
