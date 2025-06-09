package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.LojaVirtual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LojaVirtualController {
    private Map<Integer, LojaVirtual> lojas = new HashMap<>();
    private int proximoId = 1;

    public LojaVirtual criarLoja(String nome, String descricao, int idLojista) {
        LojaVirtual novaLoja = new LojaVirtual(proximoId++, nome, descricao, idLojista);
        lojas.put(novaLoja.getId(), novaLoja);
        return novaLoja;
    }

    public boolean editarLoja(int idLoja, String novoNome, String novaDescricao) {
        LojaVirtual loja = lojas.get(idLoja);
        if (loja != null && loja.isAtiva()) {
            loja.setNome(novoNome);
            loja.setDescricao(novaDescricao);
            return true;
        }
        return false;
    }

    public boolean deletarLoja(int idLoja) {
        LojaVirtual loja = lojas.get(idLoja);
        if (loja != null) {
            loja.setAtiva(false); // torna a loja invisível para o usuário
            return true;
        }
        return false;
    }

    public List<LojaVirtual> listarLojas() {
        List<LojaVirtual> lojasAtivas = new ArrayList<>();
        for (LojaVirtual loja : lojas.values()) {
            if (loja.isAtiva()) {
                lojasAtivas.add(loja);
            }
        }
        return lojasAtivas;
    }

    public List<LojaVirtual> listarTodasLojasParaAdmin() {
        return new ArrayList<>(lojas.values());
    }
}
