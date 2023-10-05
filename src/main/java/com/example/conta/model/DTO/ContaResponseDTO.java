package com.example.conta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@AllArgsConstructor
@Getter
@Setter

public class ContaResponseDTO {
    private Long id;
    private String tipoConta;
    private String numeroConta;
    private Date dataAberturaConta;
    private double saldoDolar;
    private double saldoReal;
    private String status;

}