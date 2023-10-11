package com.example.conta.service;

import com.example.conta.model.Conta;
import com.example.conta.model.Emprestimo;
import com.example.conta.model.DTO.CambioDTO;
import com.example.conta.model.DTO.ContaDTO;
import com.example.conta.model.DTO.EmprestimoDTO;
import com.example.conta.model.enums.MoedaEnum;

import java.math.BigDecimal;
import java.util.List;

public interface ContaService {
    List<Conta> findAllContas();
    Conta findContaById(Long id);
    Conta findByNumeroConta(String numeroConta);

    Conta createConta(ContaDTO conta);

    void realizarTransferencia(Conta contaOrigem, Conta contaDestino, double valor, MoedaEnum moeda);

    Emprestimo solicitarEmprestimo(Long idConta, EmprestimoDTO emprestimoDTO) throws Exception;
    
    void deleteConta(Long id);

    CambioDTO solicitarCambio (CambioDTO cambioDTO);

}


