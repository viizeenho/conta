package com.example.conta.service;

import com.example.conta.model.TitularConta;
import org.springframework.stereotype.Component;

public interface TitularContaService {

    TitularConta findTitularContabyId(Long id);

}
