package com.thawanlc.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thawanlc.biblioteca.dto.EmprestimoRequest;
import com.thawanlc.biblioteca.dto.EmprestimoResponse;
import com.thawanlc.biblioteca.entity.Emprestimo;
import com.thawanlc.biblioteca.entity.Livro;
import com.thawanlc.biblioteca.exceptions.LivroIndisponivelException;
import com.thawanlc.biblioteca.exceptions.LivroNaoEncontradoException;
import com.thawanlc.biblioteca.mapper.EmprestimoMapper;
import com.thawanlc.biblioteca.repository.EmprestimoRepository;
import com.thawanlc.biblioteca.repository.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {
    
    private EmprestimoRepository emprestimoRepository;
    private LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public EmprestimoResponse fazerEmprestimo(EmprestimoRequest request) {
        Livro livro = livroRepository.findById(request.livroId()).orElseThrow(
            () -> new LivroNaoEncontradoException("Livro não encontrado"));

        if(!livro.isDisponivel()) {
            throw new LivroIndisponivelException("Livro indisponível para empréstimo");
        }
        livro.setDisponivel(false);

        Emprestimo emprestimo = EmprestimoMapper.toEntity(request);
        emprestimo.setLivro(livro);
        emprestimoRepository.save(emprestimo);
        livroRepository.save(livro);
        return EmprestimoMapper.toResponse(emprestimo);
    }

    @Transactional
    public EmprestimoResponse devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId).orElseThrow(
            () -> new RuntimeException("Empréstimo não encontrado"));
        
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);
        emprestimoRepository.delete(emprestimo);
        return EmprestimoMapper.toResponse(emprestimo);
    }

    public List<EmprestimoResponse> listarEmprestimos() {
        return emprestimoRepository.findAll().stream()
            .map(EmprestimoMapper::toResponse)
            .collect(Collectors.toList());
    }

}
