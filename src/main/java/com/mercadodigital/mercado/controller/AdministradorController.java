package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Usuario;

public class AdministradorController extends Usuario {
    public AdministradorController(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha, endereco);
    }
}
