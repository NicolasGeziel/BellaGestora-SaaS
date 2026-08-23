package com.nicolas.bellagestora.procedimento.controller;

import com.nicolas.bellagestora.procedimento.dto.ProcedimentoRequestDTO;
import com.nicolas.bellagestora.procedimento.dto.ProcedimentoResponseDTO;
import com.nicolas.bellagestora.procedimento.model.Procedimento;
import com.nicolas.bellagestora.procedimento.repository.ProcedimentoRepository;
import com.nicolas.bellagestora.procedimento.services.ProcedimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/procedimentos")
public class ProcedimentoController {
    @Autowired
    private ProcedimentoService service;

    @PostMapping
    public ResponseEntity<ProcedimentoResponseDTO> CriarProcedimento(@RequestBody @Valid ProcedimentoRequestDTO requestDTO) {
        ProcedimentoResponseDTO responseDTO = service.criarProcedimento(requestDTO);
        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProcedimentoResponseDTO>> ExibirProcedimentos(){
        List<ProcedimentoResponseDTO> responseDTOS = service.exibirProcedimentos();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProcedimentoResponseDTO> PegarProcedimento(@PathVariable Long id){
        return ResponseEntity.ok(service.pegarProcedimento(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProcedimentoResponseDTO> AtualizarProcedimento(@PathVariable Long id, @RequestBody @Valid ProcedimentoRequestDTO requestDTO) {
        return ResponseEntity.ok(service.atualizarProcedimento(id,requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProcedimento(@PathVariable Long id) {
        service.deletarProcedimento(id);
        return ResponseEntity.noContent().build();
    }
}
