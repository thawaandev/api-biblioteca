package com.thawanlc.biblioteca.exceptions;

public class IsbnNaoEncontradoException extends RuntimeException {
    public IsbnNaoEncontradoException(String isbn) {
        super("ISBN não encontrado: " + isbn);
    }
    
}
