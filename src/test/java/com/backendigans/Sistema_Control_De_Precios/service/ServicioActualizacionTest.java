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

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
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
        Actualizacion resultado;
        Inventario inventario = crearInventario();
        Actualizacion actualizacion = crearActualizacion();
        inventario.addActualizacion(actualizacion);
        when(actualizacionRepository.findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventario.getInventarioID())).thenReturn(Optional.of(actualizacion));

        // Act
        resultado = actualizacionService.encontrarUltimaPorInventario(inventario);

        // Assert
        assertNotNull(resultado);
        assertEquals(actualizacion, resultado);
    }

    /* HU_04 */
    @Test
    @DisplayName("Get Ultima Actualizacion - No Existe")
    void siInvocoEncontrarUltimaPorProductoNoDevuelveUnaActualizacion(){
        // Arrange
        Inventario inventario = crearInventario();
        Actualizacion actualizacion = crearActualizacion();
        inventario.addActualizacion(actualizacion);
        when(actualizacionRepository.findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventario.getInventarioID())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> actualizacionService.encontrarUltimaPorInventario(inventario));
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

    /* HU_13 */
    @Test
    @DisplayName("Top sucursales - Lista no vacia")
    void siInvocoGetTopSucursalesPorInventariosConDatosValidosRetornaLista(){

        // When
        List<Sucursal> sucursalesFinales = cargarSucursalesFinalesHU13();
        List<Inventario> inventarios = cargarInventariosHU13();
        Actualizacion actualizacion = crearActualizacion();
        actualizacion.setInventario(inventarios.get(0));

        when(actualizacionRepository.findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventarios.get(0).getInventarioID())).thenReturn(Optional.of(actualizacion));
        doThrow(NoSuchElementException.class).when(actualizacionRepository).findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventarios.get(1).getInventarioID());
        doThrow(NoSuchElementException.class).when(actualizacionRepository).findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventarios.get(2).getInventarioID());
        doThrow(NoSuchElementException.class).when(actualizacionRepository).findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventarios.get(3).getInventarioID());


        // Act + Assert
        assertTrue(() -> {
            List<Sucursal> resultado = actualizacionService.getTopSucursalesPorInventarios(inventarios);
            if(resultado.size() != sucursalesFinales.size()) return false;
            for (int i = 0; i< resultado.size(); i++){
                if(resultado.get(i).getCadena() != sucursalesFinales.get(i).getCadena()) return false;
            }
            return true;
        });
    }

    @Test
    @DisplayName("Top sucursales - Lista vacia")
    void siInvocoGetTopSucursalesPorInventariosConDatosNoValidosRetornaListaVacia(){

        // When
        List<Sucursal> sucursalesFinales = new ArrayList<>();

        List<Inventario> inventarios = new ArrayList<>();

        // Act + Assert
        assertTrue(() -> {
            List<Sucursal> resultado = actualizacionService.getTopSucursalesPorInventarios(inventarios);
            if(resultado.size() != sucursalesFinales.size()) return false;
            for (int i = 0; i< resultado.size(); i++){
                if(resultado.get(i).getCadena() != sucursalesFinales.get(i).getCadena()) return false;
            }
            return true;
        });
    }

}
