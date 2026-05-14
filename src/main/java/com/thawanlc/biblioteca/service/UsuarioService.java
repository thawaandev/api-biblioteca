package com.thawanlc.biblioteca.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thawanlc.biblioteca.dto.UsuarioRequest;
import com.thawanlc.biblioteca.dto.UsuarioResponse;
import com.thawanlc.biblioteca.entity.Usuario;
import com.thawanlc.biblioteca.mapper.UsuarioMapper;
import com.thawanlc.biblioteca.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioResponse criarUsuario(UsuarioRequest request) {

        Usuario usuario = UsuarioMapper.toEntity(request);
        usuarioRepository.save(usuario);
        return UsuarioMapper.toResponse(usuario);

    }

    public List<UsuarioResponse> listarTodos() {
        return usuarioRepository.findAll().stream()
            .map(UsuarioMapper::toResponse)
            .collect(Collectors.toList());
    }

}
