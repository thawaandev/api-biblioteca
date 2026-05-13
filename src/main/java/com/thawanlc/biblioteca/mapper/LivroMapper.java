package com.thawanlc.biblioteca.mapper;

import org.springframework.stereotype.Component;

import com.thawanlc.biblioteca.dto.LivroRequest;
import com.thawanlc.biblioteca.dto.LivroResponse;
import com.thawanlc.biblioteca.entity.Livro;

@Component
public class LivroMapper {
    
    public static LivroResponse toResponse(Livro livro) {
        return new LivroResponse(
            livro.getId(),
            livro.getTitulo(),
            livro.getIsbn(),
            livro.isDisponivel()
        );
    }

    public static Livro toEntity(LivroRequest request) {
        Livro livro = new Livro();
        livro.setTitulo(request.titulo());
        livro.setIsbn(request.isbn());
        livro.setDisponivel(true);
        return livro;
    }

}
