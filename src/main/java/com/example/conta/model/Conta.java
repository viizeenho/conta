package com.example.conta.model;

import java.util.*;

import com.example.conta.model.enums.MoedaEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Conta {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String tipoConta;
	private String numeroConta;
	private Date dataAberturaConta;
	private double saldoDolar;
	private double saldoReal;
	private String status;

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private Set<TitularConta> titulares = new HashSet<>();

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private List<Extrato> extratos = new ArrayList<>();

	@OneToMany(mappedBy = "conta", cascade = CascadeType.ALL)
	private Set<Emprestimo> emprestimos = new HashSet<>();

	public void addEmprestimo(Emprestimo emprestimo) {
		emprestimos.add(emprestimo);
		emprestimo.setConta(this);
	}

}

