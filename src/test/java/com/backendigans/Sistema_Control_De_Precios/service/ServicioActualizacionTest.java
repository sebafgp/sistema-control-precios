package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.*;
import com.backendigans.Sistema_Control_De_Precios.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServicioActualizacionTest {
    @Mock
    private RepositorioActualizacion actualizacionRepository;

    @InjectMocks
    private ImplServicioActualizacion actualizacionService;

    /* HU_03 */
    @Test
    @DisplayName("Save actualizacion - datos validos")
    void siInvocoSaveActualizacionConActualizacionValidaSeGuardaCorrectamente(){
        // Arrange
        Actualizacion actualizacion = crearActualizacion();

        // Act
        actualizacionService.saveActualizacion(actualizacion);

        // Assert
        verify(actualizacionRepository, times(1)).save(actualizacion);
    }

    @Test
    @DisplayName("Save actualizacion - datos no validos")
    void siInvocoSaveActualizacionConActualizacionNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Actualizacion actualizacion = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> actualizacionService.saveActualizacion(actualizacion));
    }

    /* HU_04 */
    @Test
    @DisplayName("Get Ultima Actualizacion - Existe")
    void siInvocoEncontrarUltimaPorProductoDevuelveUnaActualizacionValida(){
        // Arrange
        Optional<Actualizacion> resultado;
        Producto producto = crearProducto();
        Actualizacion actualizacion = crearActualizacion();
        Set<Actualizacion> set = new HashSet<>();
        set.add(actualizacion);
        producto.setActualizaciones(set);
        when(actualizacionRepository.findFirstByProductoOrderByFechaActualizacionDesc(producto)).thenReturn(Optional.of(actualizacion));

        // Act
        resultado = actualizacionService.encontrarUltimaPorProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals(actualizacion, resultado.get());
    }

    /* HU_04 */
    @Test
    @DisplayName("Get Ultima Actualizacion - No Existe")
    void siInvocoEncontrarUltimaPorProductoNoDevuelveUnaActualizacion(){
        // Arrange
        Optional<Actualizacion> resultado;
        Producto producto = crearProducto();
        Actualizacion actualizacion = crearActualizacion();
        Set<Actualizacion> set = new HashSet<>();
        set.add(actualizacion);
        producto.setActualizaciones(set);
        when(actualizacionRepository.findFirstByProductoOrderByFechaActualizacionDesc(producto)).thenReturn(Optional.empty());

        // Act
        resultado = actualizacionService.encontrarUltimaPorProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isPresent());
    }

    /* HU_05 */
    @Test
    @DisplayName("Encontrar por colaborador - Existe y no Vacio")
    void siInvocoEncontrarPorColaboradorYElColaboradorExisteYTieneActualizacionesRetornaUnaLista(){
        // When
        Colaborador colaborador = crearColaborador();
        Actualizacion actualizacion = crearActualizacion();
        Set<Actualizacion> set = new HashSet<>();
        set.add(actualizacion);
        colaborador.setActualizacion(set);
        List<Actualizacion> listaActualizaciones = new ArrayList<>(set);
        List<Actualizacion> resultado;

        when(actualizacionService.encontrarPorColaborador(colaborador)).thenReturn(listaActualizaciones);

        // Act
        resultado = actualizacionRepository.findByColaborador(colaborador);

        // Assert
        assertNotNull(resultado);
        assertEquals(listaActualizaciones, resultado);
    }

    @Test
    @DisplayName("Encontrar por colaborador - Existe y Vacio")
    void siInvocoEncontrarPorColaboradorYElColaboradorExisteYNoTieneActualizacionesRetornaUnaListaVacia(){
        // When
        Colaborador colaborador = crearColaborador();
        List<Actualizacion> listaActualizaciones = new ArrayList<>();
        List<Actualizacion> resultado;

        when(actualizacionService.encontrarPorColaborador(colaborador)).thenReturn(listaActualizaciones);

        // Act
        resultado = actualizacionRepository.findByColaborador(colaborador);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    @DisplayName("Encontrar por colaborador - No Existe Colaborador")
    void siInvocoEncontrarPorColaboradorYElColaboradorNoExisteYLanzaNoSuchElementException(){
        // When
        Colaborador colaborador = null;
        doThrow(NoSuchElementException.class).when(actualizacionRepository).findByColaborador(colaborador);

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> actualizacionService.encontrarPorColaborador(colaborador));

    }

    /* Funciones Utilidad */

    private Colaborador crearColaborador(){
        Colaborador c = new Colaborador("ex@mail.com", "password", "nick");
        return c;
    }
    private  Producto crearProducto(){
        Producto p = new Producto(1, "Tallarines", "Carozzi", 100, "g", 1000, LocalDateTime.now());
        return p;
    }

    private Actualizacion crearActualizacion(){
        Colaborador c = crearColaborador();
        Producto p = crearProducto();
        Actualizacion act = new Actualizacion(c, p, 1000);
        return act;
    }

}
