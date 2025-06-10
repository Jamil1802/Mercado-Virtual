package com.mercadodigital.mercado.model;

import java.time.LocalDateTime;

public class Pagamento {
    private int id;
    private int idPedido;
    private String metodo;
    private String status;
    private LocalDateTime dataPagamento;
    private double valorTotal;
    private double valorLiquidoRecebido;

    public Pagamento(int id, int idPedido, String metodo, double valorTotal) {
        this.id = id;
        this.idPedido = idPedido;
        this.metodo = metodo;
        this.status = "Aguardando";
        this.valorTotal = valorTotal;
    }

    public boolean processar() {
        this.status = "Pago";
        this.dataPagamento = LocalDateTime.now();
        this.valorLiquidoRecebido = valorTotal * 0.85;
        return true;
    }


    public String verificarStatus() {
        return status;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public double getValorLiquidoRecebido() {
        return valorLiquidoRecebido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getMetodo() {
        return metodo;
    }

}
