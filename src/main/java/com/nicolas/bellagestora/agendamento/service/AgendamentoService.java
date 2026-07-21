package com.nicolas.bellagestora.agendamento.service;

import com.nicolas.bellagestora.agendamento.dto.AgendamentoRequestDTO;
import com.nicolas.bellagestora.agendamento.dto.AgendamentoResponseDTO;
import com.nicolas.bellagestora.agendamento.model.Agendamento;
import com.nicolas.bellagestora.agendamento.repository.AgendamentoRepository;
import com.nicolas.bellagestora.procedimento.dto.ProcedimentoResponseDTO;
import com.nicolas.bellagestora.procedimento.model.Procedimento;
import com.nicolas.bellagestora.procedimento.repository.ProcedimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ProcedimentoRepository procedimentoRepository;

    public AgendamentoResponseDTO criarAgendamento(AgendamentoRequestDTO requestDTO){
        Procedimento procedimento = procedimentoRepository.findById(requestDTO.procedimento_id())
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));
        Agendamento agendamento = new Agendamento(
                procedimento,
                requestDTO.data(),
                requestDTO.horario(),
                requestDTO.nomeCliente(),
                procedimento.getValor(),
                requestDTO.email()
        );

        Agendamento salvar = agendamentoRepository.save(agendamento);
        return converterParaDtoSemProcedimento(salvar);
    }

    public List<AgendamentoResponseDTO> buscarAgendamentos(){
        return agendamentoRepository.findAll()
                .stream()
                .map(this::converterParaDtoComProcedimento)
                .toList();
    }

    public AgendamentoResponseDTO buscarPorId(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        return converterParaDtoComProcedimento(agendamento);
    }

    public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO requestDTO) {
        Agendamento agendamentoExistente = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        Procedimento procedimento = procedimentoRepository.findById(requestDTO.procedimento_id())
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado"));

        agendamentoExistente.setProcedimento(procedimento);
        agendamentoExistente.setData(requestDTO.data());
        agendamentoExistente.setHorario(requestDTO.horario());
        agendamentoExistente.setNomeCliente(requestDTO.nomeCliente());
        agendamentoExistente.setValorCobrado(procedimento.getValor());
        agendamentoExistente.setEmail(requestDTO.email());

        Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamentoExistente);

        return converterParaDtoComProcedimento(agendamentoAtualizado);
    }

    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado");
        }
        agendamentoRepository.deleteById(id);
    }

    private AgendamentoResponseDTO converterParaDtoSemProcedimento(Agendamento agendamento) {
        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getHorario(),
                agendamento.getNomeCliente(),
                null
        );
    }

    private AgendamentoResponseDTO converterParaDtoComProcedimento(Agendamento agendamento) {
        ProcedimentoResponseDTO procDTO = new ProcedimentoResponseDTO(
                agendamento.getProcedimento().getNome(),
                agendamento.getProcedimento().getDescricao(),
                agendamento.getProcedimento().getValor()
        );

        return new AgendamentoResponseDTO(
                agendamento.getId(),
                agendamento.getData(),
                agendamento.getHorario(),
                agendamento.getNomeCliente(),
                procDTO
        );
    }
}