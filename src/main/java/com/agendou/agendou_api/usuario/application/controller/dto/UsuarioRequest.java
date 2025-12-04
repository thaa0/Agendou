package com.agendou.agendou_api.usuario.application.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioRequest {

    @NotBlank(message = "O nome completo é obrigatório.")
    @Size(min = 3, max = 120, message = "O nome completo deve ter entre 3 e 120 caracteres.")
    private String nomeCompleto;

    @NotBlank(message = "O nome fantasia é obrigatório.")
    @Size(min = 2, max = 80, message = "O nome fantasia deve ter entre 2 e 80 caracteres.")
    private String nomeFantasia;

    @NotBlank(message = "O WhatsApp é obrigatório.")
    @Pattern(
            regexp = "^55\\d{2}\\d{8,9}$",
            message = "O WhatsApp deve estar no formato 55 + DDD + número (somente dígitos)."
    )
    private String whatsapp;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(max = 8, message = "A senha deve ter no máximo 8 caracteres.")
    @Pattern(
            regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$",
            message = "A senha deve conter letras e números."
    )
    private String senha;

}
