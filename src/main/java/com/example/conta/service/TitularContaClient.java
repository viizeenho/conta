package com.example.conta.service;

import com.example.conta.model.DTO.ClientDTO;
import com.example.conta.model.DTO.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "conta", url = "http://localhost:8081/client")
public interface TitularContaClient {


    @GetMapping("/{numeroDeDocumento}") // Endpoint do serviço de TitularConta
    ResponseDTO<ClientDTO> findByDocument(@PathVariable("numeroDeDocumento") String numeroDeDocumento);
}
