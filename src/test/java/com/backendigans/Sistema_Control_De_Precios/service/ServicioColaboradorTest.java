package com.backendigans.Sistema_Control_De_Precios.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.*;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServicioColaboradorTest {

    @Mock
    private RepositorioColaborador colaboradorRepository;

    @InjectMocks
    private ImplServicioColaborador colaboradorService;

    /* HU_01 */

    @Test
    @DisplayName("Guardar colaborador - datos validos")
    void siInvocoSaveColaboradorConDatosValidosSeGuarda(){
        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();
        when(colaboradorRepository.save(colaborador)).thenReturn(colaborador);

        // Act
        resultado = colaboradorService.saveColaborador(colaborador);

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador, resultado);

    }

    @Test
    @DisplayName("Guardar colaborador - datos no validos - email")
    void siInvocoSaveColaboradorConEmailNoValidoNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setEmail(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> colaboradorService.saveColaborador(colaborador));

    }

    @Test
    @DisplayName("Guardar colaborador - datos no validos - contrasena")
    void siInvocoSaveColaboradorConContrasenaNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> colaboradorService.saveColaborador(colaborador));

    }

    @Test
    @DisplayName("Guardar colaborador - datos no validos - nickname")
    void siInvocoSaveColaboradorConNicknameNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setNickname(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> colaboradorService.saveColaborador(colaborador));

    }

    /* HU_02 */

    @Test
    @DisplayName("Buscar colaborador - datos validos")
    void siInvocoBuscarColaboradorPorEmailYEsValidoLoRetorna(){
        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();
        when(colaboradorRepository.findFirstByEmailAndContrasena(colaborador.getEmail(), colaborador.getContrasena())).thenReturn(java.util.Optional.of(colaborador));

        // Act
        resultado = colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena());

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador, resultado);

    }

    @Test
    @DisplayName("Buscar colaborador - datos no validos - email")
    void siInvocoBuscarColaboradorPorEmailYEmailNoEsValidoLanzaNoSuchElementException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setEmail(null);
        when(colaboradorRepository.findFirstByEmailAndContrasena(colaborador.getEmail(), colaborador.getContrasena())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena()));

    }

    @Test
    @DisplayName("Buscar colaborador - datos no validos - contrasena")
    void siInvocoBuscarColaboradorPorEmailYContrasenaNoEsValidaLanzaNoSuchElementException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);
        when(colaboradorRepository.findFirstByEmailAndContrasena(colaborador.getEmail(), colaborador.getContrasena())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena()));

    }

    /* HU_05 */

    @Test
    @DisplayName("Get colaborador - Existe")
    void siInvocoGetColaboradorYExisteLoRetorna(){
        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();
        when(colaboradorRepository.findById(colaborador.getColaboradorID())).thenReturn(Optional.of(colaborador));

        // Act
        resultado = colaboradorService.getColaborador(colaborador.getColaboradorID());

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador, resultado);
    }

    @Test
    @DisplayName("Get colaborador - No Existe")
    void siInvocoGetColaboradorYNoExisteLanzaNoSuchElementException(){
        // Arrange
        when(colaboradorRepository.findById(0)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> colaboradorService.getColaborador(0));
    }



   /* @Test
    @DisplayName("Buscar reputación y actualizaciones por nickname - Lista Existe")
    void siInvocoGetColaboradorRepActYExisteEntoncesRetornarListaNoVacia(){

        // Arrange
        List<Object> resultado;
        List<Object> lista = cargarDatosLista();

        String nickname = "Marco";

        when(colaboradorService.getColaboradorRepAct(nickname).thenReturn(lista));

        //Act
        resultado = colaboradorService.getColaboradorRepAct(nickname);

        //Assert
        assertNotNull(resultado);
        assertEquals(lista.size(), resultado.size());
    }*/

    /* Funciones Utilidad */

    private Colaborador crearColaborador(){
        Colaborador c = new Colaborador("ex@mail.com", "password", "nick");
        return c;
    }

    /*private List<Object> cargarDatosLista() {
        Colaborador colaborador = new Colaborador(1, "marco@mail.com", "123", "Marco", 0, 0);
        Producto producto = new Producto(1, "Tallarines", "Luchetti", 1, 1000, LocalDateTime.parse("2021-11-24T12:00:00"));
        Actualizacion actualizacion = new Actualizacion(1, colaborador, producto, 2000, LocalDateTime.parse("2021-11-24T12:00:00"), 0);
        List<Object> ob = new ArrayList<>(); 

        Set<Producto> productos = new HashSet<>();
        Set<Actualizacion> actualizaciones = new HashSet<>();

        productos.add(producto);
        actualizaciones.add(actualizacion);

        colaborador.setProductos(productos);
        colaborador.setActualizacion(actualizaciones);

        ob.add(colaborador.getNickname());
        ob.add(colaborador.getReputacion());
        ob.add(colaborador.getActualizaciones());

        return ob;
    } */
}
