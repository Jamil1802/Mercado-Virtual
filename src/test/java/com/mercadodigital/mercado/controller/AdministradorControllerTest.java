package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Usuario;
import com.mercadodigital.mercado.repository.UsuarioRepository;
import com.mercadodigital.mercado.service.AdministradorService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdministradorControllerTest {

    @Mock
    private AdministradorService administradorService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AdministradorController administradorController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("🛠️ Configurando ambiente de teste...");
    }

    @Test
    @DisplayName("👑 Promover para admin - Sucesso")
    void promoverParaAdmin_Sucesso() {
        System.out.println("Teste: Promoção para admin bem-sucedida");

        // Arrange
        int userId = 1;
        int adminId = 2;
        Usuario admin = new Usuario(adminId, "Admin", "admin@email.com", "senha", "Endereço");
        admin.setTipoUsuario("ADMIN");

        when(usuarioRepository.findById(adminId)).thenReturn(Optional.of(admin));
        doNothing().when(administradorService).promoverParaAdmin(userId);

        // Act
        ResponseEntity<String> response = administradorController.promoverParaAdmin(userId, adminId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário promovido a administrador", response.getBody());
        verify(administradorService).promoverParaAdmin(userId);
        System.out.println("✅ Teste passou: Promoção bem-sucedida");
    }

    @Test
    @DisplayName("🚫 Desativar usuário - Sucesso")
    void desativarUsuario_Sucesso() {
        System.out.println("Teste: Desativação de usuário bem-sucedida");

        // Arrange
        int userId = 1;
        int adminId = 2;
        Usuario admin = new Usuario(adminId, "Admin", "admin@email.com", "senha", "Endereço");
        admin.setTipoUsuario("ADMIN");

        when(usuarioRepository.findById(adminId)).thenReturn(Optional.of(admin));
        doNothing().when(administradorService).desativarUsuario(userId);

        // Act
        ResponseEntity<String> response = administradorController.desativarUsuario(userId, adminId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário desativado com sucesso", response.getBody());
        verify(administradorService).desativarUsuario(userId);
        System.out.println("✅ Teste passou: Desativação bem-sucedida");
    }

    @Test
    @DisplayName("🔄 Reativar usuário - Sucesso")
    void reativarUsuario_Sucesso() {
        System.out.println("Teste: Reativação de usuário bem-sucedida");

        // Arrange
        int userId = 1;
        int adminId = 2;
        Usuario admin = new Usuario(adminId, "Admin", "admin@email.com", "senha", "Endereço");
        admin.setTipoUsuario("ADMIN");

        when(usuarioRepository.findById(adminId)).thenReturn(Optional.of(admin));
        doNothing().when(administradorService).reativarUsuario(userId);

        // Act
        ResponseEntity<String> response = administradorController.reativarUsuario(userId, adminId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuário reativado com sucesso", response.getBody());
        verify(administradorService).reativarUsuario(userId);
        System.out.println("✅ Teste passou: Reativação bem-sucedida");
    }

    @Test
    @DisplayName("👤 Buscar usuário - Encontrado")
    void buscarUsuario_Encontrado() {
        System.out.println("Teste: Busca de usuário existente");

        // Arrange
        int userId = 1;
        Usuario usuario = new Usuario(userId, "Teste", "teste@email.com", "senha", "Endereço");
        when(administradorService.buscarUsuarioDetalhado(userId)).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = administradorController.buscarUsuarioDetalhado(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getId());
        System.out.println("✅ Teste passou: Usuário encontrado");
    }

    @Test
    @DisplayName("🔍 Buscar usuário - Não encontrado")
    void buscarUsuario_NaoEncontrado() {
        System.out.println("Teste: Busca de usuário inexistente");

        // Arrange
        int userId = 99;
        when(administradorService.buscarUsuarioDetalhado(userId))
                .thenThrow(new EntityNotFoundException("Usuário não encontrado"));

        // Act
        ResponseEntity<Usuario> response = administradorController.buscarUsuarioDetalhado(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("✅ Teste passou: Usuário não encontrado");
    }

    @Test
    @DisplayName("🚷 Acesso negado - Não admin")
    void acessoNegado_NaoAdmin() {
        System.out.println("Teste: Acesso negado para não administrador");

        // Arrange
        int userId = 1;
        int adminId = 2;
        when(usuarioRepository.findById(adminId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = administradorController.desativarUsuario(userId, adminId);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Acesso negado", response.getBody());
        System.out.println("✅ Teste passou: Acesso corretamente negado");
    }

    @Test
    @DisplayName("📋 Listar administradores - Sucesso")
    void listarAdministradores_Sucesso() {
        System.out.println("Teste: Listagem de administradores");

        // Arrange
        Usuario admin1 = new Usuario(1, "Admin 1", "admin1@email.com", "senha", "Endereço");
        admin1.setTipoUsuario("ADMIN");
        Usuario admin2 = new Usuario(2, "Admin 2", "admin2@email.com", "senha", "Endereço");
        admin2.setTipoUsuario("ADMIN");

        when(administradorService.listarAdministradores()).thenReturn(List.of(admin1, admin2));

        // Act
        ResponseEntity<List<Usuario>> response = administradorController.listarAdministradores();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertTrue(response.getBody().stream().allMatch(u -> "ADMIN".equals(u.getTipoUsuario())));
        System.out.println("✅ Teste passou: Listagem de admins retornou " + response.getBody().size() + " itens");
    }

    @Test
    @DisplayName("🔍 Buscar usuário detalhado - Sucesso")
    void buscarUsuarioDetalhado_Sucesso() {
        System.out.println("Teste: Busca detalhada de usuário");

        // Arrange
        int userId = 1;
        Usuario usuario = new Usuario(userId, "Usuário", "user@email.com", "senha", "Endereço");
        when(administradorService.buscarUsuarioDetalhado(userId)).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = administradorController.buscarUsuarioDetalhado(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getId());
        System.out.println("✅ Teste passou: Usuário encontrado - " + response.getBody().getNome());
    }

    @Test
    @DisplayName("🔍 Buscar usuário detalhado - Não encontrado")
    void buscarUsuarioDetalhado_NaoEncontrado() {
        System.out.println("Teste: Busca detalhada de usuário inexistente");

        // Arrange
        int userId = 99;
        when(administradorService.buscarUsuarioDetalhado(userId))
                .thenThrow(new EntityNotFoundException("Usuário não encontrado"));

        // Act
        ResponseEntity<Usuario> response = administradorController.buscarUsuarioDetalhado(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        System.out.println("✅ Teste passou: Usuário não encontrado (como esperado)");
    }

    @Test
    @DisplayName("👑 Remover privilégios admin - Sucesso")
    void removerPrivilegiosAdmin_Sucesso() {
        System.out.println("Teste: Remoção de privilégios de admin");

        // Arrange
        int userId = 2;
        int adminId = 1;
        Usuario admin = new Usuario(adminId, "Admin", "admin@email.com", "senha", "Endereço");
        admin.setTipoUsuario("ADMIN");

        when(usuarioRepository.findById(adminId)).thenReturn(Optional.of(admin));
        doNothing().when(administradorService).removerPrivilegiosAdmin(userId);

        // Act
        ResponseEntity<String> response = administradorController.removerPrivilegiosAdmin(userId, adminId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Privilégios de administrador removidos", response.getBody());
        verify(administradorService).removerPrivilegiosAdmin(userId);
        System.out.println("✅ Teste passou: Privilégios removidos com sucesso");
    }

    @Test
    @DisplayName("👑 Remover privilégios admin - Último admin")
    void removerPrivilegiosAdmin_UltimoAdmin() {
        System.out.println("Teste: Tentativa de remover último admin");

        // Arrange
        int userId = 1; // O único admin
        int adminId = 1;
        Usuario admin = new Usuario(adminId, "Admin", "admin@email.com", "senha", "Endereço");
        admin.setTipoUsuario("ADMIN");

        when(usuarioRepository.findById(adminId)).thenReturn(Optional.of(admin));
        doThrow(new SecurityException("Não é permitido remover o último administrador"))
                .when(administradorService).removerPrivilegiosAdmin(userId);

        // Act
        ResponseEntity<String> response = administradorController.removerPrivilegiosAdmin(userId, adminId);

        // Assert
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertTrue(response.getBody().contains("último administrador"));
        System.out.println("✅ Teste passou: Proteção do último admin funcionou");
    }
}