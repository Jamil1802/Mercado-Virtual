package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Pagamento;
import com.mercadodigital.mercado.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @PostMapping
    public ResponseEntity<?> criarPagamento(@RequestBody Pagamento pagamento) {
        pagamento.setStatus("Aguardando");
        Pagamento salvo = pagamentoRepository.save(pagamento);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}/processar")
    public ResponseEntity<?> processarPagamento(@PathVariable int id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElse(null);
        if (pagamento == null) {
            return ResponseEntity.notFound().build();
        }

        pagamento.processar();
        pagamentoRepository.save(pagamento);
        return ResponseEntity.ok("Pagamento processado com sucesso.");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPagamento(@PathVariable int id) {
        return pagamentoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Pagamento> listarTodos() {
        return pagamentoRepository.findAll();
    }

}
