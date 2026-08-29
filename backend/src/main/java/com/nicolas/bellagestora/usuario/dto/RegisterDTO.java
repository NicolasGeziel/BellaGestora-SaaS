package com.nicolas.bellagestora.usuario.dto;

import com.nicolas.bellagestora.usuario.role.UserRole;

public record RegisterDTO (String login, String password, UserRole role) {
}
