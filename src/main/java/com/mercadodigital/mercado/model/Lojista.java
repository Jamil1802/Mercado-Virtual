package com.mercadodigital.mercado.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "lojistas")
public class Lojista extends Usuario {

    @OneToMany(mappedBy = "lojista", cascade = CascadeType.ALL)
    private List<LojaVirtual> lojas;

    public Lojista() {
        super();
    }

    public Lojista(int id, String nome, String email, String senha, String endereco) {
        super(id, nome, email, senha, endereco);
    }

    public List<LojaVirtual> getLojas() {
        return lojas;
    }

    public void setLojas(List<LojaVirtual> lojas) {
        this.lojas = lojas;
    }
}
