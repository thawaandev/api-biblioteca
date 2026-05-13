package com.thawanlc.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thawanlc.biblioteca.dto.LivroRequest;
import com.thawanlc.biblioteca.dto.LivroResponse;
import com.thawanlc.biblioteca.service.LivroService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;




@RestController
@RequestMapping("/livros")
public class LivroController {

    @Autowired private LivroService livroService;

    @PostMapping
    public ResponseEntity<LivroResponse> salvarLivro(@RequestBody LivroRequest request) {
        LivroResponse response = livroService.criarLivro(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public List<LivroResponse> listarLivros() {
        return livroService.listarLivros();
    }
    
    
    
}
