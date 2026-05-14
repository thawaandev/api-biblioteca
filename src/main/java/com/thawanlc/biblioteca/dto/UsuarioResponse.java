package com.thawanlc.biblioteca.dto;

import java.util.List;

public record UsuarioResponse(
    Long id,
    String nome,
    boolean bloqueado,
    List<EmprestimoResponse> emprestimos
) {
    
}
