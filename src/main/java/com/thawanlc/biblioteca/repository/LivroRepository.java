package com.thawanlc.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thawanlc.biblioteca.entity.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    
}
