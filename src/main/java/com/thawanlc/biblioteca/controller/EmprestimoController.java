package com.thawanlc.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thawanlc.biblioteca.dto.EmprestimoRequest;
import com.thawanlc.biblioteca.dto.EmprestimoResponse;
import com.thawanlc.biblioteca.service.EmprestimoService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    
    @Autowired private EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<?> salvarEmprestimo(@Valid @RequestBody EmprestimoRequest req) {
        emprestimoService.fazerEmprestimo(req);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/devolver/{id}")
    public ResponseEntity<?> devolverLivro(@PathVariable Long id) {
        emprestimoService.devolverLivro(id);
        return ResponseEntity.ok().build();
    }


    @GetMapping    
    public List<EmprestimoResponse> listarEmprestimos() {
        return emprestimoService.listarEmprestimos();
    }

}
