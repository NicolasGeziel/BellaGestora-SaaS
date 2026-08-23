package com.nicolas.bellagestora.agendamento.dto;

import com.nicolas.bellagestora.agendamento.model.StatusAgendamento;
import jakarta.validation.constraints.NotNull;

public record AtualizarStatusDTO(
        @NotNull(message = "O status não pode ser nulo")
        StatusAgendamento status
){ }
