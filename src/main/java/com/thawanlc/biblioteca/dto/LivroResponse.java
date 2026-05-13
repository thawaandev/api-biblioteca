package com.thawanlc.biblioteca.dto;

public record LivroResponse(
    Long id,
    String titulo,
    String autor,
    String isbn,
    boolean disponivel
) {
    
}
