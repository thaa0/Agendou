package com.agendou.agendou_api.usuario.application.service;

import com.agendou.agendou_api.profissional.application.repository.ProfissionalRepository;
import com.agendou.agendou_api.usuario.application.controller.dto.UsuarioRequest;
import com.agendou.agendou_api.usuario.application.repository.UsuarioRepository;
import com.agendou.agendou_api.usuario.domain.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioApplicationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private ProfissionalRepository profissionalRepository;
    @Mock
    private BCryptPasswordEncoder encriptador;

    @InjectMocks
    private UsuarioApplicationService usuarioService;

    @Test
    void testCadastrarUsuario_Success() {
        UsuarioRequest request = new UsuarioRequest(
                "Usuário Teste",
                "nome",
                "123456",
                "teste@email.com",
                "11999999999"
        );

        when(encriptador.encode(request.getSenha())).thenReturn("senha-criptografada");
        usuarioService.cadastrarUsuario(request);
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository, times(1)).salva(captor.capture());

        Usuario usuarioSalvo = captor.getValue();
        assertEquals(request.getEmail(), usuarioSalvo.getEmail());
        assertEquals("senha-criptografada", usuarioSalvo.getPassword());
        assertEquals(request.getNomeCompleto(), usuarioSalvo.getNomeCompleto());
        assertEquals(request.getWhatsapp(), usuarioSalvo.getWhatsapp());

        verify(encriptador, times(1)).encode(request.getSenha());
    }

    @Test
    void testCadastrarUsuario_NullRequest() {
        assertThrows(NullPointerException.class, () -> usuarioService.cadastrarUsuario(null));
    }
}
