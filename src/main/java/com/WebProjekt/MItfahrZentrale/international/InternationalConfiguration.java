package com.WebProjekt.MItfahrZentrale.international;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Documented
@Configuration
public class InternationalConfiguration implements WebMvcConfigurer{

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolver = new SessionLocaleResolver();
        // Optional: Browser-Präferenz übersteuern
        // resolver.setDefaultLocale(Locale.GERMANY);
        return resolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        /*ResourceBundleMessageSource ist die Spring-Implementierung zum Laden von .properties-Dateien für Internationalisierung. */
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("lang/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        /**ändert Sprache basierend auf einem URL-Parameter */
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("sprache");
        return interceptor;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**LocaleChangeInterceptor überwacht Anfragen und prüft, ob ein bestimmter Request-Parameter (hier "sprache") übergeben wurde. 
         * Falls ja, wird die Sprache der Anwendung entsprechend geändert. 
         * */
        registry.addInterceptor(localeChangeInterceptor());
    }
    
}
