package com.mercadodigital.mercado.repository;

import com.mercadodigital.mercado.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
    Pagamento findByIdPedido(int idPedido);
}
