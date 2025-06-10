package com.mercadodigital.mercado.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaTest {

    @Test
    void getId() {
    }

    @Test
    void getIdPedido() {
    }

    @Test
    void getEnderecoEntrega() {
    }

    @Test
    void getStatusEntrega() {
    }

    @Test
    void getCodigoRastreamento() {
    }

    @Test
    void atualizarStatus() {
    }

    @Test
    void registrarEnvio() {
    }

    @Test
    void testCriacaoEntrega() {
        Entrega entrega = new Entrega(1, 101, "Av. 25 de Setembro, Maputo");

        assertEquals(1, entrega.getId());
        assertEquals(101, entrega.getIdPedido());
        assertEquals("Av. 25 de Setembro, Maputo", entrega.getEnderecoEntrega());
        assertEquals("Pendente", entrega.getStatusEntrega());
        assertEquals("", entrega.getCodigoRastreamento());
    }

    @Test
    void testAtualizarStatus() {
        Entrega entrega = new Entrega(2, 102, "Beira");

        entrega.atualizarStatus("Saiu para entrega");

        assertEquals("Saiu para entrega", entrega.getStatusEntrega());
    }

    @Test
    void testRegistrarEnvio() {
        Entrega entrega = new Entrega(3, 103, "Quelimane");

        entrega.registrarEnvio("R123456789MZ");

        assertEquals("Enviado", entrega.getStatusEntrega());
        assertEquals("R123456789MZ", entrega.getCodigoRastreamento());
    }
}