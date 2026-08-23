package com.nicolas.bellagestora.procedimento.repository;

import com.nicolas.bellagestora.procedimento.model.Procedimento;
import com.nicolas.bellagestora.usuario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
    List<Procedimento> findAllByuser(User usuario);
}
