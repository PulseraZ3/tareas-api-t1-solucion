package pe.edu.cibertec.tareas_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.tareas_api.model.Proyecto;
import pe.edu.cibertec.tareas_api.model.Usuario;
import pe.edu.cibertec.tareas_api.repository.ProyectoRepository;
import pe.edu.cibertec.tareas_api.repository.UsuarioRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProyectoServiceTest {
    @Mock
    private ProyectoRepository proyectoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private ProyectoService proyectoService;

    private Usuario usuario;
    private Proyecto proyecto;

    @BeforeEach
    void setUp() {
        usuario = new Usuario(1L,"Leonardo","test@gmail.com","usuario",true);
        proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Integrador cibertec");
        proyecto.setFechaInicio(LocalDate.now());
        proyecto.setFechaFin(LocalDate.now().plusMonths(2));
        proyecto.setUsuario(usuario);
    }

    @Test
    void listarTodos() {
        when(proyectoRepository.findAll()).thenReturn(List.of(proyecto));

        List<Proyecto> resultado = proyectoService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(proyectoRepository, times(1)).findAll();
    }


    @Test
    void buscarPorId_Exitoso() {
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));

        var resultado = proyectoService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Proyecto Integrador cibertec", resultado.get().getNombre());
        verify(proyectoRepository, times(1)).findById(1L);
    }

    @Test
    void crear_Exitoso() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(proyectoRepository.save(any(Proyecto.class))).thenReturn(proyecto);

        Proyecto resultado = proyectoService.crear(proyecto);

        assertNotNull(resultado);
        assertEquals("Proyecto Integrador cibertec", resultado.getNombre());
        verify(usuarioRepository).findById(1L);
        verify(proyectoRepository).save(any(Proyecto.class));
    }

    @Test
    void crear_FechaInvalida() {
        Proyecto proyecto = new Proyecto();
        proyecto.setNombre("Proyecto");
        proyecto.setFechaInicio(LocalDate.of(2025, 10, 10));
        proyecto.setFechaFin(LocalDate.of(2025, 10, 1));


        assertThrows(RuntimeException.class, () -> proyectoService.crear(proyecto));
    }
}
