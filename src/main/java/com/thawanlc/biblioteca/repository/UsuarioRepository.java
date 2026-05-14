package com.thawanlc.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thawanlc.biblioteca.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
}
