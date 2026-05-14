package com.thawanlc.biblioteca.mapper;

import java.util.stream.Collectors;

import com.thawanlc.biblioteca.dto.UsuarioRequest;
import com.thawanlc.biblioteca.dto.UsuarioResponse;
import com.thawanlc.biblioteca.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioResponse toResponse(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getNome(),
            usuario.isBloqueado(),
            usuario.getEmprestimos()
                .stream()
                .map(EmprestimoMapper::toResponse)
                .collect(Collectors.toList())
        );
    }

    public static Usuario toEntity(UsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        return usuario;
    }

}
