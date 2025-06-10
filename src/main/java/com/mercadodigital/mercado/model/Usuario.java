package com.mercadodigital.mercado.model;

import jakarta.persistence.*;

//@Table(name = "usuario")
//@Entity(name = "usuario")
public class Usuario {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
//    @Column(name = "nome", length = 150, nullable = false, unique = false)
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario() {
    }

    public Usuario(int id, String nome, String email, String senha, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}
