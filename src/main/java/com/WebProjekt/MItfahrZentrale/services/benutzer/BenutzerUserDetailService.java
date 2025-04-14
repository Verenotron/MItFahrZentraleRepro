package com.WebProjekt.MItfahrZentrale.services.benutzer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.WebProjekt.MItfahrZentrale.entities.benutzer.Benutzer;
import com.WebProjekt.MItfahrZentrale.entities.benutzer.BenutzerRepository;

import jakarta.transaction.Transactional;

@Service
public class BenutzerUserDetailService implements UserDetailsService{

    @Autowired BenutzerRepository benutzerRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Transactional //stellt sicher, dass die Methode auf getMag nur innerhalb einer offenen Session ausgeführt wird
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//Stellt Verbindung zwischen Spring Sequrity unf eigener Benutzer Tabelle her
        UserBuilder userbuilder = User.withUsername(username);
        UserDetails user1;

        Optional<Benutzer> user = benutzerRepository.findByEMail(username);
        if(user.isPresent() && user.get().getMag().contains("MACHT")){ //equals methode quasi bereits in Contains enthalten. Wort muss exakt so enthalten sein,
            user1 = userbuilder.username(user.get().geteMail()).password(user.get().getPasswort()) .roles("USER", "CHEF").build();
        }else{
            user1 = userbuilder.username(user.get().geteMail()).password(user.get().getPasswort()) .roles("USER").build();
        }
        
        return user1;

    }
    
}
