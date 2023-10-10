package com.example.conta.controller;

import com.example.conta.Enum.Moeda;
import com.example.conta.model.Conta;
import com.example.conta.model.DTO.ContaDTO;
import com.example.conta.repository.ContaRepository;
import com.example.conta.service.ContaService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/contas")
public class ContaController {
    private final ContaService contaService;

    private final ContaRepository repository;

    @Autowired
    public ContaController(ContaService contaService, ContaRepository repository) {
        this.contaService = contaService;
        this.repository = repository;
    }

    @PostMapping("/")
    public ResponseEntity<Conta> createConta(@RequestBody ContaDTO contaDTO) {
            Conta novaConta = contaService.createConta(contaDTO);
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<Conta> getAllContas() {
        return contaService.findAllContas();
    }

    @GetMapping("/{id}")
    public Conta getContaById(@PathVariable Long id) {
        return contaService.findContaById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteConta(@PathVariable Long id) {
        contaService.deleteConta(id);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(
            @RequestParam @NotBlank String numeroContaOrigem,
            @RequestParam @NotBlank String numeroContaDestino,
            @RequestParam double valor,
            @RequestParam Moeda moeda) {
        Conta contaOrigem = repository.findByNumeroConta(numeroContaOrigem);
        Conta contaDestino = repository.findByNumeroConta(numeroContaDestino);

        if (contaOrigem == null || contaDestino == null) {
            return ResponseEntity.badRequest().body("Conta de origem ou destino não encontrada.");
        }

        contaService.realizarTransferencia(contaOrigem, contaDestino, valor, moeda);

        return ResponseEntity.ok("Transferência realizada com sucesso.");
    }

}
