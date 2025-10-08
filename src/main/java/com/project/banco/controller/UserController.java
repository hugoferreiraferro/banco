package com.project.banco.controller;

import com.project.banco.dto.UserLoginDto;
import com.project.banco.dto.UserRegistrerDto;
import com.project.banco.entities.UserEntity;
import com.project.banco.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> login(@RequestBody @Valid UserRegistrerDto userRegistrerDTO) {
        if (this.userRepository.findByEmail(userRegistrerDTO.email()) != null){
            return ResponseEntity.badRequest().build();
        }

        //faço a verificação de email existente acima e abaixo criptgrafo a senha do usuario
        String encryptedPassword = new BCryptPasswordEncoder().encode(userRegistrerDTO.password());
        UserEntity user = new UserEntity(userRegistrerDTO.name(), userRegistrerDTO.email(), LocalDate.parse(userRegistrerDTO.dateOfBirth())
                , encryptedPassword, userRegistrerDTO.role());
        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto userLogin) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLogin.email(), userLogin.password());
        //coloco o email e a senha denhtro do usernamePassword que será utilizado como token
        var auth = this.authenticationManager.authenticate(usernamePassword);
        //passo o usernamePassword para o authenticate

        return ResponseEntity.ok().build();
    }


}
