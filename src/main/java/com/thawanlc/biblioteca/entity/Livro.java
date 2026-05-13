package com.thawanlc.biblioteca.entity;

import com.thawanlc.biblioteca.validation.ISBN;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String autor;

    @ISBN
    @Column(unique = true)
    private String isbn;

    private boolean disponivel;


}
