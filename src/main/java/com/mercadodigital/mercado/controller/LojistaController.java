package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Lojista;
import com.mercadodigital.mercado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lojistas")
public class LojistaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cadastrar lojista
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Lojista lojista) {
        if (usuarioRepository.findByEmail(lojista.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email já cadastrado.");
        }
        lojista.setActivo(true);
        usuarioRepository.save(lojista);
        return ResponseEntity.ok("Lojista cadastrado com sucesso.");
    }

    // Listar todos os lojistas ativos
    @GetMapping
    public List<Lojista> listarLojistasAtivos() {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u instanceof Lojista && u.isActivo())
                .map(u -> (Lojista) u)
                .toList();
    }

    // Desativar lojista
    @PutMapping("/{id}/desativar")
    public ResponseEntity<?> desativar(@PathVariable int id) {
        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || !(usuario instanceof Lojista)) {
            return ResponseEntity.notFound().build();
        }

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Lojista desativado.");
    }

    // Ativar lojista
    @PutMapping("/{id}/ativar")
    public ResponseEntity<?> ativar(@PathVariable int id) {
        var usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null || !(usuario instanceof Lojista)) {
            return ResponseEntity.notFound().build();
        }

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Lojista ativado.");
    }

    // Buscar por email
    @GetMapping("/buscar/{email}")
    public ResponseEntity<?> buscarPorEmail(@PathVariable String email) {
        var usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario instanceof Lojista) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.notFound().build();
    }
}
