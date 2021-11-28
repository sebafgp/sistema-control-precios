package com.backendigans.Sistema_Control_De_Precios.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.*;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javassist.expr.NewArray;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ControladorColaboradorTest {

    private JacksonTester<Colaborador> jsonColaborador;
    private MockMvc mockMvc;
    @Mock
    private ServicioColaborador colaboradorService;
    @Mock
    private ServicioActualizacion actualizacionService;
    @InjectMocks
    private ControladorColaborador colaboradorController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(colaboradorController).build();
    }

    /* HU_01 */

    @Test
    @DisplayName("Crear colaborador - datos validos")
    void siCreoNuevoColaboradorConDatosValidosSeGuardaEnLaBDD() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        given(colaboradorService.saveColaborador(any(Colaborador.class))).willReturn(colaborador);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(jsonColaborador.write(colaborador).getJson(),response.getContentAsString());
    }

    @Test
    @DisplayName("Crear colaborador - datos no validos - email")
    void siCreoNuevoColaboradorConEmailNoValidoNoSeGuardaEnLaBDDYLanzaIllegalArgumentException() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        colaborador.setEmail(null);
        doThrow(IllegalArgumentException.class).when(colaboradorService).saveColaborador(any(Colaborador.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Crear colaborador - datos no validos - contrasena")
    void siCreoNuevoColaboradorConContrasenaNoValidaNoSeGuardaEnLaBDDYLanzaIllegalArgumentException() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);
        doThrow(IllegalArgumentException.class).when(colaboradorService).saveColaborador(any(Colaborador.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Crear colaborador - datos no validos - nickname")
    void siCreoNuevoColaboradorConNicknameNoValidoNoSeGuardaEnLaBDDYLanzaIllegalArgumentException() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);
        doThrow(IllegalArgumentException.class).when(colaboradorService).saveColaborador(any(Colaborador.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /* HU_05 */

    @Test
    @DisplayName("Obtener valoraciones - Lista No Vacia")
    void siInvocoGetValoracionesYExistenValoracionesRetornaUnaListaNoVacia() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        Actualizacion actualizacion = crearActualizacion();
        List<Actualizacion> listaActualizaciones = new ArrayList<>();
        listaActualizaciones.add(actualizacion);

        given(colaboradorService.getColaborador(colaborador.getColaboradorID())).willReturn(colaborador);
        given(actualizacionService.encontrarPorColaborador(colaborador)).willReturn(listaActualizaciones);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/valoraciones/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("Obtener valoraciones - Lista Vacia")
    void siInvocoGetValoracionesYNoExistenValoracionesRetornaUnaListaVacia() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();

        given(colaboradorService.getColaborador(colaborador.getColaboradorID())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(actualizacionService).encontrarPorColaborador(colaborador);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/valoraciones/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }



    /* Funciones Utilidad */

    private Colaborador crearColaborador(){
        Colaborador c = new Colaborador("ex@mail.com", "password", "nick");
        return c;
    }
    private  Producto crearProducto(){
        Producto p = new Producto(1, "Tallarines", "Carozzi", 100, "g", 1000, LocalDateTime.now());
        return p;
    }
    private Actualizacion crearActualizacion(){
        Colaborador c = crearColaborador();
        Producto p = crearProducto();
        Actualizacion act = new Actualizacion(c, p, 1000);
        return act;
    }



    //HU07
    @Test
    @DisplayName("Buscar colaborador por nickname - Colaborador existe")
    void siInvocoGetColaboradorByNicknameYExisteEntoncesRetornarEseColaboradorYStatusOk() throws Exception{

        // Given
        Colaborador colaborador = new Colaborador(1, "marco@mail.com", "123", "Marco", 0, 10);
        String nickname = "Marco";

        given(colaboradorService.getColaboradorByNickname(nickname)).willReturn(colaborador);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/reputacionYActualizacionesDeColaboradorPorNickname/Marco")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("Buscar reputación y actualizaciones por nickname - Colaborador no existe")
    void siInvocoGetColaboradorByNicknameYEseNicknameNoExisteEntoncesRetornarNoSuchElementExceptionYStatusNotFound() throws Exception{

        //Given
        String nickname = "Marco";

        given(colaboradorService.getColaboradorByNickname(nickname)).willThrow(new NoSuchElementException());

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/reputacionYActualizacionesDeColaboradorPorNickname/Marco")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    private LinkedHashMap<String, Object> cargarDatosHU07() {
        Colaborador colaborador = new Colaborador(1, "marco@mail.com", "123", "Marco", 0, 10);
        Producto producto = new Producto(1, "Tallarines", "Luchetti", 1, "grs", 1000, LocalDateTime.parse("2021-11-24T12:00:00"));
        Actualizacion actualizacion = new Actualizacion(1, colaborador, producto, 2000, LocalDateTime.parse("2021-11-24T12:00:00"), 0);

        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

        Set<Producto> productos = new HashSet<>();
        Set<Actualizacion> actualizaciones = new HashSet<>();

        productos.add(producto);
        actualizaciones.add(actualizacion);

        colaborador.setProductos(productos);
        colaborador.setActualizacion(actualizaciones);

        map.put("nickname", colaborador.getNickname());
        map.put("reputacion", colaborador.getReputacion());
        map.put("actualizaciones", colaborador.getActualizaciones());
        return map;
    }
}