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
import java.util.*;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServicioCanjeTest {
    @Mock
    private RepositorioCanje canjeRepository;
    @InjectMocks
    private ImplServicioCanje canjeService;

    /* HU_10 */
    @Test
    @DisplayName("Save actualizacion - datos validos")
    void siInvocoSaveActualizacionConActualizacionValidaSeGuardaCorrectamente(){
        // Arrange
        Canje canje = crearCanje();

        // Act
        canjeService.saveCanje(canje);

        // Assert
        verify(canjeRepository, times(1)).save(canje);
    }

    @Test
    @DisplayName("Save actualizacion - datos no validos")
    void siInvocoSaveActualizacionConActualizacionNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Canje canje = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> canjeService.saveCanje(canje));
    }

    /* Funciones Utilidad */

    private Colaborador crearColaborador(){
        Colaborador c = new Colaborador("ex@mail.com", "password", "nick");
        return c;
    }
    private Recompensa crearRecompensa() {
        Recompensa r = new Recompensa(1, "Giftcard", 1000, 20, "Es una giftcard");
        return r;
    }
    private Canje crearCanje() {
        Colaborador c = crearColaborador();
        Recompensa r = crearRecompensa();
        Canje canje = new Canje(1, LocalDateTime.now(), c, r);
        return canje;
    }

}
