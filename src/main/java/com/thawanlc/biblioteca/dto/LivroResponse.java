package com.thawanlc.biblioteca.dto;

public record LivroResponse(
    Long id,
    String titulo,
    String isbn,
    boolean disponivel
) {
    
}
