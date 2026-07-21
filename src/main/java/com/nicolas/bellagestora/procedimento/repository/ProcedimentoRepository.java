package com.nicolas.bellagestora.procedimento.repository;

import com.nicolas.bellagestora.procedimento.model.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}
