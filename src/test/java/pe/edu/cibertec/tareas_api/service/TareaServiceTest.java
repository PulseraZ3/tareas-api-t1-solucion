package pe.edu.cibertec.tareas_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pe.edu.cibertec.tareas_api.model.Proyecto;
import pe.edu.cibertec.tareas_api.model.Tarea;
import pe.edu.cibertec.tareas_api.repository.ProyectoRepository;
import pe.edu.cibertec.tareas_api.repository.TareaRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
public class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private ProyectoRepository proyectoRepository;

    @InjectMocks
    private TareaService tareaService;

    private Proyecto proyecto;
    private Tarea tarea;

    @BeforeEach
    void setUp() {
        proyecto = new Proyecto();
        proyecto.setId(1L);
        proyecto.setNombre("Proyecto Alpha");
        proyecto.setActivo(true);
        proyecto.setFechaInicio(LocalDate.now());
        proyecto.setFechaFin(LocalDate.now().plusDays(10));

        tarea = new Tarea();
        tarea.setId(1L);
        tarea.setTitulo("Implementar Login");
        tarea.setEstado("PENDIENTE");
        tarea.setPrioridad("ALTA");
        tarea.setProyecto(proyecto);
        tarea.setActivo(true);
    }

    @Test
    void listarTodos(){
        when(tareaRepository.findAll()).thenReturn(Arrays.asList(tarea));

        List<Tarea> result = tareaService.listarTodos();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(tareaRepository, times(1)).findAll();
    }

    @Test
    void crear_Exitoso(){
        when(proyectoRepository.findById(1L)).thenReturn(Optional.of(proyecto));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea result = tareaService.crear(tarea);

        assertNotNull(result);
        assertEquals("Implementar Login", result.getTitulo());
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void crear_EstadoInvalido(){

        tarea.setEstado("ESTADO_INVALIDO");
        assertThrows(RuntimeException.class, () -> tareaService.crear(tarea));
        verify(proyectoRepository, never()).findById(anyLong());
        verify(tareaRepository, never()).save(any(Tarea.class));

    }
}
