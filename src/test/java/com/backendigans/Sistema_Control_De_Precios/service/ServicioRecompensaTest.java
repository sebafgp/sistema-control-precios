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
public class ServicioRecompensaTest {
    @Mock
    private RepositorioRecompensa recompensaRepository;
    @InjectMocks
    private ImplServicioRecompensa recompensaService;

    /* HU_10 */
    @Test
    @DisplayName("Save recompensa - Guardar exitosamente")
    void siGuardoUnaRecompensaValidaSeGuardaExitosamente(){
        // Arrange
        Recompensa recompensa = crearRecompensa();

        // Act
        recompensaService.saveRecompensa(recompensa);

        // Assert
        verify(recompensaRepository, times(1)).save(recompensa);
    }

    @Test
    @DisplayName("Save recompensa - Guardar fallo")
    void siGuardoUnaRecompensaNoValidaNoSeGuardaExitosamente(){
        // Arrange
        Recompensa recompensa = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> recompensaService.saveRecompensa(recompensa));
    }

    private Recompensa crearRecompensa() {
        Recompensa r = new Recompensa(1, "Giftcard", 1000, 20, "Es una giftcard");
        return r;
    }


}
