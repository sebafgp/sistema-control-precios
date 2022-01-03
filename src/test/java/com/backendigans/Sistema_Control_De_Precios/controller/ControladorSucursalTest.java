package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.*;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class ControladorSucursalTest {

    private JacksonTester<ControladorSucursal.RequestWrapperAgregarSucursal> jsonWrapper;
    private MockMvc mockMvc;

    @Mock
    private ServicioCadena cadenaService;
    @Mock
    private ServicioColaborador colaboradorService;
    @Mock
    private ServicioSucursal sucursalService;
    @InjectMocks
    private ControladorSucursal sucursalController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(sucursalController).build();
    }

    /* HU_16 */

    @Test
    @DisplayName("Crear sucursal - Datos validos")
    void siInvocoAddEnControladorSucursalConDatosValidosSeGuardaExitosamente() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        Cadena cadena = crearCadena();
        Sucursal sucursal = crearSucursal();

        ControladorSucursal.RequestWrapperAgregarSucursal datos = new ControladorSucursal.RequestWrapperAgregarSucursal(colaborador.getEmail(), colaborador.getContrasena(), "Chillan", "Collin", 442, cadena.getCadenaID());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(cadenaService.getCadena(cadena.getCadenaID())).willReturn(cadena);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/sucursales/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        verify(sucursalService, times(1)).saveSucursal(any(Sucursal.class));
        verify(cadenaService, times(1)).saveCadena(any(Cadena.class));
    }

    @Test
    @DisplayName("Crear sucursal - Datos no validos - Cadena no valida")
    void siInvocoAddEnControladorSucursalConCadenaNoValidaNoSeGuardaExitosamenteYLanzaNotFound() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        //Cadena cadena = crearCadena();
        //Sucursal sucursal = crearSucursal();

        ControladorSucursal.RequestWrapperAgregarSucursal datos = new ControladorSucursal.RequestWrapperAgregarSucursal(colaborador.getEmail(), colaborador.getContrasena(), "Chillan", "Collin", 442, 0);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(cadenaService).getCadena(0);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/sucursales/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                cadenaService.getCadena(0));
    }

    @Test
    @DisplayName("Crear sucursal - Datos no validos - Colaborador no valido")
    void siInvocoAddEnControladorSucursalConColaboradorNoValidoNoSeGuardaExitosamenteYLanzaNotFound() throws Exception {
        // Given
        //Colaborador colaborador = crearColaborador();
        //Cadena cadena = crearCadena();
        //Sucursal sucursal = crearSucursal();

        ControladorSucursal.RequestWrapperAgregarSucursal datos = new ControladorSucursal.RequestWrapperAgregarSucursal(null, null, "Chillan", "Collin", 442, 0);

        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail(null, null);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/sucursales/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapper.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                colaboradorService.buscarColaboradorPorEmail(null, null));
    }


}
