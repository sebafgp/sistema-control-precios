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
import com.backendigans.Sistema_Control_De_Precios.service.ServicioCanje;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioRecompensa;
import com.backendigans.Sistema_Control_De_Precios.exceptions.*;
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
public class ControladorColaboradorTest {

    private JacksonTester<Colaborador> jsonColaborador;
    private JacksonTester<ControladorColaborador.CanjearRecompensaWrapper> jsonCanje;
    private JacksonTester<ControladorColaborador.updateContrasenaWrapper> jsonWrapperNuevaContrasena;
    private JacksonTester<ControladorColaborador.updateNicknameWrapper> jsonWrapperNuevoNickname;

    private MockMvc mockMvc;
    @Mock
    private ServicioColaborador colaboradorService;
    @Mock
    private ServicioRecompensa recompensaService;
    @Mock
    private ServicioCanje canjeService;
    @Mock
    private ServicioActualizacion actualizacionService;
    @InjectMocks
    private ControladorColaborador colaboradorController;

    @BeforeEach
    void setup() {
        JacksonTester.initFields(this,new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(colaboradorController).build();
    }

    /* HU_01 */

    @Test
    @DisplayName("Crear colaborador - datos validos")
    void siCreoNuevoColaboradorConDatosValidosSeGuardaEnLaBDD() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        given(colaboradorService.saveColaborador(any(Colaborador.class))).willReturn(colaborador);

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(jsonColaborador.write(colaborador).getJson(),response.getContentAsString());
    }

    @Test
    @DisplayName("Crear colaborador - datos no validos - email")
    void siCreoNuevoColaboradorConEmailNoValidoNoSeGuardaEnLaBDDYLanzaIllegalArgumentException() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        colaborador.setEmail(null);
        doThrow(IllegalArgumentException.class).when(colaboradorService).saveColaborador(any(Colaborador.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Crear colaborador - datos no validos - contrasena")
    void siCreoNuevoColaboradorConContrasenaNoValidaNoSeGuardaEnLaBDDYLanzaIllegalArgumentException() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);
        doThrow(IllegalArgumentException.class).when(colaboradorService).saveColaborador(any(Colaborador.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Crear colaborador - datos no validos - nickname")
    void siCreoNuevoColaboradorConNicknameNoValidoNoSeGuardaEnLaBDDYLanzaIllegalArgumentException() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);
        doThrow(IllegalArgumentException.class).when(colaboradorService).saveColaborador(any(Colaborador.class));

        // When
        MockHttpServletResponse response = mockMvc.perform(post("/colaborador")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonColaborador.write(colaborador).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    /* HU_05 */

    @Test
    @DisplayName("Obtener valoraciones - Lista No Vacia")
    void siInvocoGetValoracionesYExistenValoracionesRetornaUnaListaNoVacia() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();
        Actualizacion actualizacion = crearActualizacion();
        List<Actualizacion> listaActualizaciones = new ArrayList<>();
        listaActualizaciones.add(actualizacion);

        given(colaboradorService.getColaborador(colaborador.getColaboradorID())).willReturn(colaborador);
        given(actualizacionService.encontrarPorColaborador(colaborador)).willReturn(listaActualizaciones);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/valoraciones/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    @DisplayName("Obtener valoraciones - Lista Vacia")
    void siInvocoGetValoracionesYNoExistenValoracionesRetornaUnaListaVacia() throws Exception {
        // Given
        Colaborador colaborador = crearColaborador();

        given(colaboradorService.getColaborador(colaborador.getColaboradorID())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(actualizacionService).encontrarPorColaborador(colaborador);

        // When
        MockHttpServletResponse response = mockMvc.perform(get("/colaborador/valoraciones/0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    /* HU_10 */
    @Test
    @DisplayName("Canjear Recompensa - Datos validos")
    void siCanjeoRecompensaConDatosValidosRetornaStatusOK() throws Exception {
        Colaborador colaborador = crearColaborador();
        Recompensa recompensa = crearRecompensa();
        colaborador.setPuntos(recompensa.getCosto() + 1);
        ControladorColaborador.CanjearRecompensaWrapper datos = new ControladorColaborador.CanjearRecompensaWrapper(colaborador.getEmail(), colaborador.getContrasena(), recompensa.getRecompensaID());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(recompensaService.getRecompensa(recompensa.getRecompensaID())).willReturn(recompensa);

        MockHttpServletResponse response = mockMvc.perform(post("/colaborador/canjearRecompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCanje.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
        verify(recompensaService, times(1)).saveRecompensa(recompensa);
        verify(canjeService, times(1)).saveCanje(any(Canje.class));
    }

    @Test
    @DisplayName("Canjear Recompensa - Datos no validos - colaborador")
    void siCanjeoRecompensaConColaboradorNoValidoLanzaNoSuchElementException() throws Exception {
        //Colaborador colaborador = crearColaborador();
        Recompensa recompensa = crearRecompensa();
        //colaborador.setPuntos(recompensa.getCosto() + 1);
        ControladorColaborador.CanjearRecompensaWrapper datos = new ControladorColaborador.CanjearRecompensaWrapper(null, null, recompensa.getRecompensaID());

        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail(datos.getEmail(), datos.getContrasena());

        MockHttpServletResponse response = mockMvc.perform(post("/colaborador/canjearRecompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCanje.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                colaboradorService.buscarColaboradorPorEmail(datos.getEmail(), datos.getContrasena()));
    }

    @Test
    @DisplayName("Canjear Recompensa - Datos no validos - recompensa")
    void siCanjeoRecompensaConRecompensaNoValidaLanzaNoSuchElementException() throws Exception {
        Colaborador colaborador = crearColaborador();
        Recompensa recompensa = crearRecompensa();
        //colaborador.setPuntos(recompensa.getCosto() + 1);
        ControladorColaborador.CanjearRecompensaWrapper datos = new ControladorColaborador.CanjearRecompensaWrapper(colaborador.getEmail(), colaborador.getContrasena(), recompensa.getRecompensaID());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        doThrow(NoSuchElementException.class).when(recompensaService).getRecompensa(recompensa.getRecompensaID());

        MockHttpServletResponse response = mockMvc.perform(post("/colaborador/canjearRecompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCanje.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertThrows(NoSuchElementException.class, () ->
                recompensaService.getRecompensa(recompensa.getRecompensaID()));
    }

    @Test
    @DisplayName("Canjear Recompensa - exceptions - colaborador sin puntos suficientes")
    void siCanjeoRecompensaSinPuntosSuficientesDevuelveBadRequest() throws Exception {
        Colaborador colaborador = crearColaborador();
        Recompensa recompensa = crearRecompensa();
        colaborador.setPuntos(-1);
        ControladorColaborador.CanjearRecompensaWrapper datos = new ControladorColaborador.CanjearRecompensaWrapper(colaborador.getEmail(), colaborador.getContrasena(), recompensa.getRecompensaID());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(recompensaService.getRecompensa(recompensa.getRecompensaID())).willReturn(recompensa);

        MockHttpServletResponse response = mockMvc.perform(post("/colaborador/canjearRecompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCanje.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Canjear Recompensa - exceptions - recompensa sin stock suficiente")
    void siCanjeoRecompensaSinStockSuficienteDevuelveBadRequest() throws Exception {
        Colaborador colaborador = crearColaborador();
        Recompensa recompensa = crearRecompensa();
        recompensa.setStock(0);
        ControladorColaborador.CanjearRecompensaWrapper datos = new ControladorColaborador.CanjearRecompensaWrapper(colaborador.getEmail(), colaborador.getContrasena(), recompensa.getRecompensaID());

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);
        given(recompensaService.getRecompensa(recompensa.getRecompensaID())).willReturn(recompensa);

        MockHttpServletResponse response = mockMvc.perform(post("/colaborador/canjearRecompensa")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonCanje.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
    
    
    @Test
    @DisplayName("Actualizar contrasena - datos validos")
    void siActualizoContrasenaConDatosValidosSeActualizaCorrectamente() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        String nuevContrasena = "password2";
        ControladorColaborador.updateContrasenaWrapper datos = new ControladorColaborador.updateContrasenaWrapper(colaborador.getEmail(), colaborador.getContrasena(), nuevContrasena);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena())).willReturn(colaborador);


        // When
        MockHttpServletResponse response = mockMvc.perform(put("/colaborador/updateContrasena/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperNuevaContrasena.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
    }
    
    @Test
    @DisplayName("Actualizar contrasena - Colaborador no Existe ")
    void siActualizoContrasenaYNoExisteColaboradorDevuelveNotFound() throws Exception {
        //Given
        String nuevContrasena = "password2";
        ControladorColaborador.updateContrasenaWrapper datos = new ControladorColaborador.updateContrasenaWrapper("hola@mail.com", "1234", nuevContrasena);
        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("hola@mail.com","1234");


        // When
        MockHttpServletResponse response = mockMvc.perform(put("/colaborador/updateContrasena/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperNuevaContrasena.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
    
    @Test
    @DisplayName("Actualizar contrasena - Nueva contrasena vacia")
    void siActualizoContrasenaYNuevaContrasenaVaciaDevuelveBadRequest() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        String nuevContrasena = "";
        ControladorColaborador.updateContrasenaWrapper datos = new ControladorColaborador.updateContrasenaWrapper(colaborador.getEmail(), colaborador.getContrasena(), nuevContrasena);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/colaborador/updateContrasena/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperNuevaContrasena.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
    
    
    @Test
    @DisplayName("Actualizar nickname - datos validos")
    void siActualizoNicknameConDatosValidosSeActualizaCorrectamente() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        String nuevoNickname = "kcin";
        ControladorColaborador.updateNicknameWrapper datos = new ControladorColaborador.updateNicknameWrapper(colaborador.getEmail(), colaborador.getNickname(), nuevoNickname);

        given(colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getNickname())).willReturn(colaborador);


        // When
        MockHttpServletResponse response = mockMvc.perform(put("/colaborador/updateNickname/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperNuevoNickname.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        verify(colaboradorService, times(1)).saveColaborador(colaborador);
    }
    
    @Test
    @DisplayName("Actualizar nickname - Colaborador no Existe ")
    void siActualizoNicknameYNoExisteColaboradorDevuelveNotFound() throws Exception {
        //Given
        String nuevoNickname = "kcin";
        ControladorColaborador.updateNicknameWrapper datos = new ControladorColaborador.updateNicknameWrapper("hola@mail.com", "1234", nuevoNickname);
        doThrow(NoSuchElementException.class).when(colaboradorService).buscarColaboradorPorEmail("hola@mail.com","1234");


        // When
        MockHttpServletResponse response = mockMvc.perform(put("/colaborador/updateNickname/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperNuevoNickname.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
    
    @Test
    @DisplayName("Actualizar nickname - Nuevo nickname vacio")
    void siActualizoNicknameYNuevoNicknameVaciaDevuelveBadRequest() throws Exception {
        //Given
        Colaborador colaborador = crearColaborador();
        String nuevNickname = "";
        ControladorColaborador.updateNicknameWrapper datos = new ControladorColaborador.updateNicknameWrapper(colaborador.getEmail(), colaborador.getNickname(), nuevNickname);

        // When
        MockHttpServletResponse response = mockMvc.perform(put("/colaborador/updateNickname/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonWrapperNuevoNickname.write(datos).getJson())
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();

        // Then
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
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
    private Recompensa crearRecompensa() {
        Recompensa r = new Recompensa(1, "Giftcard", 1000, 20, "Es una giftcard");
        return r;
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

     /*  HU_08   */
     @Test
     @DisplayName("Buscar top de colaboradores - Top Colaboradores Existe")
     void siInvocoFindByOrderByReputacionDescYExisteAlMenosUnColaboradorEntoncesRetornarUnaListaConColaboradores(){

          //Given

          //When

          //Then

     }
 
     @Test
     @DisplayName("Buscar top de colaboradores - Top Colaboradores Existe")
     void siInvocoFindByOrderByReputacionDescYNoExisteAlMenosUnColaboradorEntoncesRetornarUnaListaVacía(){

          //Given

          //When

          //Then
          
     }
}