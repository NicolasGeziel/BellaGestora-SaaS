package com.nicolas.bellagestora.procedimento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProcedimentoResponseDTO(
        @NotBlank(message = "Nome obrigatorio")
        String nome,
        @NotBlank
        String descricao,
        @NotNull
        BigDecimal valor
) {
}
