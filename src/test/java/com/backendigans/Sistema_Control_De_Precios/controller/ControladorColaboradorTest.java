package com.backendigans.Sistema_Control_De_Precios.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ControladorColaboradorTest {

    private JacksonTester<Colaborador> jsonProducto;
    private MockMvc mockMvc;
    @Mock
    private ServicioColaborador colaboradorService;
    @InjectMocks
    private ControladorColaborador colaboradorController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(colaboradorController).build();
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