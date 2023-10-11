package com.example.conta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateTransaction;
    private String descriptionTransaction;
    private BigDecimal valueTransaction;
    @ManyToOne
    @JoinColumn(name = "extrato_id")
    private Extrato extrato;

}
