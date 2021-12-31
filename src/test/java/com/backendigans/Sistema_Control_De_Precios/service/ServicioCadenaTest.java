package com.backendigans.Sistema_Control_De_Precios.service;

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.*;

import com.backendigans.Sistema_Control_De_Precios.model.Cadena;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioCadena;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class ServicioCadenaTest {
    @Mock
    private RepositorioCadena cadenaRepository;

    @InjectMocks
    private ImplServicioCadena cadenaService;

    /*  HU_15   */
    @Test
    @DisplayName("Existe una cadena dado su nombre - Sí existe")
    void siInvocoExisteCadenaPorNombreYExisteRetornarEsaCadena(){
        // Arrange
        Cadena resultado;
        Cadena cadena = new Cadena();
        cadena.setCadenaID(0);
        cadena.setNombre("Unimarc");
        cadena.setSucursales(new HashSet<>());

        when(cadenaRepository.findByNombre(cadena.getNombre())).thenReturn(java.util.Optional.of(cadena));

        // Act
        resultado = cadenaService.buscarCadenaPorNombre(cadena.getNombre());

        // Assert
        assertNotNull(resultado);
        assertEquals(cadena, resultado);
    }

    /*@Test
    @DisplayName("Existe una cadena dado su nombre - No existe")
    void siInvocoExisteCadenaPorNombreYNoExisteRetornarNull(){
        // Arrange
        Cadena resultado;

        when(cadenaRepository.findByNombre("Unimarc")).thenReturn(null);

        // Act
        resultado = cadenaService.buscarCadenaPorNombre("Unimarc");

        // Assert
        assertNull(resultado);
    }*/
}
