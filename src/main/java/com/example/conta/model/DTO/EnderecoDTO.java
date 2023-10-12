package com.example.conta.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
 @AllArgsConstructor
public class EnderecoDTO {

    private String logradouro;
    private Long numero;
    private String cidade;
    private String estado;
    private String cep;
    private String endereco;
}
