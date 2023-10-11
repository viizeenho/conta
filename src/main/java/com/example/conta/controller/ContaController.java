package com.example.conta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.conta.model.Conta;
import com.example.conta.model.Emprestimo;
import com.example.conta.model.DTO.ContaDTO;
import com.example.conta.model.DTO.EmprestimoDTO;
import com.example.conta.model.enums.MoedaEnum;
import com.example.conta.repository.ContaRepository;
import com.example.conta.service.ContaService;

import jakarta.validation.constraints.NotBlank;


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

    @PostMapping
    public ResponseEntity<Conta> createConta(@RequestBody ContaDTO contaDTO) {
            Conta novaConta = contaService.createConta(contaDTO);
            return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    @GetMapping
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
    
    @PostMapping("/{id}/emprestimos")
    public ResponseEntity<Emprestimo> solicitarEmprestimo(@PathVariable Long id, @RequestBody EmprestimoDTO emprestimoDTO) {
        try {
            Emprestimo emprestimo = contaService.solicitarEmprestimo(id, emprestimoDTO);
            return new ResponseEntity<>(emprestimo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(
            @RequestParam @NotBlank String numeroContaOrigem,
            @RequestParam @NotBlank String numeroContaDestino,
            @RequestParam double valor,
            @RequestParam MoedaEnum moeda) {
        Conta contaOrigem = repository.findByNumeroConta(numeroContaOrigem).get();
        Conta contaDestino = repository.findByNumeroConta(numeroContaDestino).get();

        if (contaOrigem == null || contaDestino == null) {
            return ResponseEntity.badRequest().body("Conta de origem ou destino não encontrada.");
        }

        contaService.realizarTransferencia(contaOrigem, contaDestino, valor, moeda);

        return ResponseEntity.ok("Transferência realizada com sucesso.");
    }

}
