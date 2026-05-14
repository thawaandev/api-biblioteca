package com.thawanlc.biblioteca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thawanlc.biblioteca.service.EmprestimoService;

@Component
public class EmprestimoScheduler {

    @Autowired private EmprestimoService emprestimoService;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    public void verificarAtrasos() {
        emprestimoService.verificarEmprestimosExpirados();
        System.out.println("Verificando Livros atrasados.....");
    }
    
}
