package com.mercadodigital.mercado.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LojaVirtualTest {

    @Test
    void getId() {
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
    void setId() {
    }

    @Test
    void getIdLojista() {
    }

    @Test
    void setIdLojista() {
    }

    @Test
    void isAtiva() {
    }

    @Test
    void setAtiva() {
    }

    @Test
    void getProdutos() {
    }

    @Test
    void setProdutos() {
    }

    @Test
    void testConstrutorComParametros() {
        LojaVirtual loja = new LojaVirtual(1, "TechStore", "Loja de eletrônicos", 101);

        assertEquals(1, loja.getId());
        assertEquals("TechStore", loja.getNome());
        assertEquals("Loja de eletrônicos", loja.getDescricao());
        assertEquals(101, loja.getIdLojista());
        assertTrue(loja.isAtiva());
        assertNotNull(loja.getProdutos());
        assertTrue(loja.getProdutos().isEmpty());
    }

    @Test
    void testSettersEBasicos() {
        LojaVirtual loja = new LojaVirtual();

        loja.setId(2);
        loja.setNome("Casa da Moda");
        loja.setDescricao("Loja de roupas femininas");
        loja.setIdLojista(202);
        loja.setAtiva(false);

        assertEquals(2, loja.getId());
        assertEquals("Casa da Moda", loja.getNome());
        assertEquals("Loja de roupas femininas", loja.getDescricao());
        assertEquals(202, loja.getIdLojista());
        assertFalse(loja.isAtiva());
    }

    @Test
    void testManipulacaoProdutos() {
        LojaVirtual loja = new LojaVirtual(3, "Livros & Cia", "Loja de livros", 303);

        Produto produto1 = new Produto(1, "Livro Java", "Aprenda Java", 120.0, 10, 3);
        Produto produto2 = new Produto(2, "Livro Spring", "Spring Boot", 150.0, 5, 3);

        loja.setProdutos(List.of(produto1, produto2));

        List<Produto> produtos = loja.getProdutos();

        assertEquals(2, produtos.size());
        assertEquals("Livro Java", produtos.get(0).getNome());
        assertEquals("Livro Spring", produtos.get(1).getNome());
    }
}