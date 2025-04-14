package com.WebProjekt.MItfahrZentrale.configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.services.benutzer.BenutzerServiceImpl;

@Configuration @EnableWebSecurity
public class SecurityConfiguration {

    @Autowired 
    BenutzerServiceImpl benutzerService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize //requestMatchers legt fest, wie die GTTP-Anfragen autorisiert werden.
        .requestMatchers(HttpMethod.GET, "/assets/*").permitAll() //Anfragen über Http-Get auf assets sind für jeden erlaubt. Also bilder etc.
        .requestMatchers("/admin/ort/*", "/help").hasRole("CHEF")
        .requestMatchers("/admin/*").authenticated()
        .requestMatchers("/h2-console/**").permitAll()
        .anyRequest().permitAll() ) //egal welche Anfrage, jeder kann zugreifen.
        .formLogin(form -> form.defaultSuccessUrl("/admin", true)) //hier wird man standardmäßig nach erfolgreichem login hinnavigiert
        //.logout(out -> out.logoutSuccessUrl("/info"));
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/h2-console/**")  // Deaktiviere CSRF für H2-Konsole
        )
        .headers(headers -> headers
            .frameOptions().sameOrigin()  // Ermöglicht die Anzeige der H2-Konsole in einem iFrame
        );
        return http.build();
    }

    @Bean 
    PasswordEncoder passwordEncoder() {
        // @Bean -> Encoder woanders per @Autowired abrufbar
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    // @Bean public UserDetailsService userDetailsService() {
    //     UserBuilder userbuilder = User.withDefaultPasswordEncoder(); // Klartext-Passwort codiert speichern

    //     Collection<UserDetails> alleUserDetails = new LinkedList<UserDetails>();
    //     List<Benutzer> alleBenutzer = benutzerService.holeAlleBenutzer();

    //     UserDetails user1 = userbuilder.username("joendhard@diebiffels.de").password("1234") .roles("USER").build();
    //     UserDetails user2 = userbuilder.username("joghurta@diebiffels.de").password("1234") .roles("USER", "CHEF").build();

    //     alleUserDetails.add(user1); alleUserDetails.add(user2);

    //     for (Benutzer benutzer : alleBenutzer){
    //         UserDetails user = userbuilder.username(benutzer.geteMail()).password(benutzer.getPasswort()).roles("USER").build();
    //         alleUserDetails.add(user);

    //     }
    //     return new InMemoryUserDetailsManager(user1, user2);
    // }
    
}
