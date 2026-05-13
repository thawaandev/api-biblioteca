package com.thawanlc.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thawanlc.biblioteca.entity.Emprestimo;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    
}
