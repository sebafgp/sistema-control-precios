package com.backendigans.Sistema_Control_De_Precios.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    @DisplayName("Buscar reputación y actualizaciones por nickname - Lista Existe")
    void siInvocoGetColaboradorRepActYExisteEntoncesRetornarListaNoVaciaYStatusOk() throws Exception{

        // Given
        List<Object> lista = cargarDatosLista();
        String nickname = "Marco";

        given(colaboradorService.getColaboradorRepAct(nickname)).willReturn(lista);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/reputacionYActualizacionesDeColaboradorPorNickname/Marco")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }

    @Test
    @DisplayName("Buscar reputación y actualizaciones por nickname - Lista No Existe")
    void siInvocoGetColaboradorRepActYEseNicknameNoExisteEntoncesRetornarListaVaciaYStatusNotFound() throws Exception{

        // Given
        List<Object> lista = new ArrayList<>();
        String nickname = "Marco";

        given(colaboradorService.getColaboradorRepAct(nickname)).willReturn(lista);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/reputacionYActualizacionesDeColaboradorPorNickname/Marco")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    private List<Object> cargarDatosLista() {
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
    }
}