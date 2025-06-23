package com.mercadodigital.mercado.repository;

import com.mercadodigital.mercado.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByEmail(String email);
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Usuario> findByActivo(Boolean ativo, Pageable pageable);
    Page<Usuario> findByNomeContainingIgnoreCaseAndActivo(String nome, Boolean ativo, Pageable pageable);
    List<Usuario> findByTipoUsuario(String tipoUsuario);
    long countByTipoUsuario(String tipoUsuario);
}
