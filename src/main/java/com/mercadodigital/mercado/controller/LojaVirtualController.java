package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.LojaVirtual;
import com.mercadodigital.mercado.repository.LojaVirtualRepository;
import com.mercadodigital.mercado.service.LojaVirtualService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private LojaVirtualService lojaVirtualService;

    //Metodo para adicionar lojas
    @PostMapping
    public ResponseEntity<LojaVirtual> criarLoja(@RequestBody LojaVirtual loja) {
        loja.setAtiva(true);
        LojaVirtual salva = lojaRepository.save(loja);
        return ResponseEntity.ok(salva);
    }

    //Metodo para editar lojas
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

    //Metodo para remover lojas
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarLoja(@PathVariable int id) {
        LojaVirtual loja = lojaRepository.findById(id).orElse(null);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }

        loja.setAtiva(false);
        lojaRepository.save(loja);
        return ResponseEntity.ok("Loja removida com sucesso.");
    }

    //Metodo para listar lojas para usuarios normais
    @GetMapping
    public List<LojaVirtual> listarLojasAtivas() {
        return lojaRepository.findAll().stream()
                .filter(LojaVirtual::isAtiva)
                .toList();
    }

    //Metodo para listar lojas para usuarios adminitradores
    @GetMapping("/admin")
    public List<LojaVirtual> listarTodasLojasParaAdmin() {
        return lojaRepository.findAll();
    }

    @GetMapping("/ativas-paginadas")
    public ResponseEntity<Page<LojaVirtual>> listarLojasAtivas(Pageable pageable) {
        Page<LojaVirtual> lojasPage = lojaVirtualService.listarLojasAtivas(pageable);
        return ResponseEntity.ok(lojasPage);
    }
}
