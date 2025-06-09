package com.mercadodigital.mercado.model;

import java.util.List;

public class Lojista extends Usuario{
    private List<LojaVirtual> lojas;

    public Lojista(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha, endereco);
        this.lojas = lojas;
    }
}
