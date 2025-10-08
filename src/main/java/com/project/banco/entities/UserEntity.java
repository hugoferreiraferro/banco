package com.project.banco.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.UserDatabase;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data //lombok cria de forma "escondida" os getters e setters
@NoArgsConstructor //construtor padrão sem argumentos
@AllArgsConstructor //construtor padrão com todos os argumentos
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //gera o id de forma aleatória
    private String idUser;

    @NotBlank(message =  "Nome não pode estar em branco")
    private String name;

    @NotBlank(message =  "Email não pode estar em branco")
    private String email;

    private LocalDate dateOfBirth;

    @NotBlank(message =  "Senha não pode estar em branco")
    private String password; //cripotagrafar com a JWT

    @Enumerated(EnumType.STRING) //falo para salvar como texto no db
    private UserEnum role;

    public UserEntity(String name, String email, LocalDate dateOfBirth, String password, UserEnum role) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.role = role;
    } //construtor de registro de usuário


//MÉTODOS DO USERDETAILS

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); //não faço a verificação
        // pois só terei uma ROLE
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
