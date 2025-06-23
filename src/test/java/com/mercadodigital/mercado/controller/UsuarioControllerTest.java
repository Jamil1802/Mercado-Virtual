package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Usuario;
import com.mercadodigital.mercado.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        System.out.println("Preparando ambiente de teste...");
    }

    @Test
    @DisplayName("✅ Registrar novo usuário - deve retornar sucesso")
    void registrarUsuario_NovoUsuario_DeveRetornarSucesso() {
        System.out.println("Executando teste: Registrar novo usuário válido");

        // Arrange
        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail("novo@email.com");
        novoUsuario.setSenha("senha123");

        when(usuarioRepository.findByEmail("novo@email.com")).thenReturn(null);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(novoUsuario);

        // Act
        ResponseEntity<?> response = usuarioController.registrarUsuario(novoUsuario);

        // Assert
        assertAll("Verificando todos os aspectos da resposta",
                () -> assertEquals(200, response.getStatusCodeValue(),
                        "Status code deveria ser 200 (OK)"),
                () -> assertEquals("Usuário registrado com sucesso.", response.getBody(),
                        "Mensagem de sucesso incorreta"),
                () -> {
                    verify(usuarioRepository, times(1)).findByEmail("novo@email.com");
                    System.out.println("✔ Verificação de email único realizada");
                },
                () -> {
                    verify(usuarioRepository, times(1)).save(novoUsuario);
                    System.out.println("✔ Usuário salvo com sucesso");
                }
        );

        System.out.println("✅ Teste concluído com sucesso");
    }

    @Test
    @DisplayName("❌ Registrar usuário existente - deve retornar erro")
    void registrarUsuario_UsuarioExistente_DeveRetornarErro() {
        System.out.println("Executando teste: Tentativa de registrar usuário existente");

        // Arrange
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setEmail("existente@email.com");

        when(usuarioRepository.findByEmail("existente@email.com")).thenReturn(usuarioExistente);

        // Act
        ResponseEntity<?> response = usuarioController.registrarUsuario(usuarioExistente);

        // Assert
        assertAll("Verificando resposta de usuário existente",
                () -> assertEquals(400, response.getStatusCodeValue(),
                        "Deveria retornar status 400 (Bad Request)"),
                () -> assertEquals("Usuário já existe.", response.getBody(),
                        "Mensagem de erro incorreta"),
                () -> {
                    verify(usuarioRepository, never()).save(any());
                    System.out.println("✔ Usuário não foi salvo (como esperado)");
                }
        );

        System.out.println("✅ Teste de usuário existente concluído");
    }

    @Test
    @DisplayName("🔑 Login com credenciais corretas - deve autenticar")
    void login_CredenciaisCorretas_DeveRetornarUsuario() {
        System.out.println("Executando teste: Login com credenciais válidas");

        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");
        usuario.setSenha("senha123");

        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(usuario);

        Usuario credenciais = new Usuario();
        credenciais.setEmail("test@email.com");
        credenciais.setSenha("senha123");

        // Act
        ResponseEntity<?> response = usuarioController.login(credenciais);

        // Assert
        assertAll("Verificando login bem-sucedido",
                () -> assertEquals(200, response.getStatusCodeValue(),
                        "Status code deveria ser 200 (OK)"),
                () -> assertEquals(usuario, response.getBody(),
                        "Deveria retornar o objeto usuário completo"),
                () -> {
                    verify(usuarioRepository, times(1)).findByEmail("test@email.com");
                    System.out.println("✔ Busca por email realizada");
                }
        );

        System.out.println("✅ Teste de autenticação bem-sucedida concluído");
    }

    @Test
    @DisplayName("🚫 Login com credenciais incorretas - deve negar acesso")
    void login_CredenciaisIncorretas_DeveRetornarNaoAutorizado() {
        System.out.println("Executando teste: Login com credenciais inválidas");

        // Arrange
        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(null);

        Usuario credenciais = new Usuario();
        credenciais.setEmail("test@email.com");
        credenciais.setSenha("senhaErrada");

        // Act
        ResponseEntity<?> response = usuarioController.login(credenciais);

        // Assert
        assertAll("Verificando tratamento de credenciais inválidas",
                () -> assertEquals(401, response.getStatusCodeValue(),
                        "Deveria retornar status 401 (Unauthorized)"),
                () -> assertEquals("Email ou senha incorretos.", response.getBody(),
                        "Mensagem de erro incorreta"),
                () -> {
                    verify(usuarioRepository, times(1)).findByEmail("test@email.com");
                    System.out.println("✔ Verificação de email realizada");
                }
        );

        System.out.println("✅ Teste de autenticação falha concluído");
    }

    @Test
    @DisplayName("🗑️ Deletar perfil existente - deve marcar como inativo")
    void deletarPerfil_UsuarioExistente_DeveMarcarComoInativo() {
        System.out.println("Executando teste: Exclusão lógica de usuário");

        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setActivo(true);

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        ResponseEntity<?> response = usuarioController.deletarPerfil(1);

        // Assert
        assertAll("Verificando exclusão lógica",
                () -> assertEquals(200, response.getStatusCodeValue(),
                        "Deveria retornar status 200 (OK)"),
                () -> assertEquals("Usuario marcado como inativo.", response.getBody(),
                        "Mensagem de sucesso incorreta"),
                () -> assertFalse(usuario.isActivo(),
                        "Flag 'activo' deveria ser false"),
                () -> {
                    verify(usuarioRepository, times(1)).save(usuario);
                    System.out.println("✔ Usuário atualizado no banco");
                }
        );

        System.out.println("✅ Teste de exclusão lógica concluído");
    }

    @Test
    @DisplayName("🔍 Buscar usuário por email - existente")
    void getUsuario_Existente_DeveRetornarUsuario() {
        System.out.println("Executando teste: Busca de usuário existente");

        // Arrange
        Usuario usuario = new Usuario();
        usuario.setEmail("test@email.com");

        when(usuarioRepository.findByEmail("test@email.com")).thenReturn(usuario);

        // Act
        ResponseEntity<Usuario> response = usuarioController.getUsuario("test@email.com");

        // Assert
        assertAll("Verificando busca por usuário",
                () -> assertEquals(200, response.getStatusCodeValue(),
                        "Deveria retornar status 200 (OK)"),
                () -> assertEquals(usuario, response.getBody(),
                        "Deveria retornar o usuário correto"),
                () -> {
                    verify(usuarioRepository, times(1)).findByEmail("test@email.com");
                    System.out.println("✔ Busca por email realizada");
                }
        );

        System.out.println("✅ Teste de busca bem-sucedida concluído");
    }
}