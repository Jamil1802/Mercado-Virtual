package com.mercadodigital.mercado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "administradores")
public class Administrador extends Usuario {

    @OneToOne(mappedBy = "administrador", cascade = CascadeType.ALL)
    private LojaVirtual loja;

    public LojaVirtual getLoja() {
        return loja;
    }

    public void setLoja(LojaVirtual loja) {
        this.loja = loja;
    }
}
