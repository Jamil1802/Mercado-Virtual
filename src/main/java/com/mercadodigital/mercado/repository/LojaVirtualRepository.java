package com.mercadodigital.mercado.repository;

import com.mercadodigital.mercado.model.LojaVirtual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LojaVirtualRepository extends JpaRepository<LojaVirtual, Integer> {
    List<LojaVirtual> findByAtivaTrue();
    List<LojaVirtual> findByNomeContainingIgnoreCase(String nome);
    List<LojaVirtual> findByAtivaTrueAndNomeContainingIgnoreCase(String nome);
    @Modifying
    @Query("UPDATE LojaVirtual l SET l.ativa = :status WHERE l.id = :id")
    int atualizarStatus(@Param("id") int id, @Param("status") boolean status);
    @Query("SELECT l FROM LojaVirtual l WHERE l.id = :id")
    Optional<LojaVirtual> findByIdIncluindoInativas(@Param("id") int id);
    long countByAtiva(boolean ativa);
    @Query("SELECT l FROM LojaVirtual l WHERE l.ativa = true")
    Page<LojaVirtual> findLojasAtivas(Pageable pageable);

    Page<LojaVirtual> findByAtiva(boolean ativa, Pageable pageable);
}