package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.enums.StatusPedido;
import com.mercadodigital.mercado.model.Pedido;
import com.mercadodigital.mercado.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody Pedido pedido) {
        pedido.setStatus(StatusPedido.PENDENTE);
        Pedido salvo = pedidoRepository.save(pedido);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> atualizarStatus(@PathVariable int id, @RequestParam String status) {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            StatusPedido novoStatus = StatusPedido.valueOf(status.toUpperCase());
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);
            return ResponseEntity.ok("Status atualizado para: " + novoStatus);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Status inválido.");
        }
    }

    @GetMapping
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable int id) {
        return pedidoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}