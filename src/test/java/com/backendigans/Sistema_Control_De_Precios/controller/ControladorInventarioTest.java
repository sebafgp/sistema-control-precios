package com.backendigans.Sistema_Control_De_Precios.controller;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
public class ControladorInventarioTest {
    private JacksonTester<Producto> jsonProducto;
    private JacksonTester<ControladorInventario.RequestWrapperActualizar> jsonWrapperActualizar;
    private JacksonTester<ControladorInventario.puntuarProductoWrapper> jsonWrapperPuntuar;
    private MockMvc mockMvc;
    @Mock
    private ServicioProducto productoService;
    @Mock
    private ServicioColaborador colaboradorService;
    @Mock
    private ServicioSucursal sucursalService;
    @Mock
    private ServicioActualizacion actualizacionService;
    @Mock
    private ServicioInventario inventarioService;
    @InjectMocks
    private ControladorInventario inventarioController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(inventarioController).build();
    }

    /* HU_03 */

    @Test
    @DisplayName("Actualizar precio - datos validos")
    void siActualizoElPrecioDeUnProductoConDatosValidosSeActualizaExitosamente() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        Inventario inventario = crearInventario();
        int precio = 1000;
        inventario.setPrecio(precio);
        ControladorInventario.RequestWrapperActualizar datos = new ControladorInventario.RequestWrapperActualizar(precio, colaborador.getEmail(), colaborador.getContrasena());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(inventarioService.buscarInventarioPorId(inventario.getInventarioID())).willReturn(inventario);


        // When
        MockHttpServletResponse response = mockMvc.perform(put("/inventarios/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperActualizar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(inventarioService, times(1)).saveInventario(inventario);
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
        verify(actualizacionService, times(1)).saveActualizacion(any(Actualizacion.class));
    }

    @Test
    @DisplayName("Actualizar precio - datos no validos - producto")
    void siActualizoElPrecioDeUnProductoConProductoNoValidoNoSeActualiza() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        int precio = 1000;
        ControladorInventario.RequestWrapperActualizar datos = new ControladorInventario.RequestWrapperActualizar(precio, colaborador.getEmail(), colaborador.getContrasena());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(inventarioService).buscarInventarioPorId(1);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/inventarios/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperActualizar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                inventarioService.buscarInventarioPorId(1));
    }

    @Test
    @DisplayName("Actualizar precio - datos no validos - colaborador")
    void siActualizoElPrecioDeUnProductoConColaboradorNoValidoNoSeActualiza() throws Exception {

        //Given
        Inventario inventario = crearInventario();
        int precio = 1000;
        inventario.setPrecio(precio);
        ControladorInventario.RequestWrapperActualizar datos = new ControladorInventario.RequestWrapperActualizar(precio, "", "");

        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("","");

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/inventarios/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperActualizar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                colaboradorService.buscarColaboradorPorEmail(datos.getEmail(), datos.getContrasena()));
    }

    /* HU_04 */
    @Test
    @DisplayName("Puntuar producto - datos validos")
    void siPuntuoUnProductoConDatosValidosSeActualizaCorrectamente() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        Inventario inventario = crearInventario();
        Actualizacion actualizacion = crearActualizacion();
        ControladorInventario.puntuarProductoWrapper datos = new ControladorInventario.puntuarProductoWrapper(colaborador.getEmail(), colaborador.getContrasena(), true);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(inventarioService.buscarInventarioPorId(inventario.getInventarioID())).willReturn(inventario);
        given(actualizacionService.encontrarUltimaPorInventario(inventario)).willReturn(actualizacion);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/inventarios/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
        verify(actualizacionService, times(1)).saveActualizacion(actualizacion);
    }

    @Test
    @DisplayName("Puntuar producto - datos no validos - colaborador")
    void siPuntuoUnProductoConColaboradorNoValidoNoSeActualizaYLanzaException() throws Exception {
        //Given
        ControladorInventario.puntuarProductoWrapper datos = new ControladorInventario.puntuarProductoWrapper("", "", true);

        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("","");

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/inventarios/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                colaboradorService.buscarColaboradorPorEmail(datos.getEmail(), datos.getContrasena()));
    }

    @Test
    @DisplayName("Puntuar producto - datos no validos - inventario")
    void siPuntuoUnProductoConProductoNoValidoNoSeActualizaYLanzaException() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        ControladorInventario.puntuarProductoWrapper datos = new ControladorInventario.puntuarProductoWrapper(colaborador.getEmail(), colaborador.getContrasena(), true);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(inventarioService).buscarInventarioPorId(1);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/inventarios/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                inventarioService.buscarInventarioPorId(1));
    }

    @Test
    @DisplayName("Puntuar producto - datos no validos - ultima actualizacion")
    void siPuntuoUnProductoSinUltimaActualizacionNoSeActualiza() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        Inventario inventario = crearInventario();
        ControladorInventario.puntuarProductoWrapper datos = new ControladorInventario.puntuarProductoWrapper(colaborador.getEmail(), colaborador.getContrasena(), true);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(inventarioService.buscarInventarioPorId(inventario.getInventarioID())).willReturn(inventario);
        doThrow(NoSuchElementException.class).when(actualizacionService).encontrarUltimaPorInventario(inventario);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/inventarios/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                actualizacionService.encontrarUltimaPorInventario(inventario));
    }


}
