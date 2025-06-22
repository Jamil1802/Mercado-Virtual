package com.mercadodigital.mercado.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UsuarioTest {

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
    void getEmail() {
    }

    @Test
    void setEmail() {
    }

    @Test
    void getSenha() {
    }

    @Test
    void setSenha() {
    }

    @Test
    void getEndereco() {
    }

    @Test
    void setEndereco() {
    }

    @Test
    void isActivo() {
    }

    @Test
    void setActivo() {
    }

    @Test
    void getLoja() {
    }

    @Test
    void setLoja() {
    }
    @Test
    void testUsuarioCompleto() {
        Usuario usuario = new Usuario(
                1,
                "Carlos Alberto",
                "carlos@email.com",
                "senha123",
                "Av. 25 de Setembro, Maputo"
        );

        assertEquals("Carlos Alberto", usuario.getNome());
        assertEquals("carlos@email.com", usuario.getEmail());
        assertEquals("senha123", usuario.getSenha());
        assertEquals("Av. 25 de Setembro, Maputo", usuario.getEndereco());
        assertEquals(1, usuario.getId());
    }

    @Test
    void testSetters() {
        Usuario usuario = new Usuario();
        usuario.setNome("Maria");
        usuario.setEmail("maria@email.com");
        usuario.setSenha("abc123");
        usuario.setEndereco("Rua Central");

        assertEquals("Maria", usuario.getNome());
        assertEquals("maria@email.com", usuario.getEmail());
        assertEquals("abc123", usuario.getSenha());
        assertEquals("Rua Central", usuario.getEndereco());
    }
}
}