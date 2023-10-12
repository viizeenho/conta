package com.example.conta.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    @JsonProperty("nome")
    private String nome;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("nacionalidade")
    private String nacionalidade;

    @JsonProperty("endereco")
    private EnderecoDTO endereco;
}