package com.thawanlc.biblioteca.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private BigDecimal saldo = BigDecimal.ZERO;
    private boolean bloqueado = false;
    private boolean multado = false;

    @JsonManagedReference
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Emprestimo> emprestimos = new ArrayList<>();


    public void bloquear() {
        this.bloqueado = true;
    }

    public void desbloquear() {
        this.bloqueado = false;
    }

    public void aplicarPenalidade() {
        bloquear();
        multar();
    }

    public void tirarPenalidade() {
        desbloquear();
        pagamentoMulta();
    }

    public void pagamentoMulta() {
        this.multado = false;
        this.saldo = BigDecimal.ZERO;
    }

    public void multar() {
        BigDecimal valorMulta = BigDecimal.valueOf(50.0);
        if(this.bloqueado) {
            this.multado = true;
            this.saldo = this.saldo.subtract(valorMulta);
        }
    }
    

}
