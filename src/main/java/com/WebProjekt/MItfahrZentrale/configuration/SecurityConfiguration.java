package com.WebProjekt.MItfahrZentrale.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize //requestMatchers legt fest, wie die GTTP-Anfragen autorisiert werden.
        .requestMatchers(HttpMethod.GET, "/assets/*").permitAll() //Anfragen über Http-Get auf assets sind für jeden erlaubt. Also bilder etc.
        .requestMatchers("/admin/ort/*", "/help").hasRole("CHEF")
        .requestMatchers("/admin/*").authenticated()
        .anyRequest().permitAll() ) //egal welche Anfrage, jeder kann zugreifen.
        .formLogin(form -> form.defaultSuccessUrl("/admin", true)); //hier wird man standardmäßig nach erfolgreichem login hinnavigiert
        //.logout(out -> out.logoutSuccessUrl("/info"));
        return http.build();
    }

    @Bean 
    PasswordEncoder passwordEncoder() {
        // @Bean -> Encoder woanders per @Autowired abrufbar
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean public UserDetailsService userDetailsService() {
        UserBuilder userbuilder = User.withDefaultPasswordEncoder(); // Klartext-Passwort codiert speichern

        UserDetails user1 = userbuilder.username("joendhard@diebiffels.de").password("1234") .roles("USER").build();
        UserDetails user2 = userbuilder.username("joghurta@diebiffels.de").password("1234") .roles("USER", "CHEF").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
    
}
