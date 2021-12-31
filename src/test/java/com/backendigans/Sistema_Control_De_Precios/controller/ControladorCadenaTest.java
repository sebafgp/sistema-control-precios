package com.backendigans.Sistema_Control_De_Precios.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import com.backendigans.Sistema_Control_De_Precios.model.Cadena;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Vista.Sucursal;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioCadena;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
public class ControladorCadenaTest {
    private JacksonTester<Cadena> jsonCadena;
    private JacksonTester<ControladorCadena.CrearCadenaWrapper> jsonWrapperNuevaCadena;
    private MockMvc mockMvc;
    @Mock
    private ServicioCadena cadenaService;
    @Mock
    private ServicioColaborador colaboradorService;
    @InjectMocks
    private ControladorCadena cadenaController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(cadenaController).build();
    }

    /*  HU_15   */

    @Test
    @DisplayName("Agregar nueva cadena - éxito")
    void siInvocoCrearCadenaYColaboradorExisteYNoExisteUnaCadenaConElNombreDadoEntoncesCrearUnaNuevaCadenaYRetornarStatusCreated() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();

        ControladorCadena.CrearCadenaWrapper datos = new ControladorCadena.CrearCadenaWrapper(colaborador.getEmail(), colaborador.getContrasena(), "Unimarc");

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(cadenaService.buscarCadenaPorNombre("Unimarc")).willReturn(null);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/cadenas/crearCadena/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWrapperNuevaCadena.write(datos).getJson())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(cadenaService, times(1)).saveCadena(any(Cadena.class));
    }

    @Test
    @DisplayName("Agregar nueva cadena - cadena ya existe")
    void siInvocoCrearCadenaYColaboradorExisteYExisteUnaCadenaConElNombreDadoEntoncesRetornarStatusBadRequest() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        Cadena cadena = new Cadena();
        cadena.setCadenaID(0);
        cadena.setNombre("Unimarc");
        cadena.setSucursales(new HashSet<>());

        ControladorCadena.CrearCadenaWrapper datos = new ControladorCadena.CrearCadenaWrapper(colaborador.getEmail(), colaborador.getContrasena(), "Unimarc");

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(cadenaService.buscarCadenaPorNombre("Unimarc")).willReturn(cadena);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/cadenas/crearCadena/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWrapperNuevaCadena.write(datos).getJson())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Agregar nueva cadena - éxito")
    void siInvocoCrearCadenaYColaboradorNoExisteEntoncesRetornarStatusNotFound() throws Exception {
        // Given
        ControladorCadena.CrearCadenaWrapper datos = new ControladorCadena.CrearCadenaWrapper("email", "contrasena", "Unimarc");
        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("email","contrasena");

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/cadenas/crearCadena/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonWrapperNuevaCadena.write(datos).getJson())
                .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}   
