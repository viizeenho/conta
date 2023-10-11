package com.example.conta.repository;

import com.example.conta.model.Extrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtratoRepository extends JpaRepository<Extrato,Long> {
}
