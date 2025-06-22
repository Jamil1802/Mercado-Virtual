package com.mercadodigital.mercado.controller;

import com.mercadodigital.mercado.model.Produto;
import com.mercadodigital.mercado.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity<Produto> adicionarProduto(@RequestBody Produto produto) {
        produto.setDisponivel(true);
        Produto salvo = produtoRepository.save(produto);
        return ResponseEntity.ok(salvo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editarProduto(@PathVariable int id, @RequestBody Produto dados) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null || !produto.isDisponivel()) {
            return ResponseEntity.notFound().build();
        }

        produto.setNome(dados.getNome());
        produto.setDescricao(dados.getDescricao());
        produto.setPreco(dados.getPreco());
        produto.setEstoque(dados.getEstoque());

        produtoRepository.save(produto);
        return ResponseEntity.ok("Produto actualizado.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerProduto(@PathVariable int id) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null) {
            return ResponseEntity.notFound().build();
        }

        produto.setDisponivel(false);
        produtoRepository.save(produto);
        return ResponseEntity.ok("Produto removido com sucesso.");
    }

    @GetMapping("/loja/{idLoja}")
    public List<Produto> listarProdutosDisponiveisPorLoja(@PathVariable int idLoja) {
        return produtoRepository.findByIdLojaAndDisponivelTrue(idLoja);
    }

    @GetMapping("/admin")
    public List<Produto> listarTodosProdutosParaAdmin() {
        return produtoRepository.findAll();
    }

    @GetMapping
    public List<Produto> listarProdutosDisponiveis() {
        return produtoRepository.findAll().stream()
                .filter(Produto::isDisponivel)
                .toList();
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<?> atualizarEstoque(@PathVariable int id, @RequestBody Produto dados) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null || !produto.isDisponivel()) {
            return ResponseEntity.notFound().build();
        }

        produto.setEstoque(dados.getEstoque());
        produtoRepository.save(produto);
        return ResponseEntity.ok("Estoque atualizado.");
    }

    @PatchMapping("/{id}/preco")
    public ResponseEntity<?> atualizarPreco(@PathVariable int id, @RequestBody Produto dados) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        if (produto == null || !produto.isDisponivel()) {
            return ResponseEntity.notFound().build();
        }

        produto.setPreco(dados.getPreco());
        produtoRepository.save(produto);
        return ResponseEntity.ok("Preço atualizado.");

    }
}
