package com.nicolas.bellagestora.procedimento.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProcedimentoRequestDTO(
                @NotBlank
                String nome,
                @NotBlank
                String descricao,
                @NotBlank
                String observacaoDono,
                @NotNull
                BigDecimal valor
) {
}
