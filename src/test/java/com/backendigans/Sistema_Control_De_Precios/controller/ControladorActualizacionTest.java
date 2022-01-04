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

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.backendigans.Sistema_Control_De_Precios.exceptions.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javassist.expr.NewArray;

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ControladorActualizacionTest {

    private JacksonTester<String> jsonString;
    private MockMvc mockMvc;
    @Mock
    private ServicioActualizacion actualizacionService;
    @InjectMocks
    private ControladorActualizacion actualizacionController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(actualizacionController).build();
    }

    /* HU_17 */

    @Test
    @DisplayName("Agregar comentario - exito")
    void siAregoComentarioAActualizacionSeAgregaCorrectamente() throws Exception {
        // Given
        Actualizacion actualizacion = new Actualizacion();
        String comentario = "Comentario de prueba";
        given(actualizacionService.encontrarPorId(actualizacion.getActualizacionID())).willReturn(actualizacion);
        given(actualizacionService.agregarComentario(comentario, actualizacion)).willReturn(Boolean.TRUE);

        String url = String.format("/actualizaciones/%d", actualizacion.getActualizacionID());

        // When
        MockHttpServletResponse response = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comentario)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(actualizacionService, times(1)).agregarComentario(comentario, actualizacion);
        verify(actualizacionService, times(1)).saveActualizacion(any(Actualizacion.class));
    }

    @Test
    @DisplayName("Agregar comentario - actualizacion no existe")
    void siAregoComentarioAActualizacionNoExistenteLanzaNoSuchElementException() throws Exception {
        // Given
        Actualizacion actualizacion = new Actualizacion();
        String comentario = "Comentario de prueba";
        doThrow(NoSuchElementException.class).when(actualizacionService).encontrarPorId(0);

        String url = String.format("/actualizaciones/%d", actualizacion.getActualizacionID());

        // When
        MockHttpServletResponse response = mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(comentario)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }


}
