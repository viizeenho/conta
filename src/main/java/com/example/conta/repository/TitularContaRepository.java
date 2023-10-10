package com.example.conta.repository;

import com.example.conta.model.TitularConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TitularContaRepository extends JpaRepository<TitularConta, Long> {
}
