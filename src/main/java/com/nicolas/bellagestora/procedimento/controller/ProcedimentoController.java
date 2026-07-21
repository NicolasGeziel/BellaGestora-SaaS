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
import java.util.Optional;

@RestController
@RequestMapping("/procedimentos")
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
    public ResponseEntity<List<ProcedimentoResponseDTO>> ExibirProcedimentos(){
        List<Procedimento>  procedimentos = repository.findAll();
        List<ProcedimentoResponseDTO> responseDTOS = procedimentos.stream().
                map( p-> new ProcedimentoResponseDTO(
                  p.getNome(),p.getDescricao(),p.getValor()
                )
        ).toList();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedimentoResponseDTO> PegarProcedimento(@PathVariable Long id){
        return repository.findById(id).map(p->
                new ProcedimentoResponseDTO(
                p.getNome(),
                p.getDescricao(),
                p.getValor()
                )
        ).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedimentoResponseDTO> AtualizarProcedimento(@PathVariable Long id, @RequestBody @Valid ProcedimentoRequestDTO requestDTO) {

        return repository.findById(id)
                .map(p -> {
                    p.setNome(requestDTO.nome());
                    p.setDescricao(requestDTO.descricao());
                    p.setObservacaoDono(requestDTO.observacaoDono());
                    p.setValor(requestDTO.valor());

                    Procedimento atualizado = repository.save(p);

                    ProcedimentoResponseDTO responseDTO = new ProcedimentoResponseDTO(
                            atualizado.getNome(),
                            atualizado.getDescricao(),
                            atualizado.getValor()
                    );
                    return ResponseEntity.ok(responseDTO);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProcedimento(@PathVariable Long id) {
        return repository.findById(id)
                .map(p -> {
                    repository.delete(p);
                    return ResponseEntity.noContent().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
