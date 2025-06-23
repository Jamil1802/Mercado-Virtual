package com.mercadodigital.mercado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "entregas")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "pedido_id", referencedColumnName = "id")
    private Pedido pedido;

    private String enderecoEntrega;
    private String statusEntrega;
    private String codigoRastreamento;

    public Entrega() {}

    public Entrega(Pedido pedido, String enderecoEntrega) {
        this.pedido = pedido;
        this.enderecoEntrega = enderecoEntrega;
        this.statusEntrega = "Pendente";
        this.codigoRastreamento = "";
    }

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public String getStatusEntrega() {
        return statusEntrega;
    }

    public void atualizarStatus(String novoStatus) {
        this.statusEntrega = novoStatus;
    }

    public String getCodigoRastreamento() {
        return codigoRastreamento;
    }

    public void registrarEnvio(String codigoRastreamento) {
        this.codigoRastreamento = codigoRastreamento;
        this.statusEntrega = "Enviado";
    }
}