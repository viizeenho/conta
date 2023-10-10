package com.example.conta.service;

import com.example.conta.Enum.Moeda;
import com.example.conta.model.Conta;
import com.example.conta.model.DTO.ContaDTO;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ContaService {
    List<Conta> findAllContas();
    Conta findContaById(Long id);
    Conta findByNumeroConta(String numeroConta);

    Conta createConta(ContaDTO conta);

    void realizarTransferencia(Conta contaOrigem, Conta contaDestino, double valor, Moeda moeda);

    void deleteConta(Long id);

}


