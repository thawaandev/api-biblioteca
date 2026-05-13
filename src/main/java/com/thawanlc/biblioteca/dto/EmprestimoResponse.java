package com.thawanlc.biblioteca.dto;

import java.time.LocalDateTime;

public record EmprestimoResponse(
    Long id,
    String livroName,
    String isbn,
    LocalDateTime dataEmprestimo,
    LocalDateTime dataDevolucao
) {
    
}
