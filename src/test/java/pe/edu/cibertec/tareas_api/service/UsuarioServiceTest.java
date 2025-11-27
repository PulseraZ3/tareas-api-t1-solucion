package pe.edu.cibertec.tareas_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import pe.edu.cibertec.tareas_api.model.Usuario;
import pe.edu.cibertec.tareas_api.repository.UsuarioRepository;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L, "Leonardo", "Leonardo.jimenezlayme@gmail.com", "usuario", true);
    }

    @Test
    void listartodos() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));
        List<Usuario> resultado = usuarioService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1,resultado.size() );
        verify(usuarioRepository, times(1)).findAll();    
    }

    @Test
    void buscarPorId_Exitoso(){
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        var resultado = usuarioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Leonardo", resultado.get().getNombre());
        verify(usuarioRepository, times(1)).findById(1L);
    }
    @Test
    void crear_Exitoso(){
        Usuario usuario = new Usuario();
        usuario.setId(1l);
        usuario.setNombre("Carlos");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        Usuario resultado = usuarioService.crear(usuario);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        verify(usuarioRepository, times(1)).save(usuario);

    }
}
