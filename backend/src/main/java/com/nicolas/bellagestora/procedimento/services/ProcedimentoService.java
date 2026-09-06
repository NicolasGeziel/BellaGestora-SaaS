package com.nicolas.bellagestora.procedimento.services;

import com.nicolas.bellagestora.procedimento.dto.ProcedimentoRequestDTO;
import com.nicolas.bellagestora.procedimento.dto.ProcedimentoResponseDTO;
import com.nicolas.bellagestora.procedimento.model.Procedimento;
import com.nicolas.bellagestora.procedimento.repository.ProcedimentoRepository;
import com.nicolas.bellagestora.usuario.model.User;
import com.nicolas.bellagestora.usuario.repositories.UserRepository;
import com.nicolas.bellagestora.usuario.role.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcedimentoService {
    @Autowired
    private ProcedimentoRepository procedimentoRepository;
    @Autowired
    private UserRepository repositoryUser;

    public ProcedimentoResponseDTO criarProcedimento(ProcedimentoRequestDTO requestDTO) {
        User usuarioLogado = getUsuarioLogado();

        Procedimento procedimento = new Procedimento(
                requestDTO.nome(),
                requestDTO.descricao(),
                requestDTO.observacaoDono(),
                requestDTO.valor()
        );
        procedimento.setUser(usuarioLogado);

        Procedimento salvo = procedimentoRepository.save(procedimento);
        return new ProcedimentoResponseDTO(salvo.getId(), salvo.getNome(), salvo.getDescricao(), salvo.getValor());
    }

    public List<ProcedimentoResponseDTO> exibirProcedimentos() {
        User usuarioLogado = getUsuarioLogado();
        List<Procedimento> procedimentos;

        if (usuarioLogado.getRole() == UserRole.ADMIN) {
            procedimentos = procedimentoRepository.findAllByuser(usuarioLogado);
        } else {
            procedimentos = procedimentoRepository.findAll();
        }

        return procedimentos.stream()
                .map(p -> new ProcedimentoResponseDTO(p.getId(),p.getNome(), p.getDescricao(), p.getValor()))
                .toList();
    }

    public ProcedimentoResponseDTO pegarProcedimento(Long id) {
        User usuarioLogado = getUsuarioLogado();
        Procedimento procedimento = buscarEValidarDono(id, usuarioLogado);

        return new ProcedimentoResponseDTO(procedimento.getId(), procedimento.getNome(), procedimento.getDescricao(), procedimento.getValor());
    }

    public ProcedimentoResponseDTO atualizarProcedimento(Long id, ProcedimentoRequestDTO requestDTO) {
        User usuarioLogado = getUsuarioLogado();
        Procedimento procedimento = buscarEValidarDono(id, usuarioLogado);

        procedimento.setNome(requestDTO.nome());
        procedimento.setDescricao(requestDTO.descricao());
        procedimento.setObservacaoDono(requestDTO.observacaoDono());
        procedimento.setValor(requestDTO.valor());

        Procedimento atualizado = procedimentoRepository.save(procedimento);
        return new ProcedimentoResponseDTO(atualizado.getId(), atualizado.getNome(), atualizado.getDescricao(), atualizado.getValor());
    }

    public void deletarProcedimento(Long id) {
        User usuarioLogado = getUsuarioLogado();
        Procedimento procedimento = buscarEValidarDono(id, usuarioLogado);

        procedimentoRepository.delete(procedimento);
    }

    private User getUsuarioLogado() {
        String loginUsuario = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = repositoryUser.findByLogin(loginUsuario);
        if (userDetails == null) {
            throw new RuntimeException("Usuário não encontrado no sistema.");
        }
        return (User) userDetails;
    }

    private Procedimento buscarEValidarDono(Long procedimentoId, User usuarioLogado) {
        Procedimento procedimento = procedimentoRepository.findById(procedimentoId)
                .orElseThrow(() -> new RuntimeException("Procedimento não encontrado."));

        if (!procedimento.getUser().getId().equals(usuarioLogado.getId())) {
            throw new RuntimeException("Acesso Negado: Este procedimento pertence a outra conta.");
        }

        return procedimento;
    }
}
