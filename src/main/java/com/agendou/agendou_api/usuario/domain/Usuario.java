package com.agendou.agendou_api.usuario.domain;


import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, unique = true, nullable = false)
    private UUID id;
    @NotBlank(message = "Campo não pode ser em branco")
    private String nomeCompleto;
    @NotBlank
    private String nomeFantasia;
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @Getter(AccessLevel.NONE)
    @Size(max = 60)
    private String senha;
    private TipoPlano tipo;
    @NotBlank(message = "Campo não pode ser em branco")
    private String whatsapp;
    private LocalDateTime momentoCriacao;

    public Usuario(UsuarioRequest usuarioRequest, BCryptPasswordEncoder passwordEncoder) {
        this.whatsapp = usuarioRequest.getWhatsapp();
        this.senha = passwordEncoder.encode(usuarioRequest.getSenha());
        this.email = usuarioRequest.getEmail();
        this.nomeCompleto = usuarioRequest.getNomeCompleto();
        this.nomeFantasia = usuarioRequest.getNomeFantasia();
        this.tipo = TipoPlano.FREE;
        this.momentoCriacao = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
    @Override
    public String getPassword() {
        return this.senha;
    }
    @Override
    public String getUsername() {
        return this.email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}