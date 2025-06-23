package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Administrador;
import com.mercadodigital.mercado.model.Usuario;
import com.mercadodigital.mercado.repository.UsuarioRepository;
import com.mercadodigital.mercado.service.AdministradorService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdministradorController {

    private final AdministradorService administradorService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public AdministradorController(AdministradorService administradorService,
                                   UsuarioRepository usuarioRepository) {
        this.administradorService = administradorService;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/desativar/{userId}")
    public ResponseEntity<String> desativarUsuario(
            @PathVariable int userId,
            @RequestHeader("X-Admin-ID") int adminId) {

        try {
            if (!isAdministrador(adminId)) {
                return ResponseEntity.status(403).body("Acesso negado");
            }

            administradorService.desativarUsuario(userId);
            return ResponseEntity.ok("Usuário desativado");

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    private boolean isAdministrador(int usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        return usuario != null && "ADMIN".equals(usuario.getTipoUsuario());
    }

    @PostMapping("/reativar/{userId}")
    public ResponseEntity<String> reativarUsuario(
            @PathVariable int userId,
            @RequestHeader("X-Admin-ID") int adminId) {

        if (!isAdministrador(adminId)) {
            return ResponseEntity.status(403).body("Acesso negado");
        }

        try {
            administradorService.reativarUsuario(userId);
            return ResponseEntity.ok("Usuário reativado com sucesso");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/promover/{userId}")
    public ResponseEntity<String> promoverParaAdmin(
            @PathVariable int userId,
            @RequestHeader("X-Admin-ID") int adminId) {

        if (!isAdministrador(adminId)) {
            return ResponseEntity.status(403).body("Acesso negado");
        }

        try {
            administradorService.promoverParaAdmin(userId);
            return ResponseEntity.ok("Usuário promovido a administrador");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public Page<Usuario> listarUsuarios(
            @RequestParam(required = false) String filtro,
            @RequestParam(required = false) Boolean ativos,
            Pageable pageable) {

        return administradorService.listarUsuarios(pageable, filtro, ativos);
    }

    @GetMapping("/administradores")
    public ResponseEntity<List<Usuario>> listarAdministradores() {
        List<Usuario> administradores = administradorService.listarAdministradores();
        return ResponseEntity.ok(administradores);
    }

    @GetMapping("/usuarios/{userId}")
    public ResponseEntity<Usuario> buscarUsuarioDetalhado(@PathVariable int userId) {
        try {
            Usuario usuario = administradorService.buscarUsuarioDetalhado(userId);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/remover-admin/{userId}")
    public ResponseEntity<String> removerPrivilegiosAdmin(
            @PathVariable int userId,
            @RequestHeader("X-Admin-ID") int adminId) {

        if (!isAdministrador(adminId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
        }

        try {
            administradorService.removerPrivilegiosAdmin(userId);
            return ResponseEntity.ok("Privilégios de administrador removidos");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    private boolean isAdministrador(int usuarioId) {
//        return usuarioRepository.findById(usuarioId)
//                .map(u -> "ADMIN".equals(u.getTipoUsuario()))
//                .orElse(false);
//    }
}