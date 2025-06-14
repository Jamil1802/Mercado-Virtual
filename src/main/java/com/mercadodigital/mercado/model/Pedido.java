package com.mercadodigital.mercado.model;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    private int id;
    private int idUsuario;
    private List<Integer> produtosIds;
    private String status;
    private LocalDateTime dataPedido;

    public Pedido(int id, int idUsuario, List<Integer> produtosIds) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.produtosIds = produtosIds;
        this.status = "Pendente";
        this.dataPedido = LocalDateTime.now();
    }

    public void atualizarStatus(String novoStatus) {
        this.status = novoStatus;
    }

    public void cancelar() {
        this.status = "Cancelado";
    }
}
