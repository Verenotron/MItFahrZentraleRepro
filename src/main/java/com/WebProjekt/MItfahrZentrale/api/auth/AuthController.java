package com.WebProjekt.MItfahrZentrale.api.auth;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.WebProjekt.MItfahrZentrale.services.auth.AuthTokenService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080"})
@RestController
public class AuthController {

    @Autowired AuthenticationManager authManager;
    @Autowired AuthTokenService authTokenService;

    @PostMapping("/api/token")
    public ResponseEntity<String> login(@RequestBody AuthDTD authDTD){
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(authDTD.username(), authDTD.passwort())
        );

        String jwt = null;
        try{
            jwt = authTokenService.generateToken(authDTD.username(), auth.getAuthorities().toString());
        }catch(Exception e){
            System.out.println(e.toString());
        }
        
        System.out.println("Login aus dem Frontend hat funktioniert.");
        return ResponseEntity.ok(jwt);
    }
    
}
