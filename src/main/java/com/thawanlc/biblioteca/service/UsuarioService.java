package com.thawanlc.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thawanlc.biblioteca.dto.UsuarioRequest;
import com.thawanlc.biblioteca.dto.UsuarioResponse;
import com.thawanlc.biblioteca.entity.Usuario;
import com.thawanlc.biblioteca.exceptions.UsuarioNaoEncontradoException;
import com.thawanlc.biblioteca.mapper.UsuarioMapper;
import com.thawanlc.biblioteca.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponse criarUsuario(UsuarioRequest request) {

        Usuario usuario = UsuarioMapper.toEntity(request);
        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);
    }

    @Transactional
    public UsuarioResponse pagarSaldoDevedor(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(
            () -> new UsuarioNaoEncontradoException("Usuário Inexistente")
        );
        usuario.tirarPenalidade();
        return UsuarioMapper.toResponse(usuario);
    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
            .map(UsuarioMapper::toResponse)
            .collect(Collectors.toList());
    }

}
