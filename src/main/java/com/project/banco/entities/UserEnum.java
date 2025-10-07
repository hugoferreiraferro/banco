package com.project.banco.entities;

public enum UserEnum {
    USER("user"); //role do usuario

    private String role;

    UserEnum(String role) { //enum n tem encrapsulamento
        this.role = role;
    }

    public String getRole() { //Método para pegar a role
        return role;
    }
}
