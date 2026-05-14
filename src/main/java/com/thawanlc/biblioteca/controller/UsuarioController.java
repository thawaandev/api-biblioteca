package com.thawanlc.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thawanlc.biblioteca.dto.UsuarioRequest;
import com.thawanlc.biblioteca.dto.UsuarioResponse;
import com.thawanlc.biblioteca.service.UsuarioService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    
    @Autowired private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> postUser(@Valid @RequestBody UsuarioRequest request) {
        usuarioService.criarUsuario(request);
        return ResponseEntity.ok().body(request);
    }

    @PostMapping("/pagamento/{id}")
    public ResponseEntity<?> postPaymentLibrary(@PathVariable Long id) {
        usuarioService.pagarSaldoDevedor(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    

    @GetMapping
    public List<UsuarioResponse> getAllUsers() {
        return usuarioService.listarTodos();
    }
    
    

}
