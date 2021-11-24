package com.backendigans.Sistema_Control_De_Precios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServicioColaboradorTest {

    @Mock
    private RepositorioColaborador colaboradorRepository;

    @InjectMocks
    private ImplServicioColaborador colaboradorService;

   /* @Test
    @DisplayName("Buscar reputación y actualizaciones por nickname - Lista Existe")
    void siInvocoGetColaboradorRepActYExisteEntoncesRetornarListaNoVacia(){

        // Arrange
        List<Object> resultado;
        List<Object> lista = cargarDatosLista();

        String nickname = "Marco";

        when(colaboradorService.getColaboradorRepAct(nickname).thenReturn(lista));

        //Act
        resultado = colaboradorService.getColaboradorRepAct(nickname);

        //Assert
        assertNotNull(resultado);
        assertEquals(lista.size(), resultado.size());
    }*/

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
