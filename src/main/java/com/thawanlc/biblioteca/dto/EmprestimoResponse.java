package com.thawanlc.biblioteca.dto;

import java.time.LocalDateTime;

import com.thawanlc.biblioteca.entity.enums.TipoStatus;

public record EmprestimoResponse(
    Long id,
    String usuarioName,
    String livroName,
    String isbn,
    TipoStatus tipoStatus,
    LocalDateTime dataEmprestimo,
    LocalDateTime dataDevolucao
) {
    
}
