package com.example.conta.service;

import com.example.conta.model.Conta;
import com.example.conta.model.DTO.ContaDTO;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ContaService {
    List<Conta> findAllContas();
    Conta findContaById(Long id);

    Conta createConta(ContaDTO conta);

    void deleteConta(Long id);

}


