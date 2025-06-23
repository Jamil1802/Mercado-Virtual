package com.mercadodigital.mercado.service;

import com.mercadodigital.mercado.model.Usuario;
import com.mercadodigital.mercado.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministradorService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void desativarUsuario(int userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if ("ADMIN".equals(usuario.getTipoUsuario())) {
            throw new SecurityException("Não é permitido desativar administradores");
        }

        usuario.setActivo(false);
        usuarioRepository.save(usuario);
    }

    public void reativarUsuario(int userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        usuario.setActivo(true);
        usuarioRepository.save(usuario);
    }

    public void promoverParaAdmin(int userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if ("ADMIN".equals(usuario.getTipoUsuario())) {
            throw new IllegalStateException("O usuário já é administrador");
        }

        usuario.setTipoUsuario("ADMIN");
        usuarioRepository.save(usuario);
    }

    public void removerPrivilegiosAdmin(int userId) {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!"ADMIN".equals(usuario.getTipoUsuario())) {
            throw new IllegalStateException("O usuário não é administrador");
        }

        if (usuarioRepository.countByTipoUsuario("ADMIN") <= 1) {
            throw new SecurityException("Não é permitido remover o último administrador");
        }

        usuario.setTipoUsuario("USER");
        usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarUsuarios(Pageable pageable, String filtro, Boolean ativos) {
        if (filtro != null && ativos != null) {
            return usuarioRepository.findByNomeContainingIgnoreCaseAndActivo(filtro, ativos, pageable);
        } else if (filtro != null) {
            return usuarioRepository.findByNomeContainingIgnoreCase(filtro, pageable);
        } else if (ativos != null) {
            return usuarioRepository.findByActivo(ativos, pageable);
        }
        return usuarioRepository.findAll(pageable);
    }

    public List<Usuario> listarAdministradores() {
        return usuarioRepository.findByTipoUsuario("ADMIN");
    }

    public Usuario buscarUsuarioDetalhado(int userId) {
        return usuarioRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }
}