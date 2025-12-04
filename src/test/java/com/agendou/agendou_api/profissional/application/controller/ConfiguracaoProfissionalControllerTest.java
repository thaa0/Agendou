package com.agendou.agendou_api.profissional.application.controller;

import com.agendou.agendou_api.core.handler.APIException;
import com.agendou.agendou_api.core.handler.GlobalExceptionHandler;
import com.agendou.agendou_api.profissional.application.controller.dto.ConfiguracaoProfissionalRequest;
import com.agendou.agendou_api.profissional.application.service.ConfiguracaoService;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ConfiguracaoProfissionalControllerTest {

    @Mock
    private ConfiguracaoService configuracaoService;

    @InjectMocks
    private ConfiguracaoProfissionalController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(
                        new AuthenticationPrincipalArgumentResolver()
                )
                .build();
    }


    @Test
    void deveConfigurarPoliticasComSucesso() throws Exception {
        UUID userId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).build();

        var auth = new UsernamePasswordAuthenticationToken(usuario, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String json = """
            {
              "intervaloCancelamentoHoras": 24,
              "msgLembreteAtendimento": "Não se atrase!",
              "msgPosAtendimento": "Obrigado por escolher nosso serviço!"
            }
        """;

        mockMvc.perform(post("/v1/configuracao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(configuracaoService)
                .configura(eq(userId), any(ConfiguracaoProfissionalRequest.class));
    }

    @Test
    void deveRetornarErroQuandoServicoLancarAPIException() throws Exception {
        UUID userId = UUID.randomUUID();
        Usuario usuario = Usuario.builder().id(userId).build();

        var auth = new UsernamePasswordAuthenticationToken(usuario, null);
        SecurityContextHolder.getContext().setAuthentication(auth);

        String json = """
            {
              "intervaloCancelamentoHoras": 24,
              "msgLembreteAtendimento": "msg teste",
              "msgPosAtendimento": "msg pos"
            }
        """;

        doThrow(APIException.build(HttpStatus.NOT_FOUND, "Profissional não encontrado"))
                .when(configuracaoService)
                .configura(eq(userId), any(ConfiguracaoProfissionalRequest.class));

        mockMvc.perform(post("/v1/configuracao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }
}
