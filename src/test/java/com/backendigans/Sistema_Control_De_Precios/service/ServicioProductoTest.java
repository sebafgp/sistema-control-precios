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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServicioProductoTest {

    @Mock
    private RepositorioProducto productoRepository;

    @InjectMocks
    private ImplServicioProducto productoService;

    @Test
    @DisplayName("Buscar por precio - Lista Existe")
    void siInvocoGetProductoPorPrecioYEncuentraProductosRetornaListaNoVacia(){

        // Arrange
        List<Producto> resultado;
        List<Producto> productos = cargarProductos();
        int precio = 2000;

        when(productoRepository.findByPrecioLessThanEqual(precio)).thenReturn(productos);

        // Act
        resultado = productoService.getProductoPorPrecio(precio);

        // Assert
        assertNotNull(resultado);
        assertEquals(productos.size(), resultado.size());

    }

    @Test
    @DisplayName("Buscar por precio - Lista Vacia")
    void siInvocoGetProductoPorPrecioYNoEncuentraProductosRetornaListaVacia(){

        // Arrange
        List<Producto> resultado;
        List<Producto> productos = new ArrayList<>();;
        int precio = 2000;

        when(productoRepository.findByPrecioLessThanEqual(precio)).thenReturn(productos);

        // Act
        resultado = productoService.getProductoPorPrecio(precio);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());

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
