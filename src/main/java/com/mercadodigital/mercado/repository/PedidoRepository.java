package com.mercadodigital.mercado.repository;

import com.mercadodigital.mercado.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
}
