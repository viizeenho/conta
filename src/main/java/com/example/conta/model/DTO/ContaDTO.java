package com.example.conta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter

public class ContaDTO {
    private String tipoConta;
    private String numeroConta;
    private Date dataAberturaConta;
    private double saldoDolar;
    private double saldoReal;
    private String status;
    private List<TitularContaDTO> titulares;
}

