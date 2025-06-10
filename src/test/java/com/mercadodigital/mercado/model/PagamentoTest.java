package com.mercadodigital.mercado.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoTest {

    @Test
    void processar() {
    }

    @Test
    void verificarStatus() {
    }

    @Test
    void testProcessar() {
    }

    @Test
    void testVerificarStatus() {
    }

    @Test
    void getIdPedido() {
    }

    @Test
    void getDataPagamento() {
    }

    @Test
    void getValorLiquidoRecebido() {
    }

    @Test
    void getValorTotal() {
    }

    @Test
    void getMetodo() {
    }

    @Test
    void testCriacaoPagamento() {
        Pagamento pagamento = new Pagamento(1, 1001, "Cartão", 1000.0);

        assertEquals("Aguardando", pagamento.verificarStatus());
        assertEquals(1000.0, pagamento.getValorTotal());
        assertEquals("Cartão", pagamento.getMetodo());
        assertEquals(1001, pagamento.getIdPedido());
    }

    @Test
    void testProcessarPagamentoComTaxa() {
        Pagamento pagamento = new Pagamento(2, 1002, "M-Pesa", 2000.0);

        pagamento.processar();

        assertEquals("Pago", pagamento.verificarStatus());
        assertNotNull(pagamento.getDataPagamento());

        double valorEsperado = 1700.0;
        double valorReal = pagamento.getValorLiquidoRecebido();
        assertEquals(valorEsperado, valorReal, 0.01); // margem de erro de 0.01
    }

}