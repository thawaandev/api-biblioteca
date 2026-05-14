package com.thawanlc.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.thawanlc.biblioteca.dto.EmprestimoRequest;
import com.thawanlc.biblioteca.dto.EmprestimoResponse;
import com.thawanlc.biblioteca.entity.Emprestimo;
import com.thawanlc.biblioteca.entity.Livro;
import com.thawanlc.biblioteca.entity.Usuario;
import com.thawanlc.biblioteca.exceptions.LivroIndisponivelException;
import com.thawanlc.biblioteca.exceptions.LivroNaoEncontradoException;
import com.thawanlc.biblioteca.exceptions.UsuarioBloqueadoException;
import com.thawanlc.biblioteca.exceptions.UsuarioNaoEncontradoException;
import com.thawanlc.biblioteca.mapper.EmprestimoMapper;
import com.thawanlc.biblioteca.repository.EmprestimoRepository;
import com.thawanlc.biblioteca.repository.LivroRepository;
import com.thawanlc.biblioteca.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class EmprestimoService {
    
    private EmprestimoRepository emprestimoRepository;
    private LivroRepository livroRepository;
    private UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public EmprestimoResponse fazerEmprestimo(EmprestimoRequest request) {
        Livro livro = livroRepository.findById(request.livroId()).orElseThrow(
            () -> new LivroNaoEncontradoException("Livro não encontrado"));

        Usuario usuario = usuarioRepository.findById(request.usuarioId()).orElseThrow(
          () -> new UsuarioNaoEncontradoException("Usuário não Encontrado"));

        if(usuario.isBloqueado()) {
            throw new UsuarioBloqueadoException("Usuário Bloqueado não pode fazer empréstimos");
        }

        if(usuario.isMultado()) {
            throw new UsuarioBloqueadoException("Usuário tem um saldo pendente e por isso não pode fazer empréstimos");
        }

        if(!livro.isDisponivel()) {
            throw new LivroIndisponivelException("Livro indisponível para empréstimo");
        }
        livro.setDisponivel(false);

        Emprestimo emprestimo = EmprestimoMapper.toEntity(request);

        if(emprestimo.isCancelado()) {
            throw new LivroIndisponivelException("Livro indisponível para empréstimo");
        }

        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimoRepository.save(emprestimo);
        livroRepository.save(livro);
        return EmprestimoMapper.toResponse(emprestimo);
    }

    @Transactional
    public EmprestimoResponse devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId).orElseThrow(
            () -> new RuntimeException("Empréstimo não encontrado"));
        
        Usuario usuario = emprestimo.getUsuario();
        usuario.desbloquear();

        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);
        usuarioRepository.save(usuario);
        emprestimoRepository.delete(emprestimo);
        return EmprestimoMapper.toResponse(emprestimo);
    }

    public List<EmprestimoResponse> listarEmprestimos() {
        return emprestimoRepository.findAll().stream()
            .map(EmprestimoMapper::toResponse)
            .collect(Collectors.toList());
    }

    public void verificarEmprestimosExpirados() {
        List<Emprestimo> emprestimos = emprestimoRepository.findAll();
        for (Emprestimo emprestimo : emprestimos) {
            if(emprestimo.isAtrasado()) {
                Usuario usuario = emprestimo.getUsuario();
                usuario.aplicarPenalidade();
                System.out.println("Usuário["+usuario.getNome()+"] bloqueado e impedido de fazer empréstimos...");
                usuarioRepository.save(usuario);
                emprestimoRepository.save(emprestimo);
            }
        }
    }

}
