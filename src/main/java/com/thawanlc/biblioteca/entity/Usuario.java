package com.thawanlc.biblioteca.entity;

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
    private Double saldo = 0.0;
    private boolean bloqueado = false;

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

    public void multar() {
        double valorMulta = 50.0;
        if(this.bloqueado) {
            this.saldo -= valorMulta;
        }
    }
    

}
