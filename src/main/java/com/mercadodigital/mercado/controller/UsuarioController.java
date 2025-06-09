package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Usuario;

import java.util.HashMap;
import java.util.Map;

public class UsuarioController {
    private Map<String, Usuario> usuarios = new HashMap<>(); // simulando base de dados
    private int proximoId = 1;
    private Usuario usuarioLogado = null;

    public boolean registrarUsuario(String nome, String email, String senha, String endereco) {
        if (usuarios.containsKey(email)) {
            return false; // usuário já existe
        }
        Usuario novoUsuario = new Usuario(proximoId++, nome, email, senha, endereco);
        usuarios.put(email, novoUsuario);
        return true;
    }

    public boolean login(String email, String senha) {
        Usuario usuario = usuarios.get(email);
        return usuario != null && usuario.getSenha().equals(senha);
    }

    public void logout() {
        usuarioLogado = null;
    }

    public boolean editarPerfil(String email, String novoNome, String novaSenha, String novoEndereco) {
        Usuario usuario = usuarios.get(email);
        if (usuario != null) {
            usuario.setNome(novoNome);
            usuario.setSenha(novaSenha);
            usuario.setEndereco(novoEndereco);
            return true;
        }
        return false;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}
