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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServicioProductoTest {

    @Mock
    private RepositorioProducto productoRepository;
    @Mock
    private RepositorioColaborador colaboradorRepository;

    @InjectMocks
    private ImplServicioProducto productoService;

    /* HU_02 */

    @Test
    @DisplayName("Colaborador guarda producto - datos validos")
    void siColaboradorGuardaProductoYAmbosSonValidosGuardaProductoYActualizaColaborador(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        Producto producto = crearProducto();

        // Act
        productoService.colaboradorGuardaProducto(producto, colaborador);

        // Assert
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    @DisplayName("Colaborador guarda producto - colaborador no valido")
    void siColaboradorGuardaProductoYColaboradorNoEsValidoNoGuardaProductoYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = null;
        Producto producto = crearProducto();

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.colaboradorGuardaProducto(producto, colaborador));
    }

    @Test
    @DisplayName("Colaborador guarda producto - producto no valido")
    void siColaboradorGuardaProductoYProductoNoEsValidoNoGuardaProductoYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        Producto producto = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.colaboradorGuardaProducto(producto, colaborador));
    }

    /* HU_03 */
    @Test
    @DisplayName("Save producto - producto valido")
    void siInvocoSaveProductoYEsProductoValidoSeGuarda(){
        Producto resultado;
        Producto producto = crearProducto();
        when(productoRepository.save(producto)).thenReturn(producto);

        // Act
        resultado = productoService.saveProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals(producto, resultado);
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    @DisplayName("Save producto - producto no valido")
    void siInvocoSaveProductoYNoEsProductoValidoNoSeGuarda(){
        Producto producto = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> productoService.saveProducto(producto));
    }


    /* HU_09 */

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


    /* HU_06 */

    @Test
    @DisplayName("Buscar por nombre - existen productos")
    void siBuscoPorNombreYExisteDevolverListaConProductos (){

        // Arrange
        List<Producto> resultado;
        List<Producto> productos = cargarProductos();
        String nombre = "Tallarines";

        when(productoRepository.findByNombre(nombre)).thenReturn(productos);

        // Act
        resultado = productoService.getByNombre(nombre);

        // Assert
        assertNotNull(resultado);
        assertEquals(productos.size(), resultado.size());
        assertEquals(productos.get(0).getNombre(), resultado.get(0).getNombre());

    }

    @Test
    @DisplayName("Buscar por nombre - No existen productos")
    void siBuscoPorNombreYNoExisteDevolverListaVacia (){

        // Arrange
        List<Producto> resultado;
        List<Producto> productos = new ArrayList <>();
        String nombre = "Salsa";

        when(productoRepository.findByNombre(nombre)).thenReturn(productos);

        // Act
        resultado = productoService.getByNombre(nombre);

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

    private Colaborador crearColaborador(){
        Colaborador c = new Colaborador("ex@mail.com", "password", "nick");
        return c;
    }
    private  Producto crearProducto(){
        Producto p = new Producto(1, "Tallarines", "Carozzi", 100, "g", 1000, LocalDateTime.now());
        return p;
    }

}
