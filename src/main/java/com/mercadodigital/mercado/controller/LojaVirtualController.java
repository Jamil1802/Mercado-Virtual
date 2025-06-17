package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.LojaVirtual;
import com.mercadodigital.mercado.repository.LojaVirtualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lojas")
public class LojaVirtualController {

    @Autowired
    private LojaVirtualRepository lojaRepository;

    @PostMapping
    public ResponseEntity<LojaVirtual> criarLoja(@RequestBody LojaVirtual loja) {
        loja.setAtiva(true);
        LojaVirtual salva = lojaRepository.save(loja);
        return ResponseEntity.ok(salva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarLoja(@PathVariable int id, @RequestBody LojaVirtual atualizada) {
        LojaVirtual loja = lojaRepository.findById(id).orElse(null);
        if (loja == null || !loja.isAtiva()) {
            return ResponseEntity.notFound().build();
        }

        loja.setNome(atualizada.getNome());
        loja.setDescricao(atualizada.getDescricao());
        lojaRepository.save(loja);

        return ResponseEntity.ok("Loja atualizada.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLoja(@PathVariable int id) {
        LojaVirtual loja = lojaRepository.findById(id).orElse(null);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }

        loja.setAtiva(false);
        lojaRepository.save(loja);
        return ResponseEntity.ok("Loja marcada como inativa.");
    }

    @GetMapping
    public List<LojaVirtual> listarLojasAtivas() {
        return lojaRepository.findAll().stream()
                .filter(LojaVirtual::isAtiva)
                .toList();
    }

    @GetMapping("/admin")
    public List<LojaVirtual> listarTodasLojasParaAdmin() {
        return lojaRepository.findAll();
    }
}
