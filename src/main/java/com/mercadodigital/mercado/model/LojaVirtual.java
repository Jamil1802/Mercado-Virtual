package com.mercadodigital.mercado.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "loja_virtual")
public class LojaVirtual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    private boolean ativa;

    @OneToMany(mappedBy = "loja")
    private List<Produto> produtos;

    @OneToMany(mappedBy = "loja")
    private List<Usuario> usuarios;

    @OneToOne
    @JoinColumn(name = "administrador_id")
    private Administrador administrador;

    @ManyToOne
    @JoinColumn(name = "id_lojista")
    private Lojista lojista;

    public LojaVirtual() {
    }

    public LojaVirtual(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.ativa = true;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }
}