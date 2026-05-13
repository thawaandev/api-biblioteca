package com.thawanlc.biblioteca.mapper;

import java.time.LocalDateTime;

import com.thawanlc.biblioteca.dto.EmprestimoRequest;
import com.thawanlc.biblioteca.dto.EmprestimoResponse;
import com.thawanlc.biblioteca.entity.Emprestimo;

public class EmprestimoMapper {
    
    public static EmprestimoResponse toResponse(Emprestimo emprestimo) {
        return new EmprestimoResponse(
            emprestimo.getId(),
            emprestimo.getLivro().getTitulo(),
            emprestimo.getLivro().getIsbn(),
            emprestimo.getDataEmprestimo(),
            emprestimo.getDataDevolucao()
        );
    }

    public static Emprestimo toEntity(EmprestimoRequest request) {
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(LocalDateTime.now());
        emprestimo.setDataDevolucao(LocalDateTime.now().plusDays(7));
        return emprestimo;
    }

}
