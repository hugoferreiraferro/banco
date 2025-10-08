package com.project.banco.dto;

import com.project.banco.entities.UserEnum;

import java.time.LocalDate;

public record UserRegistrerDto(String name, String email,
                               String dateOfBirth,
                               String password,
                               UserEnum role) { }
