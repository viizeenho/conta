package com.example.conta.model.DTO;


import com.example.conta.model.enums.MoedaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CambioDTO {
    private String numeroConta;
    private BigDecimal valor;
    private MoedaEnum moeda;
}

