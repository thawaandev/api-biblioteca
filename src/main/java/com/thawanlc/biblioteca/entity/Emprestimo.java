package com.thawanlc.biblioteca.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thawanlc.biblioteca.entity.enums.TipoStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Emprestimo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Livro livro;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataEmprestimo;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataDevolucao;

    @Enumerated(EnumType.STRING)
    private TipoStatus status;

    private boolean cancelado;

    public boolean isAtrasado() {
        if(dataDevolucao == null) throw new RuntimeException("Data de devolução não definida");
        if(LocalDateTime.now().isAfter(dataDevolucao)) {
            setStatus(TipoStatus.ATRASADO);
            System.out.println("Empréstimo " + id + " está atrasado e foi cancelado.");
            return true;
        }
        return false;
    }



}
