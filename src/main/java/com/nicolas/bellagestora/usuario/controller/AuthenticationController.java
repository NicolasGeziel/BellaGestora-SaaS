package com.nicolas.bellagestora.usuario.controller;

import com.nicolas.bellagestora.config.security.TokenSerivce;
import com.nicolas.bellagestora.usuario.dto.AuthenticationDTO;
import com.nicolas.bellagestora.usuario.dto.RegisterDTO;
import com.nicolas.bellagestora.usuario.dto.ResponseLoginDTO;
import com.nicolas.bellagestora.usuario.model.User;
import com.nicolas.bellagestora.usuario.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenSerivce tokenSerivce;

    @PostMapping("/login")
    public ResponseEntity Login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        var authentication = authenticationManager.authenticate(usernamePassword);
        var token =  tokenSerivce.generateToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new ResponseLoginDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(repository.findByLogin(data.login())!=null) return ResponseEntity.badRequest().build();
        String password = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.login(), password, data.role());
        this.repository.save(user);

        return ResponseEntity.ok().build();
    }

}
