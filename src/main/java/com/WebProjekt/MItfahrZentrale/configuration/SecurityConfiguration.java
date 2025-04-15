package com.WebProjekt.MItfahrZentrale.configuration;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.services.benutzer.BenutzerServiceImpl;

// import org.springframework.security.oauth2.jwt.JwtDecoder;
// import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration @EnableWebSecurity
public class SecurityConfiguration {

    @Autowired 
    BenutzerServiceImpl benutzerService;

    @Value("${SECRETKEY}")
    private String secretKey;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public NimbusJwtDecoder jwtDecoder() {
        byte[] keyBytes = secretKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }

    @Order(1)
    @Bean
    public SecurityFilterChain filterChainAPI(HttpSecurity http) throws Exception{
        http
        // diese Filterkette ist für diese URI-Pfade zuständig
        .securityMatchers(s -> s.requestMatchers("/api/**","/stompbroker"))
        .authorizeHttpRequests(authz -> authz
        // Zugang zu /api/token und (hier) STOMP-Endpunkt offen
        .requestMatchers(HttpMethod.POST, "/api/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
        .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll() //OPTIONS prüft, ob eine Anfrage erlaubt ist(CORD-Vorabfragen vom Browser)
        .requestMatchers("/stompbroker").permitAll()
        // Zugriff auf sonstige /api-Endpunkte nur authentifiziert
        .requestMatchers("/api/**").authenticated()
        // Rest blocken
        .anyRequest().permitAll())
        // JWT-Authentifizierung mit eigenem JWT-Decoder (s.o.)
        // .oauth2ResourceServer(o -> o.jwt(j -> j.decoder(jwtDecoder())
        // kein CSRF-Check und kein Sessionmanagement für /api-Endpunkte
        .csrf(csrf -> csrf.disable())
        .sessionManagement((session) ->
        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration(); //Erlaubt CORS Abfragen von überall, für alle Methoden und Header. Wichtig für mein Frontend.
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // @Bean
    // @Order(1)
    // public SecurityFilterChain filterChainAPI(HttpSecurity http) throws Exception {
    // http
    //     .securityMatcher("/api/**", "/stompbroker") // nur für diese Pfade
    //     .csrf(csrf -> csrf.disable())
    //     .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //     .authorizeHttpRequests(auth -> auth
    //         .requestMatchers("/api/token", "/stompbroker").permitAll()
    //         .anyRequest().authenticated()
    //     )
    //     .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // JWT-Authentifizierung

    // return http.build();
    // }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(authorize -> authorize //requestMatchers legt fest, wie die GTTP-Anfragen autorisiert werden.
        .requestMatchers(HttpMethod.GET, "/assets/*").permitAll() //Anfragen über Http-Get auf assets sind für jeden erlaubt. Also bilder etc.
        .requestMatchers("/admin/ort", "/admin/ort/*", "/help").hasRole("CHEF")
        .requestMatchers("/admin/*").authenticated()
        //.requestMatchers("/h2-console/**").permitAll() //nur für entwicklung nötig
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
