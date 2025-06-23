package com.mercadodigital.mercado.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    private String metodo;
    private String status;
    private LocalDateTime dataPagamento;
    private double valorTotal;
    private double valorLiquidoRecebido;

    public Pagamento() {}

    public Pagamento(Pedido pedido, String metodo, double valorTotal) {
        this.pedido = pedido;
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

    public int getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorLiquidoRecebido() {
        return valorLiquidoRecebido;
    }

    public String getMetodo() {
        return metodo;
    }
}