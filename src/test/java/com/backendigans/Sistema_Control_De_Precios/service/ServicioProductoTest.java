package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

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

}
