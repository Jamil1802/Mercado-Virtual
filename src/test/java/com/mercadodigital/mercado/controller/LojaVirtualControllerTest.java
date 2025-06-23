package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.LojaVirtual;
import com.mercadodigital.mercado.repository.LojaVirtualRepository;
import com.mercadodigital.mercado.service.LojaVirtualService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;

class LojaVirtualControllerTest {

    @Mock
    private LojaVirtualRepository lojaRepository;

    @Mock
    private LojaVirtualService lojaService;

    @InjectMocks
    private LojaVirtualController lojaVirtualController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<LojaVirtual> gerarMuitasLojas(int quantidade) {
        Random random = new Random();
        return IntStream.rangeClosed(1, quantidade)
                .mapToObj(i -> {
                    LojaVirtual loja = new LojaVirtual();
                    loja.setId(i);
                    loja.setNome("Loja " + i);
                    loja.setDescricao("Descrição da loja " + i);
                    loja.setAtiva(random.nextBoolean());
                    return loja;
                })
                .collect(Collectors.toList());
    }

    List<LojaVirtual> lojasAtivas = List.of(
            new LojaVirtual(1, "Loja 1", "Desc 1"),
            new LojaVirtual(2, "Loja 2", "Desc 2")
    );

    Pageable pageable = PageRequest.of(0, 2); // página 0, tamanho 2

    Page<LojaVirtual> page = new PageImpl<>(lojasAtivas, pageable, lojasAtivas.size());

    @Test
    @DisplayName("🛒 Criar loja - Sucesso")
    void criarLoja_Sucesso() {
        // Arrange
        LojaVirtual novaLoja = new LojaVirtual();
        novaLoja.setNome("Nova Loja");
        novaLoja.setDescricao("Descrição da loja");

        LojaVirtual lojaSalva = new LojaVirtual();
        lojaSalva.setId(1);
        lojaSalva.setNome(novaLoja.getNome());
        lojaSalva.setDescricao(novaLoja.getDescricao());
        lojaSalva.setAtiva(true);

        when(lojaRepository.save(any(LojaVirtual.class))).thenReturn(lojaSalva);

        // Act
        ResponseEntity<LojaVirtual> response = lojaVirtualController.criarLoja(novaLoja);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getId());
        assertTrue(response.getBody().isAtiva());
        verify(lojaRepository, times(1)).save(novaLoja);
    }

    @Test
    @DisplayName("🛒 Editar loja - Sucesso")
    void editarLoja_Sucesso() {
        // Arrange
        int id = 1;
        LojaVirtual lojaExistente = new LojaVirtual();
        lojaExistente.setId(id);
        lojaExistente.setNome("Loja Antiga");
        lojaExistente.setAtiva(true);

        LojaVirtual atualizada = new LojaVirtual();
        atualizada.setNome("Loja Atualizada");
        atualizada.setDescricao("Nova descrição");

        when(lojaRepository.findById(id)).thenReturn(Optional.of(lojaExistente));
        when(lojaRepository.save(any(LojaVirtual.class))).thenReturn(lojaExistente);

        // Act
        ResponseEntity<?> response = lojaVirtualController.editarLoja(id, atualizada);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Loja atualizada.", response.getBody());
        assertEquals("Loja Atualizada", lojaExistente.getNome());
        assertEquals("Nova descrição", lojaExistente.getDescricao());
        verify(lojaRepository, times(1)).save(lojaExistente);
    }

    @Test
    @DisplayName("🛒 Editar loja - Não encontrada")
    void editarLoja_NaoEncontrada() {
        // Arrange
        int id = 99;
        when(lojaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = lojaVirtualController.editarLoja(id, new LojaVirtual());

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(lojaRepository, never()).save(any());
    }

    @Test
    @DisplayName("🛒 Editar loja - Inativa")
    void editarLoja_Inativa() {
        // Arrange
        int id = 1;
        LojaVirtual lojaInativa = new LojaVirtual();
        lojaInativa.setId(id);
        lojaInativa.setAtiva(false);

        when(lojaRepository.findById(id)).thenReturn(Optional.of(lojaInativa));

        // Act
        ResponseEntity<?> response = lojaVirtualController.editarLoja(id, new LojaVirtual());

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(lojaRepository, never()).save(any());
    }

    @Test
    @DisplayName("🛒 Deletar loja - Sucesso")
    void deletarLoja_Sucesso() {
        // Arrange
        int id = 1;
        LojaVirtual loja = new LojaVirtual();
        loja.setId(id);
        loja.setAtiva(true);

        when(lojaRepository.findById(id)).thenReturn(Optional.of(loja));
        when(lojaRepository.save(any(LojaVirtual.class))).thenReturn(loja);

        // Act
        ResponseEntity<?> response = lojaVirtualController.deletarLoja(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Loja removida com sucesso.", response.getBody());
        assertFalse(loja.isAtiva());
        verify(lojaRepository, times(1)).save(loja);
    }

    @Test
    @DisplayName("🛒 Deletar loja - Não encontrada")
    void deletarLoja_NaoEncontrada() {
        // Arrange
        int id = 99;
        when(lojaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = lojaVirtualController.deletarLoja(id);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(lojaRepository, never()).save(any());
    }

    @Test
    @DisplayName("🛒 Listar lojas ativas - Sucesso")
    void listarLojasAtivas_Sucesso() {
        // Arrange
        LojaVirtual loja1 = new LojaVirtual();
        loja1.setId(1);
        loja1.setAtiva(true);

        LojaVirtual loja2 = new LojaVirtual();
        loja2.setId(2);
        loja2.setAtiva(false);

        LojaVirtual loja3 = new LojaVirtual();
        loja3.setId(3);
        loja3.setAtiva(true);

        when(lojaRepository.findAll()).thenReturn(List.of(loja1, loja2, loja3));

        // Act
        List<LojaVirtual> resultado = lojaVirtualController.listarLojasAtivas();

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(LojaVirtual::isAtiva));
    }

    @Test
    @DisplayName("🛒 Listar todas lojas (admin) - Sucesso")
    void listarTodasLojasParaAdmin_Sucesso() {
        // Arrange
        LojaVirtual loja1 = new LojaVirtual();
        loja1.setId(1);
        loja1.setAtiva(true);

        LojaVirtual loja2 = new LojaVirtual();
        loja2.setId(2);
        loja2.setAtiva(false);

        when(lojaRepository.findAll()).thenReturn(List.of(loja1, loja2));

        // Act
        List<LojaVirtual> resultado = lojaVirtualController.listarTodasLojasParaAdmin();

        // Assert
        assertEquals(2, resultado.size());
        verify(lojaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Validação de Entrada")
    void criarLoja_DadosInvalidos_DeveRetornarBadRequest() {
        LojaVirtual lojaInvalida = new LojaVirtual(); // Sem nome
        // Configurar validação para falhar

        ResponseEntity<LojaVirtual> response = lojaVirtualController.criarLoja(lojaInvalida);

        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Listar lojas ativas paginadas - Sucesso")
    void listarLojasAtivasPaginadas_Sucesso() {
        Pageable pageable = PageRequest.of(0, 10);
        List<LojaVirtual> lojas = Arrays.asList(
                new LojaVirtual(1, "Loja 1", "Desc 1"),
                new LojaVirtual(2, "Loja 2", "Desc 2")
        );
        Page<LojaVirtual> page = new PageImpl<>(lojas, pageable, lojas.size());

        when(lojaService.listarLojasAtivas(pageable)).thenReturn(page);

        ResponseEntity<Page<LojaVirtual>> response = lojaVirtualController.listarLojasAtivas(pageable);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getTotalElements());
        verify(lojaService, times(1)).listarLojasAtivas(pageable);
    }

    @Test
    @DisplayName("Teste de performance com 1.000 lojas")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void listarTodasLojas_Performance_Rapido() {
        // Arrange
        List<LojaVirtual> muitasLojas = gerarMuitasLojas(1000);
        when(lojaRepository.findAll()).thenReturn(muitasLojas);

        // Act
        List<LojaVirtual> resultado = lojaVirtualController.listarTodasLojasParaAdmin();

        // Assert
        assertEquals(1000, resultado.size());
    }

    @Test
    @DisplayName("Teste de performance com 10.000 lojas")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void listarTodasLojas_Performance_Medio() {
        // Arrange
        List<LojaVirtual> muitasLojas = gerarMuitasLojas(10000);
        when(lojaRepository.findAll()).thenReturn(muitasLojas);

        // Act
        List<LojaVirtual> resultado = lojaVirtualController.listarTodasLojasParaAdmin();

        // Assert
        assertEquals(10000, resultado.size());
    }

    @Test
    @DisplayName("Teste de performance com 100.000 lojas")
    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    void listarTodasLojas_Performance_GrandeVolume() {
        // Arrange
        List<LojaVirtual> muitasLojas = gerarMuitasLojas(100000);
        when(lojaRepository.findAll()).thenReturn(muitasLojas);

        // Act
        List<LojaVirtual> resultado = lojaVirtualController.listarTodasLojasParaAdmin();

        // Assert
        assertEquals(100000, resultado.size());
    }

}