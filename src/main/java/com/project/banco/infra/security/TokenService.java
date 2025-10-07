package com.project.banco.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.banco.entities.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") //pega do properties a secret passada via string
    private String secret;
    public String generateTOken(UserEntity user){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret); //algoritmo utilizado para criptgrafia
            String token = JWT.create() //criar token
                    .withIssuer("login-auth-api")
                    .withSubject(user.getEmail()) //o email é salvo no token
                    .withExpiresAt(this.generateExpirationDate()) //função para setar o tempo de expiração do token
                    .sign(algorithm); //passo o algorithm de criação do token
            return token;
        }catch (Exception e){
            throw new RuntimeException("Error enquanto estava autenticando");
        }
    }
       public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("login-auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException e){ //erro de verificação de token
            return null;
        }
       }

       private Instant generateExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
        //LocalDateTime pega o tempo de criação do token,
        // aumenta em duas horas e passa o instante q é o horário de brasília
    }
}
