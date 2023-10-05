package com.example.conta.serviceImpl;

import com.example.conta.model.TitularConta;
import com.example.conta.repository.TitularContaRepository;
import com.example.conta.service.TitularContaService;
import org.springframework.stereotype.Service;

@Service
public class TitularContaServiceImpl implements TitularContaService {

    private final TitularContaRepository titularContaRepository;

    public TitularContaServiceImpl(TitularContaRepository titularContaRepository) {
        this.titularContaRepository = titularContaRepository;
    }


    @Override
    public TitularConta findTitularContabyId(Long id) {
        return titularContaRepository.findById(id).orElse(null);
    }
}
