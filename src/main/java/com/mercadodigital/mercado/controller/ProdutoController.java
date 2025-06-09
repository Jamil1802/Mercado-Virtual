package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoController {
    private Map<Integer, Produto> produtos = new HashMap<>();
    private int proximoId = 1;

    public Produto adicionarProduto(String nome, String descricao, double preco, int estoque, int idLoja) {
        Produto novoProduto = new Produto(proximoId++, nome, descricao, preco, estoque, idLoja);
        produtos.put(novoProduto.getId(), novoProduto);
        return novoProduto;
    }

    public boolean editarProduto(int idProduto, String nome, String descricao, double preco, int estoque) {
        Produto produto = produtos.get(idProduto);
        if (produto != null && produto.isDisponivel()) {
            produto.setNome(nome);
            produto.setDescricao(descricao);
            produto.setPreco(preco);
            produto.setEstoque(estoque);
            return true;
        }
        return false;
    }

    public boolean removerProduto(int idProduto) {
        Produto produto = produtos.get(idProduto);
        if (produto != null) {
            produto.setDisponivel(false); // invisível para usuários, visível para administradores
            return true;
        }
        return false;
    }

    public List<Produto> listarProdutosDisponiveisPorLoja(int idLoja) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto produto : produtos.values()) {
            if (produto.isDisponivel() && produto.getIdLoja() == idLoja) {
                resultado.add(produto);
            }
        }
        return resultado;
    }

    public List<Produto> listarTodosProdutosParaAdmin() {
        return new ArrayList<>(produtos.values());
    }

}
