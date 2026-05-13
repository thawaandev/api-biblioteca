package com.thawanlc.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thawanlc.biblioteca.dto.LivroRequest;
import com.thawanlc.biblioteca.dto.LivroResponse;
import com.thawanlc.biblioteca.entity.Livro;
import com.thawanlc.biblioteca.mapper.LivroMapper;
import com.thawanlc.biblioteca.repository.LivroRepository;

@Service
public class LivroService {
    
    private LivroRepository livroRepository;
    private OpenLibraryService openLibraryService;

    public LivroService(LivroRepository livroRepository, OpenLibraryService openLibraryService) {
        this.livroRepository = livroRepository;
        this.openLibraryService = openLibraryService;
    }

    public LivroResponse criarLivro(LivroRequest request) {

        boolean isbnValido = openLibraryService.isbnExiste(request.isbn());
        if(!isbnValido) {
            throw new RuntimeException("ISBN inválido ou não encontrado na Open Library");
        }

        Livro livro = LivroMapper.toEntity(request);
        livroRepository.save(livro);
        return LivroMapper.toResponse(livro);
    }

    public List<LivroResponse> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
            .map(LivroMapper::toResponse)
            .toList();
    }



}
