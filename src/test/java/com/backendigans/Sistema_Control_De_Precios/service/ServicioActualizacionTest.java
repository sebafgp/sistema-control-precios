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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ServicioActualizacionTest {
    @Mock
    private RepositorioActualizacion actualizacionRepository;

    @InjectMocks
    private ImplServicioActualizacion actualizacionService;

    /* HU_03 */
    @Test
    @DisplayName("Save actualizacion - datos validos")
    void siInvocoSaveActualizacionConActualizacionValidaSeGuardaCorrectamente(){
        // Arrange
        Actualizacion actualizacion = crearActualizacion();

        // Act
        actualizacionService.saveActualizacion(actualizacion);

        // Assert
        verify(actualizacionRepository, times(1)).save(actualizacion);
    }

    @Test
    @DisplayName("Save actualizacion - datos no validos")
    void siInvocoSaveActualizacionConActualizacionNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Actualizacion actualizacion = null;

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> actualizacionService.saveActualizacion(actualizacion));
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

}
