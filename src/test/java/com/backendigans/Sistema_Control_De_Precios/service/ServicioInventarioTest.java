package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioInventario;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioProducto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicioInventarioTest {

    @Mock
    private RepositorioInventario inventarioRepository;
    @Mock
    private RepositorioColaborador colaboradorRepository;

    @InjectMocks
    private ImplServicioInventario inventarioService;


    /* HU_09 */

    @Test
    @DisplayName("Buscar por precio - Lista Existe")
    void siInvocoGetProductoPorPrecioYEncuentraProductosRetornaListaNoVacia(){

        // Arrange
        List<Inventario> resultado;
        List<Inventario> inventarios = cargarInventarios();
        int precio = 2000;

        when(inventarioRepository.findByPrecioLessThanEqual(precio)).thenReturn(inventarios);

        // Act
        resultado = inventarioService.getInventariosPorPrecio(precio);

        // Assert
        assertNotNull(resultado);
        assertEquals(inventarios.size(), resultado.size());

    }

    @Test
    @DisplayName("Buscar por precio - Lista Vacia")
    void siInvocoGetProductoPorPrecioYNoEncuentraProductosRetornaListaVacia(){

        // Arrange
        List<Inventario> resultado;
        List<Inventario> inventarios = new ArrayList<>();
        int precio = 2000;

        when(inventarioRepository.findByPrecioLessThanEqual(precio)).thenReturn(inventarios);

        // Act
        resultado = inventarioService.getInventariosPorPrecio(precio);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());

    }

    /* HU_13 */

    @Test
    @DisplayName("Obtener inventarios por producto - Lista no Vacia")
    void siInvocoGetInventariosDeProductoConProductoValidoRetornaListaNoVacia(){
        // Arrange
        Producto producto = crearProducto();
        List<Inventario> resultado;
        List<Inventario> inventarios = cargarInventarios();

        when(inventarioRepository.findByProducto(producto)).thenReturn(inventarios);

        // Act
        resultado = inventarioService.getInventariosDeProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals(inventarios.size(), resultado.size());
    }

    @Test
    @DisplayName("Obtener inventarios por producto - Lista Vacia")
    void siInvocoGetInventariosDeProductoConProductoValidoPeroInventarioVacioRetornaListaVacia(){
        // Arrange
        Producto producto = crearProducto();
        List<Inventario> resultado;
        List<Inventario> inventarios = new ArrayList<>();

        when(inventarioRepository.findByProducto(producto)).thenReturn(inventarios);

        // Act
        resultado = inventarioService.getInventariosDeProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }

    @Test
    @DisplayName("Obtener inventarios por producto - Producto no Valido")
    void siInvocoGetInventariosDeProductoConProductoNoValidoRetornaListaVacia(){
        // Arrange
        Producto producto = null;
        List<Inventario> resultado;

        // Act
        resultado = inventarioService.getInventariosDeProducto(producto);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, resultado.size());
    }
}
