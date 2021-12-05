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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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
    private JacksonTester<ControladorProducto.RequestWrapperActualizar> jsonWrapperActualizar;
    private JacksonTester<ControladorProducto.puntuarProductoWrapper> jsonWrapperPuntuar;
    private MockMvc mockMvc;
    @Mock
    private ServicioProducto productoService;
    @Mock
    private ServicioColaborador colaboradorService;
    @Mock
    private ServicioSucursal sucursalService;
    @Mock
    private ServicioActualizacion actualizacionService;
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
        List<Producto> productos = cargarProductos();
        int precio = 2000;

        given(productoService.getProductoPorPrecio(precio)).willReturn(productos);

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
        List<Producto> productos = new ArrayList<>();
        int precio = 0;

        given(productoService.getProductoPorPrecio(precio)).willReturn(productos);

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
        Sucursal sucursal = creaSucursal();
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, colaborador.getEmail(), colaborador.getContrasena(), 1);

        given(sucursalService.getSucursal(1)).willReturn(sucursal);
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
        Sucursal sucursal = creaSucursal();
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, "", "", 1);

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
        Sucursal sucursal = creaSucursal();
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, "", "", 1);

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
        ControladorProducto.RequestWrapper datos = new ControladorProducto.RequestWrapper(producto, "", "", 0);

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

    /* HU_03 */

    @Test
    @DisplayName("Actualizar precio - datos validos")
    void siActualizoElPrecioDeUnProductoConDatosValidosSeActualizaExitosamente() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = crearProducto();
        int precio = 1000;
        ControladorProducto.RequestWrapperActualizar datos = new ControladorProducto.RequestWrapperActualizar(precio, colaborador.getEmail(), colaborador.getContrasena());

        given(productoService.getProducto(producto.getProductoID())).willReturn(producto);
        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);


        // When
        MockHttpServletResponse response = mockMvc.perform(put("/productos/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperActualizar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(productoService, times(1)).saveProducto(producto);
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
        verify(actualizacionService, times(1)).saveActualizacion(any(Actualizacion.class));
    }

    @Test
    @DisplayName("Actualizar precio - datos no validos - producto")
    void siActualizoElPrecioDeUnProductoConProductoNoValidoNoSeActualiza() throws Exception {

        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = null;
        int precio = 1000;
        ControladorProducto.RequestWrapperActualizar datos = new ControladorProducto.RequestWrapperActualizar(precio, "", "");

        doThrow(NoSuchElementException.class).when(productoService).getProducto(1);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/productos/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperActualizar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                productoService.getProducto(1));
    }

    @Test
    @DisplayName("Actualizar precio - datos no validos - colaborador")
    void siActualizoElPrecioDeUnProductoConColaboradorNoValidoNoSeActualiza() throws Exception {

        //Given
        Colaborador colaborador = null;
        Producto producto = crearProducto();
        int precio = 1000;
        ControladorProducto.RequestWrapperActualizar datos = new ControladorProducto.RequestWrapperActualizar(precio, "", "");

        given(productoService.getProducto(producto.getProductoID())).willReturn(producto);
        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("","");

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/productos/actualizar/1")
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
        Producto producto = crearProducto();
        Actualizacion actualizacion = crearActualizacion();
        ControladorProducto.puntuarProductoWrapper datos = new ControladorProducto.puntuarProductoWrapper(colaborador.getEmail(), colaborador.getContrasena(), true);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(productoService.getProducto(producto.getProductoID())).willReturn(producto);
        given(actualizacionService.encontrarUltimaPorProducto(producto)).willReturn(Optional.of(actualizacion));


        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
        verify(actualizacionService, times(1)).saveActualizacion(actualizacion);
    }

    @Test
    @DisplayName("Puntuar producto - datos no validos - colaborador")
    void siPuntuoUnProductoConColaboradorNoValidoNoSeActualizaYLanzaException() throws Exception {
        //Given
        Colaborador colaborador = null;
        Producto producto = crearProducto();
        Actualizacion actualizacion = crearActualizacion();
        ControladorProducto.puntuarProductoWrapper datos = new ControladorProducto.puntuarProductoWrapper("", "", true);

        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("","");

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos/puntuar/1")
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
    @DisplayName("Puntuar producto - datos no validos - producto")
    void siPuntuoUnProductoConProductoNoValidoNoSeActualizaYLanzaException() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = null;
        Actualizacion actualizacion = crearActualizacion();
        ControladorProducto.puntuarProductoWrapper datos = new ControladorProducto.puntuarProductoWrapper(colaborador.getEmail(), colaborador.getContrasena(), true);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(productoService).getProducto(1);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                productoService.getProducto(1));
    }

    @Test
    @DisplayName("Puntuar producto - datos no validos - ultima actualizacion")
    void siPuntuoUnProductoSinUltimaActualizacionNoSeActualiza() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        Producto producto = crearProducto();
        Optional<Object> actualizacion = Optional.empty();
        ControladorProducto.puntuarProductoWrapper datos = new ControladorProducto.puntuarProductoWrapper(colaborador.getEmail(), colaborador.getContrasena(), true);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(productoService.getProducto(1)).willReturn(producto);
        given(actualizacionService.encontrarUltimaPorProducto(producto)).willReturn(Optional.empty());

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/productos/puntuar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperPuntuar.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }



    /* Funciones Utilidad */

    private List<Producto> cargarProductos(){
        List<Producto> productos = new ArrayList<>();

        Producto p1 = new Producto();
        p1.setProductoID(1);
        p1.setNombre("Tallarines");
        p1.setMarca("Lucchetti");
        p1.setCantidad(1);
        p1.setPrecio(1000);
        p1.setFechaActualizacion(LocalDateTime.now());
        productos.add(p1);

        Producto p2 = new Producto();
        p2.setProductoID(2);
        p2.setNombre("Tallarines");
        p2.setMarca("Carozzi");
        p2.setCantidad(1);
        p2.setPrecio(1500);
        p2.setFechaActualizacion(LocalDateTime.now());
        productos.add(p2);

        return productos;
    }

    private Colaborador crearColaborador(){
        Colaborador c = new Colaborador("ex@mail.com", "password", "nick");
        return c;
    }
    private  Producto crearProducto(){
        Producto p = new Producto(1, "Tallarines", "Carozzi", 100, "g", 1000, LocalDateTime.now());
        return p;
    }
    private Sucursal creaSucursal() {
        Sucursal s = new Sucursal(1, 1, "Chillan", "Collin", 111);
        return s;
    }
    private Actualizacion crearActualizacion(){
        Colaborador c = crearColaborador();
        Producto p = crearProducto();
        Actualizacion act = new Actualizacion(c, p, 1000);
        return act;
    }

}
