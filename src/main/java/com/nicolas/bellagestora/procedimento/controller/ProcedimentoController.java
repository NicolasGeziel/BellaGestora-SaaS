package com.nicolas.bellagestora.procedimento.controller;

import com.nicolas.bellagestora.procedimento.dto.ProcedimentoRequestDTO;
import com.nicolas.bellagestora.procedimento.dto.ProcedimentoResponseDTO;
import com.nicolas.bellagestora.procedimento.model.Procedimento;
import com.nicolas.bellagestora.procedimento.repository.ProcedimentoRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/procedimento")
public class ProcedimentoController {

    private final ProcedimentoRepository repository;
    public ProcedimentoController(ProcedimentoRepository repository){
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<ProcedimentoResponseDTO> CriarProcedimento(@RequestBody @Valid ProcedimentoRequestDTO requestDTO) {
        Procedimento procedimento = new Procedimento(
                requestDTO.nome(),requestDTO.descricao(),
                requestDTO.observacaoDono(), requestDTO.valor()
        );
        Procedimento salvar = repository.save(procedimento);
        ProcedimentoResponseDTO responseDTO = new ProcedimentoResponseDTO(
          salvar.getNome(),salvar.getDescricao(),salvar.getValor()
        );
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProcedimentoResponseDTO>> ExibirProcedimento(){
        List<Procedimento>  procedimentos = repository.findAll();
        List<ProcedimentoResponseDTO> responseDTOS = procedimentos.stream().
                map( p-> new ProcedimentoResponseDTO(
                  p.getNome(),p.getDescricao(),p.getValor()
                )
        ).toList();
        return ResponseEntity.ok(responseDTOS);
    }
}
