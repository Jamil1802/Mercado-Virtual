package com.mercadodigital.mercado.model;

public class Entrega {
    private int id;
    private int idPedido;
    private String enderecoEntrega;
    private String statusEntrega;
    private String codigoRastreamento;

    public Entrega(int id, int idPedido, String enderecoEntrega) {
        this.id = id;
        this.idPedido = idPedido;
        this.enderecoEntrega = enderecoEntrega;
        this.statusEntrega = "Pendente";
        this.codigoRastreamento = "";
    }

    public int getId() {
        return id;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public String getCodigoRastreamento() {
        return codigoRastreamento;
    }

    public void atualizarStatus(String novoStatus) {
        this.statusEntrega = novoStatus;
    }

    public void registrarEnvio(String codigoRastreamento) {
        this.codigoRastreamento = codigoRastreamento;
        this.statusEntrega = "Enviado";
    }
}
