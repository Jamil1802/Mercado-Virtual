package com.mercadodigital.mercado.model;

import java.time.LocalDateTime;

public class Pagamento {
    private int id;
    private int idPedido;
    private String metodo;
    private String status;
    private LocalDateTime dataPagamento;

    public Pagamento(int id, int idPedido, String metodo) {
        this.id = id;
        this.idPedido = idPedido;
        this.metodo = metodo;
        this.status = "Aguardando";
    }

    public boolean processar() {
        this.status = "Pago";
        this.dataPagamento = LocalDateTime.now();
        return true;
    }

    public String verificarStatus() {
        return status;
    }
}
