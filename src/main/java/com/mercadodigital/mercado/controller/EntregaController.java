package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Entrega;
import com.mercadodigital.mercado.repository.EntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entregas")
public class EntregaController {

    @Autowired
    private EntregaRepository entregaRepository;

    @GetMapping
    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Entrega> criarEntrega(@RequestBody Entrega entrega) {
        Entrega salva = entregaRepository.save(entrega);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable int id, @RequestBody String novoStatus) {
        Entrega entrega = entregaRepository.findById(id).orElse(null);
        if (entrega == null) return ResponseEntity.notFound().build();

        entrega.atualizarStatus(novoStatus);
        entregaRepository.save(entrega);
        return ResponseEntity.ok("EStado actualizado.");
    }

    @PutMapping("/{id}/rastreio")
    public ResponseEntity<?> registrarRastreamento(@PathVariable int id, @RequestBody String codigo) {
        Entrega entrega = entregaRepository.findById(id).orElse(null);
        if (entrega == null) return ResponseEntity.notFound().build();

        entrega.registrarEnvio(codigo);
        entregaRepository.save(entrega);
        return ResponseEntity.ok("Código de rastreamento registrado.");
    }
}