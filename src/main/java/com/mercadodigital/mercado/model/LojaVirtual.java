package com.mercadodigital.mercado.model;

import java.util.ArrayList;
import java.util.List;

public class LojaVirtual {
    private int id;
    private String nome;
    private String descricao;
    private int idLojista;
    private boolean ativa;
    private List<Produto> produtos;

    public LojaVirtual() {
    }

    public LojaVirtual(int id, String nome, String descricao, int idLojista) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idLojista = idLojista;
        this.ativa = true;
        this.produtos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

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

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLojista() {
        return idLojista;
    }

    public void setIdLojista(int idLojista) {
        this.idLojista = idLojista;
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
}
