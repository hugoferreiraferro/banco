package com.project.banco.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Data //lombok cria de forma "escondida" os getters e setters
@NoArgsConstructor //construtor padrão sem argumentos
@AllArgsConstructor //construtor padrão com todos os argumentos
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) //gera o id de forma aleatória
    private String idUser;

    @NotBlank()
    private String name;

    @NotBlank()
    private String cpf;

    @NotBlank()
    private String rg;

    @NotBlank()
    private LocalDate dateOfBirth;

    @NotBlank()
    private String password; //cripotagrafar com a JWT

}
