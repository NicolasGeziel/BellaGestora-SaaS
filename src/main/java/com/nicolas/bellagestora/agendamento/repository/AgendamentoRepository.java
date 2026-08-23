package com.nicolas.bellagestora.agendamento.repository;

import com.nicolas.bellagestora.agendamento.model.Agendamento;
import com.nicolas.bellagestora.usuario.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findAllByuser(User usuario);
    List<Agendamento> findAllByProcedimento_user(User donoDoProcedimento);
}
