package com.thawanlc.biblioteca.dto;

public record LivroRequest(
    String titulo,
    Long autorId,
    String isbn
) {
    
}
