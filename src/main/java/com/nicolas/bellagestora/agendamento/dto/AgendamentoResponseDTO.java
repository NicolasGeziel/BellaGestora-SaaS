package com.nicolas.bellagestora.agendamento.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record AgendamentoResponseDTO(
        @NotNull
        Long procedimento_id,

        @NotNull
        @Future
        LocalDate data,

        @NotNull
        LocalTime horario,

        @NotBlank
        String nomeCliente
){
}
