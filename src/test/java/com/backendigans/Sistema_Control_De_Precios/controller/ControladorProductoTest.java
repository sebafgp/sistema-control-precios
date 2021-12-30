package com.backendigans.Sistema_Control_De_Precios.controller;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class ControladorProductoTest {

    private JacksonTester<Producto> jsonProducto;
    private JacksonTester<ControladorProducto.RequestWrapper> jsonWrapper;
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
    private ControladorProducto productoController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    /* HU_09 */

    @Test
    @DisplayName("Buscar por precio - Lista Existe")
    void siInvocoBuscarPorPrecioYEncuentraProductosRetornarListaNoVacia() throws Exception {

        // Given
        List<Inventario> inventarios = cargarInventarios();
        List<Producto> productos = cargarProductos();
        int precio = 2000;

        given(inventarioService.getInventariosPorPrecio(precio)).willReturn(inventarios);
        given(productoService.getProductosDeInventarios(inventarios)).willReturn(productos);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/productos/buscarPorPrecio/2000")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    @DisplayName("Buscar por precio - Lista Vacia")
    void siInvocoBuscarPorPrecioYNoEncuentraProductosRetornarListaVaciaYRetornaNoSuchElementException() throws Exception {

        // Given
        List<Inventario> inventarios = new ArrayList<>();
        List<Producto> productos = new ArrayList<>();
        int precio = 0;

        given(inventarioService.getInventariosPorPrecio(precio)).willReturn(inventarios);
        given(productoService.getProductosDeInventarios(inventarios)).willReturn(productos);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/productos/buscarPorPrecio/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    @Test
    void siBuscoPorNombreYExisteDevolverListaConProductos () throws Exception {

        // Given
        List<Producto> productos = cargarProductos();
        String nombre = "Tallarines";

        given(productoService.getByNombre(nombre)).willReturn(productos);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/productos/buscarPorNombre/Tallarines")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    void siBuscoPorNombreYNoExisteDevolverListaVacia () throws Exception {

        // Given
        List<Producto> productos = new ArrayList<>();
        String nombre = "Salsa";

        given(productoService.getByNombre(nombre)).willReturn(productos);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/productos/buscarPorNombre/Salsa")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());

    }

    /* HU_02 */

    @Test
    @DisplayName("Agregar producto - datos validos")
    void cuandoUnColaboradorValidoAgregaUnProductoValidoSeAgregaALaBDD() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = crearProducto();
        Sucursal sucursal = crearSucursal();
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, colaborador.getEmail(),
                                                    colaborador.getContrasena(), sucursal.getSucursalID(), 1000);

        given(sucursalService.getSucursal(sucursal.getSucursalID())).willReturn(sucursal);
        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);


        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Agregar producto - datos no validos - colaborador")
    void cuandoUnColaboradorNoValidoAgregaUnProductoValidoNoSeAgregaALaBDDYLanzaBAD_REQUEST() throws Exception {

        //Given
        Colaborador colaborador = null;
        Producto producto = crearProducto();
        Sucursal sucursal = crearSucursal();
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, "", "", sucursal.getSucursalID(), 1000);

        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("","");

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                colaboradorService.buscarColaboradorPorEmail(datos.getEmail(), datos.getContrasena()));
    }

    @Test
    @DisplayName("Agregar producto - datos no validos - producto")
    void cuandoUnColaboradorValidoAgregaUnProductoNoValidoNoSeAgregaALaBDDYLanzaBAD_REQUEST() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = null;
        Sucursal sucursal = crearSucursal();
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, "", "", sucursal.getSucursalID(), 1000);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Agregar producto - datos no validos - sucursal")
    void cuandoUnColaboradorValidoAgregaUnProductoConSucursalInvalidaNoSeAgregaALaBDDYLanzaBAD_REQUEST() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = crearProducto();
        Sucursal sucursal = null;
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, "", "", 0, 0);

        doThrow(NoSuchElementException.class).when(sucursalService).getSucursal(datos.getSucursalID());

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                sucursalService.getSucursal(datos.getSucursalID()));
    }


}
