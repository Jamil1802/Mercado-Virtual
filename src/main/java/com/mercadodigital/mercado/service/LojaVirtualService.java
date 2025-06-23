package com.mercadodigital.mercado.service;

import com.mercadodigital.mercado.model.LojaVirtual;
import com.mercadodigital.mercado.repository.LojaVirtualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaVirtualService {

    @Autowired
    private LojaVirtualRepository lojaRepository;

    public List<LojaVirtual> buscarLojasAtivasPorNome(String nome) {
        return lojaRepository.findByAtivaTrueAndNomeContainingIgnoreCase(nome);
    }

    public void desativarLoja(int id) {
        lojaRepository.atualizarStatus(id, false);
    }

    public Page<LojaVirtual> listarLojasAtivasPaginadas(Pageable pageable) {
        return lojaRepository.findLojasAtivas(pageable);
    }

    public Page<LojaVirtual> listarLojasAtivas(Pageable pageable) {
        return lojaRepository.findByAtiva(true, pageable);
    }
}
