package com.thawanlc.biblioteca.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.thawanlc.biblioteca.dto.LivroRequest;
import com.thawanlc.biblioteca.dto.LivroResponse;
import com.thawanlc.biblioteca.entity.Livro;
import com.thawanlc.biblioteca.exceptions.IsbnNaoEncontradoException;
import com.thawanlc.biblioteca.exceptions.LivroNaoEncontradoException;
import com.thawanlc.biblioteca.mapper.LivroMapper;
import com.thawanlc.biblioteca.repository.LivroRepository;
import com.thawanlc.biblioteca.service.OpenLibraryService.OpenLibraryResponse;

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
            throw new IsbnNaoEncontradoException(request.isbn() + " não encontrado na Open Library");
        }

        OpenLibraryResponse externalBook = openLibraryService.getBookDetails(request.isbn());
    
        Livro livro = LivroMapper.toEntity(request);
        livro.setTitulo(externalBook != null ? externalBook.getTitle() : "Titulo Desconecido");
        livro.setAutor(externalBook != null ? openLibraryService.getAuthorName(externalBook) : "Autor desconhecido");
        livroRepository.save(livro);
        return LivroMapper.toResponse(livro);
    }

    public List<LivroResponse> listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        return livros.stream()
            .map(LivroMapper::toResponse)
            .toList();
    }

    public void deletarLivroPorId(Long id) {
        Livro livro = livroRepository.findById(id).orElseThrow(
            () -> new LivroNaoEncontradoException("Livro não encontrado"));
        livro.setDisponivel(false);
    }


}
