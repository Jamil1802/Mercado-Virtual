package com.mercadodigital.mercado.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void atualizarStatus() {
    }

    @Test
    void cancelar() {
    }

    @Test
    void testCriacaoPedido() {
        List<Integer> produtos = List.of(1, 2, 3);
        Pedido pedido = new Pedido(1, 1001, produtos);

        assertEquals(1, pedido.getId());
        assertEquals(1001, pedido.getIdUsuario());
        assertEquals(produtos, pedido.getProdutosIds());
        assertEquals("Pendente", pedido.getStatus());
        assertNotNull(pedido.getDataPedido());
    }

    @Test
    void testAtualizarStatus() {
        Pedido pedido = new Pedido(2, 1002, List.of(5, 6));

        pedido.atualizarStatus("Processando");

        assertEquals("Processando", pedido.getStatus());
    }

    @Test
    void testCancelarPedido() {
        Pedido pedido = new Pedido(3, 1003, List.of(9, 10));

        pedido.cancelar();

        assertEquals("Cancelado", pedido.getStatus());
    }
}