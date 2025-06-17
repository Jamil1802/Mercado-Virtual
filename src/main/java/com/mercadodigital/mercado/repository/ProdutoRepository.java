package com.mercadodigital.mercado.repository;

import com.mercadodigital.mercado.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByIdLojaAndDisponivelTrue(int idLoja);
}
