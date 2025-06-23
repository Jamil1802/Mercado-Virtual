package com.mercadodigital.mercado.audth;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_auditoria")
public class UsuarioAuditoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ultima_modificacao")
    private LocalDateTime ultimaModificacao;

    private int usuarioId;
    private String acao;
    private LocalDateTime data;
    private String responsavel;

    public UsuarioAuditoria() {
    }

    public UsuarioAuditoria(Long id, int usuarioId, String acao, LocalDateTime data, String responsavel) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.acao = acao;
        this.data = data;
        this.responsavel = responsavel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
