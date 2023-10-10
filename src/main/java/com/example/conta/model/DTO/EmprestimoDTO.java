package com.example.conta.model.DTO;

import com.example.conta.model.enums.MoedaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmprestimoDTO {
    private double valor;
    private MoedaEnum moeda;
    private Date dataContratacao;
}