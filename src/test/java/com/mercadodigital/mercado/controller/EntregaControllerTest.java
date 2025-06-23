package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Entrega;
import com.mercadodigital.mercado.repository.EntregaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntregaControllerTest {

    @Mock
    private EntregaRepository entregaRepository;

    @InjectMocks
    private EntregaController entregaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarEntregas_DeveRetornarListaDeEntregas() {
        // Arrange
        Entrega entrega1 = new Entrega();
        Entrega entrega2 = new Entrega();
        List<Entrega> entregas = Arrays.asList(entrega1, entrega2);

        when(entregaRepository.findAll()).thenReturn(entregas);

        // Act
        List<Entrega> resultado = entregaController.listarEntregas();

        // Assert
        assertEquals(2, resultado.size());
        verify(entregaRepository, times(1)).findAll();
    }

    @Test
    void criarEntrega_DeveSalvarERetornarEntrega() {
        // Arrange
        Entrega entrega = new Entrega();
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega);

        // Act
        ResponseEntity<Entrega> response = entregaController.criarEntrega(entrega);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        verify(entregaRepository, times(1)).save(entrega);
    }

    @Test
    void atualizarStatus_QuandoEntregaExiste_DeveAtualizarStatus() {
        // Arrange
        Entrega entrega = new Entrega();
        when(entregaRepository.findById(1)).thenReturn(Optional.of(entrega));
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega);

        // Act
        ResponseEntity<?> response = entregaController.atualizarStatus(1, "ENTREGUE");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(entrega).atualizarStatus("ENTREGUE");
        verify(entregaRepository, times(1)).save(entrega);
    }

    @Test
    void atualizarStatus_QuandoEntregaNaoExiste_DeveRetornarNotFound() {
        // Arrange
        when(entregaRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = entregaController.atualizarStatus(1, "ENTREGUE");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(entregaRepository, never()).save(any());
    }

    @Test
    void registrarRastreamento_QuandoEntregaExiste_DeveRegistrarCodigo() {
        // Arrange
        Entrega entrega = new Entrega();
        when(entregaRepository.findById(1)).thenReturn(Optional.of(entrega));
        when(entregaRepository.save(any(Entrega.class))).thenReturn(entrega);

        // Act
        ResponseEntity<?> response = entregaController.registrarRastreamento(1, "COD123");

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(entrega).registrarEnvio("COD123");
        verify(entregaRepository, times(1)).save(entrega);
    }

    @Test
    void registrarRastreamento_QuandoEntregaNaoExiste_DeveRetornarNotFound() {
        // Arrange
        when(entregaRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = entregaController.registrarRastreamento(1, "COD123");

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(entregaRepository, never()).save(any());
    }
}