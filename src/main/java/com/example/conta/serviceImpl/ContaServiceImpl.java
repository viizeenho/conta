package com.example.conta.serviceImpl;


import com.example.conta.model.Conta;
import com.example.conta.model.Emprestimo;
import com.example.conta.model.DTO.CambioDTO;
import com.example.conta.model.DTO.ContaDTO;
import com.example.conta.model.DTO.EmprestimoDTO;
import com.example.conta.model.DTO.TitularContaDTO;
import com.example.conta.model.enums.MoedaEnum;
import com.example.conta.model.TitularConta;
import com.example.conta.repository.ContaRepository;
import com.example.conta.repository.EmprestimoRepository;
import com.example.conta.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class ContaServiceImpl implements ContaService {

    @Value("${dollar.brlValue}")
    private String priceValue;

    @Value("${contaBanco.numeroConta}")
    private String contaBanco;

    private final ContaRepository contaRepository;
    private final TitularContaServiceImpl titularContaServiceImpl;
    private final EmprestimoRepository emprestimoRepository;

    @Autowired
    public ContaServiceImpl(ContaRepository contaRepository, TitularContaServiceImpl titularContaServiceImpl, EmprestimoRepository emprestimoRepository) {
        this.contaRepository = contaRepository;
        this.titularContaServiceImpl = titularContaServiceImpl;
        this.emprestimoRepository = emprestimoRepository;
    }

    @Override
    public List<Conta> findAllContas() {
        return contaRepository.findAll();
    }

    @Override
    public Conta findContaById(Long id) {
        return contaRepository.findById(id).orElse(null);
    }

    @Override
    public Conta findByNumeroConta(String numeroConta) {
        return contaRepository.findByNumeroConta(numeroConta).get();
    }
    
    
    

    @Override
    public Conta createConta(ContaDTO contaDTO) {

       Conta conta = new Conta();

       conta.setTipoConta(contaDTO.getTipoConta());
       conta.setNumeroConta(contaDTO.getNumeroConta());
       conta.setDataAberturaConta(contaDTO.getDataAberturaConta());
       conta.setSaldoDolar(contaDTO.getSaldoDolar());
       conta.setSaldoReal(contaDTO.getSaldoReal());
       conta.setStatus(contaDTO.getStatus());

       List<TitularConta> titularContas = new ArrayList<>();

       if (contaDTO.getTitulares() != null){
           for (TitularContaDTO titularContaDTO : contaDTO.getTitulares()){
               TitularConta titularConta = new TitularConta();
               titularConta.setNome(titularContaDTO.getNome());
               titularConta.setCpfCnpj(titularContaDTO.getCpfCnpj());
               titularConta.setConta(conta);
               titularContas.add(titularConta);
           }
       }
        conta.setTitulares(new HashSet<>(titularContas));
        return contaRepository.save(conta);
    }

    @Override
    public void realizarTransferencia(Conta contaOrigem, Conta contaDestino, double valor, MoedaEnum moeda) {
        if (moeda == MoedaEnum.REAL) {
            if (contaOrigem.getSaldoReal() >= valor) {
                contaOrigem.setSaldoReal(contaOrigem.getSaldoReal() - valor);
                contaDestino.setSaldoReal(contaDestino.getSaldoReal() + valor);
            } else {
                throw new RuntimeException("Saldo insuficiente na conta de origem.");
            }
        } else if (moeda == MoedaEnum.DOLAR) {
            if (contaOrigem.getSaldoDolar() >= valor) {
                contaOrigem.setSaldoDolar(contaOrigem.getSaldoDolar() - valor);
                contaDestino.setSaldoDolar(contaDestino.getSaldoDolar() + valor);
            } else {
                throw new RuntimeException("Saldo insuficiente em dólares na conta de origem.");
            }
        } else {
            throw new RuntimeException("Moeda não suportada.");
        }

        contaRepository.save(contaOrigem);
        contaRepository.save(contaDestino);
    }
    
    @Override
    public Emprestimo solicitarEmprestimo(Long idConta, EmprestimoDTO emprestimoDTO) throws Exception {
        Conta conta = contaRepository.findById(idConta).orElse(null);
        Conta contaRoot = contaRepository.findByNumeroConta(contaBanco).orElse(null);
        if (idConta == contaRoot.getId()) {
        	 throw new Exception("Não e possivel realizar emprestimo com essa conta!");
		}

        if (conta != null && emprestimoDTO.getValor() > 0) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setValor(emprestimoDTO.getValor());
            emprestimo.setMoeda(emprestimoDTO.getMoeda());
            emprestimo.setDataContratacao(emprestimoDTO.getDataContratacao());

            double valorEmReal = 0;

            if (MoedaEnum.DOLAR.equals(emprestimoDTO.getMoeda())) {
                // Considerando a taxa de câmbio fixa de 1 dólar para 5 reais
                valorEmReal = emprestimoDTO.getValor() * 5;
       
                contaRoot.setSaldoDolar(contaRoot.getSaldoDolar() - emprestimoDTO.getValor());
                conta.setSaldoDolar(conta.getSaldoDolar() + emprestimoDTO.getValor());
            } else if (MoedaEnum.REAL.equals(emprestimoDTO.getMoeda())) {
                valorEmReal = emprestimoDTO.getValor();
                contaRoot.setSaldoReal(contaRoot.getSaldoReal() - emprestimoDTO.getValor());
                conta.setSaldoReal(conta.getSaldoReal() + emprestimoDTO.getValor());
            } else {
                throw new Exception("Moeda não Suportada!");
            }

            emprestimo.setValorEmReal(valorEmReal);
            conta.addEmprestimo(emprestimo);

            emprestimoRepository.save(emprestimo);
            return emprestimo;
        }

        return null;
    }


    @Override
    public void deleteConta(Long id) {
        contaRepository.deleteById(id);
    }

    @Override
    public CambioDTO solicitarCambio(CambioDTO cambioDTO) throws RuntimeException{
        BigDecimal dollarAmmount = BigDecimal.valueOf(0.0);;
        BigDecimal realAmmount = BigDecimal.valueOf(0.0);
        BigDecimal returnValue = BigDecimal.ZERO;

        BigDecimal price =  getPrice();

        CambioDTO cambio = new CambioDTO();
        cambio.setNumeroConta(cambioDTO.getNumeroConta());

        Conta contaCliente = findByNumeroConta(cambio.getNumeroConta());
        Conta contaBanco = findByNumeroConta(getContaBanco());

        if (cambioDTO.getMoeda().equals(MoedaEnum.REAL)){
            realAmmount = cambioDTO.getValor();
            dollarAmmount = realAmmount.divide(price,2, RoundingMode.HALF_UP);
            cambio.setMoeda(MoedaEnum.DOLAR);

            //Realiza transferencia de real do cliente para o banco
            realizarTransferencia(contaCliente,contaBanco,realAmmount.doubleValue(),cambioDTO.getMoeda());

            //Realiza transferencia de dolar do banco para o cliente
            realizarTransferencia(contaBanco, contaCliente,dollarAmmount.doubleValue(),cambio.getMoeda());

            returnValue = dollarAmmount;
        } else {
            dollarAmmount = cambioDTO.getValor();
            realAmmount = dollarAmmount.multiply(price).setScale(2,RoundingMode.HALF_UP);
            cambio.setMoeda(MoedaEnum.REAL);

            //Realiza transferencia de dolar do cliente para o banco
            realizarTransferencia(contaCliente,contaBanco,dollarAmmount.doubleValue(),cambioDTO.getMoeda());

            //Realiza transferencia de real do banco para o cliente
            realizarTransferencia(contaBanco, contaCliente,realAmmount.doubleValue(),cambio.getMoeda());

            returnValue = realAmmount;
        }

        cambio.setValor(returnValue);

        return cambio;
    }

    private String getContaBanco() {
        if (contaBanco!=null){
            return contaBanco;
        }
        return "1";
    }

    private BigDecimal getPrice() {
        BigDecimal price = new BigDecimal(5);
        if(priceValue!= null) {
            try {
                price = new BigDecimal(priceValue);
            } catch (NumberFormatException e) {
                throw new RuntimeException(e);
            }
        }
        return price;
    }
}
