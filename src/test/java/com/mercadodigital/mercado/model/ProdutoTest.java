package com.mercadodigital.mercado.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void getNome() {
    }

    @Test
    void setNome() {
    }

    @Test
    void getDescricao() {
    }

    @Test
    void setDescricao() {
    }

    @Test
    void getPreco() {
    }

    @Test
    void setPreco() {
    }

    @Test
    void getEstoque() {
    }

    @Test
    void setEstoque() {
    }

    @Test
    void getIdLoja() {
    }

    @Test
    void setIdLoja() {
    }

    @Test
    void isDisponivel() {
    }

    @Test
    void setDisponivel() {
    }

    @Test
    void testConstrutorCompleto() {
        Produto produto = new Produto(1, "Mouse Gamer", "Mouse com RGB", 150.0, 50, 10);

        assertEquals(1, produto.getId());
        assertEquals("Mouse Gamer", produto.getNome());
        assertEquals("Mouse com RGB", produto.getDescricao());
        assertEquals(150.0, produto.getPreco(), 0.01);
        assertEquals(50, produto.getEstoque());
        assertEquals(10, produto.getIdLoja());
        assertTrue(produto.isDisponivel());
    }

    @Test
    void testSettersAndGetters() {
        Produto produto = new Produto();

        produto.setId(2);
        produto.setNome("Teclado Mecânico");
        produto.setDescricao("Teclado com switch blue");
        produto.setPreco(250.0);
        produto.setEstoque(30);
        produto.setIdLoja(20);
        produto.setDisponivel(false);

        assertEquals(2, produto.getId());
        assertEquals("Teclado Mecânico", produto.getNome());
        assertEquals("Teclado com switch blue", produto.getDescricao());
        assertEquals(250.0, produto.getPreco(), 0.01);
        assertEquals(30, produto.getEstoque());
        assertEquals(20, produto.getIdLoja());
        assertFalse(produto.isDisponivel());
    }

    @Test
    void testReduzirEstoque() {
        Produto produto = new Produto(1, "Monitor", "24 pol.", 1000.0, 5, 1);

        produto.reduzirEstoque(2);
        assertEquals(3, produto.getEstoque());
        assertTrue(produto.isDisponivel());

        produto.reduzirEstoque(3);
        assertEquals(0, produto.getEstoque());
        assertFalse(produto.isDisponivel());
    }

    @Test
    void testCalcularSubtotal() {
        Produto produto = new Produto(1, "Monitor", "24 pol.", 1000.0, 5, 1);

        double subtotal = produto.calcularSubtotal(3);
        assertEquals(3000.0, subtotal, 0.01);
    }

}