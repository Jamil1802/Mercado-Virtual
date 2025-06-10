package com.mercadodigital.mercado.model;

public class Produto {
    private int id;
    private String nome;
    private String descricao;
    private double preco;
    private int estoque;
    private int idLoja;
    private boolean disponivel;

    public Produto() {
    }

    public Produto(int id, String nome, String descricao, double preco, int estoque, int idLoja) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.estoque = estoque;
        this.idLoja = idLoja;
        this.disponivel = true;
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

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public int getIdLoja() {
        return idLoja;
    }

    public void setIdLoja(int idLoja) {
        this.idLoja = idLoja;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void reduzirEstoque(int quantidade) {
        if (quantidade <= 0) return;
        if (estoque >= quantidade) {
            estoque -= quantidade;
            if (estoque == 0) {
                this.disponivel = false;
            }
        } else {
            throw new IllegalArgumentException("Estoque insuficiente para a quantidade solicitada.");
        }
    }

    public double calcularSubtotal(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        return preco * quantidade;
    }

}
