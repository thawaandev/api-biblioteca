package com.thawanlc.biblioteca.dto;

import java.math.BigDecimal;
import java.util.List;

public record UsuarioResponse(
    Long id,
    String nome,
    BigDecimal valorMulta,
    boolean bloqueado,
    boolean multado,
    List<EmprestimoResponse> emprestimos
) {
    
}
