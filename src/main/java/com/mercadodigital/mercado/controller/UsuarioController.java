package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.LojaVirtual;
import com.mercadodigital.mercado.model.Usuario;
import com.mercadodigital.mercado.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Usuário já existe.");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário registrado com sucesso.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario credenciais) {
        Usuario usuario = usuarioRepository.findByEmail(credenciais.getEmail());
        if (usuario != null && usuario.getSenha().equals(credenciais.getSenha())) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(401).body("Email ou senha incorretos.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarPerfil(@PathVariable int id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        usuario.setAtiva(false);
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario marcado como inativo.");
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> editarPerfil(@PathVariable String email, @RequestBody Usuario atualizacao) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        if (usuario == null) return ResponseEntity.notFound().build();

        usuario.setNome(atualizacao.getNome());
        usuario.setSenha(atualizacao.getSenha());
        usuario.setEndereco(atualizacao.getEndereco());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok("Perfil atualizado.");
    }

    @GetMapping("/{email}")
    public ResponseEntity<Usuario> getUsuario(@PathVariable String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }
}
