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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ControladorProductoTest {

    private JacksonTester<Producto> jsonProducto;
    private MockMvc mockMvc;
    @Mock
    private ServicioProducto productoService;
    @InjectMocks
    private ControladorProducto productoController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

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

}
