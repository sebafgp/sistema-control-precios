package com.backendigans.Sistema_Control_De_Precios.service;

import static com.backendigans.Sistema_Control_De_Precios.utilities.FuncionesUtilidad.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import java.util.*;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioColaborador;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
public class ServicioColaboradorTest {

    @Mock
    private RepositorioColaborador colaboradorRepository;

    @InjectMocks
    private ImplServicioColaborador colaboradorService;

    /* HU_01 */

    @Test
    @DisplayName("Guardar colaborador - datos validos")
    void siInvocoSaveColaboradorConDatosValidosSeGuarda(){
        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();
        when(colaboradorRepository.save(colaborador)).thenReturn(colaborador);

        // Act
        resultado = colaboradorService.saveColaborador(colaborador);

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador, resultado);

    }

    @Test
    @DisplayName("Guardar colaborador - datos no validos - email")
    void siInvocoSaveColaboradorConEmailNoValidoNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setEmail(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> colaboradorService.saveColaborador(colaborador));

    }

    @Test
    @DisplayName("Guardar colaborador - datos no validos - contrasena")
    void siInvocoSaveColaboradorConContrasenaNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> colaboradorService.saveColaborador(colaborador));

    }

    @Test
    @DisplayName("Guardar colaborador - datos no validos - nickname")
    void siInvocoSaveColaboradorConNicknameNoValidaNoSeGuardaYLanzaIllegalArgumentException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setNickname(null);

        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> colaboradorService.saveColaborador(colaborador));

    }

    /* HU_02 */

    @Test
    @DisplayName("Buscar colaborador - datos validos")
    void siInvocoBuscarColaboradorPorEmailYEsValidoLoRetorna(){
        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();
        when(colaboradorRepository.findFirstByEmailAndContrasena(colaborador.getEmail(), colaborador.getContrasena())).thenReturn(java.util.Optional.of(colaborador));

        // Act
        resultado = colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena());

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador, resultado);

    }

    @Test
    @DisplayName("Buscar colaborador - datos no validos - email")
    void siInvocoBuscarColaboradorPorEmailYEmailNoEsValidoLanzaNoSuchElementException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setEmail(null);
        when(colaboradorRepository.findFirstByEmailAndContrasena(colaborador.getEmail(), colaborador.getContrasena())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena()));

    }

    @Test
    @DisplayName("Buscar colaborador - datos no validos - contrasena")
    void siInvocoBuscarColaboradorPorEmailYContrasenaNoEsValidaLanzaNoSuchElementException(){
        // Arrange
        Colaborador colaborador = crearColaborador();
        colaborador.setContrasena(null);
        when(colaboradorRepository.findFirstByEmailAndContrasena(colaborador.getEmail(), colaborador.getContrasena())).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> colaboradorService.buscarColaboradorPorEmail(colaborador.getEmail(), colaborador.getContrasena()));

    }

    /* HU_05 */

    @Test
    @DisplayName("Get colaborador - Existe")
    void siInvocoGetColaboradorYExisteLoRetorna(){
        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();
        when(colaboradorRepository.findById(colaborador.getColaboradorID())).thenReturn(Optional.of(colaborador));

        // Act
        resultado = colaboradorService.getColaborador(colaborador.getColaboradorID());

        // Assert
        assertNotNull(resultado);
        assertEquals(colaborador, resultado);
    }

    @Test
    @DisplayName("Get colaborador - No Existe")
    void siInvocoGetColaboradorYNoExisteLanzaNoSuchElementException(){
        // Arrange
        when(colaboradorRepository.findById(0)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(NoSuchElementException.class,
                () -> colaboradorService.getColaborador(0));
    }


    /*  HU_07   */
    @Test
    @DisplayName("Buscar colaborador por nickname - Colaborador existe")
    void siInvocoGetColaboradorByNicknameYExisteEntoncesRetornarColaboradorConEseNickname(){

        // Arrange
        Colaborador resultado;
        Colaborador colaborador = crearColaborador();

        String nickname = "nick";

        when(colaboradorRepository.findFirstByNickname(nickname)).thenReturn(Optional.of(colaborador));

        //Act
        
        resultado = colaboradorService.getColaboradorByNickname(nickname);

        //Assert
        assertNotNull(resultado);
        assertEquals(colaborador.getEmail(), resultado.getEmail());
    }

    @Test
    @DisplayName("Buscar colaborador por nickname - Colaborador no existe")
    void siInvocoGetColaboradorByNicknameYEsteNoExisteEntoncesRetornarNull(){

        // Arrange
        Colaborador resultado;
        String nickname = "nick";

        when(colaboradorRepository.findFirstByNickname(nickname)).thenReturn(null);

        //Act
        resultado = colaboradorService.getColaboradorByNickname(nickname);

        //Assert
        assertNull(resultado);
    }

    /*  HU_08   */
    @Test
    @DisplayName("Buscar top de colaboradores - Existe al menos un colaborador")
    void siInvocoFindByOrderByReputacionDescYExisteAlMenosUnColaboradorEntoncesRetornarUnaListaConColaboradores(){

         //Arrange
         List <Colaborador> resultado;
         Colaborador colaborador= crearColaborador();
         List <Colaborador> lista= new ArrayList<>();
         lista.add(colaborador);
         when(colaboradorRepository.findByOrderByReputacionDesc(Sort.by(Sort.Order.desc("reputacion")))).thenReturn(lista);

         //Act
         resultado= colaboradorService.getTopColaboradores();

         //Assert
         assertNotNull(resultado);
         assertEquals(lista.size(), resultado.size());

    }

    @Test
    @DisplayName("Buscar top de colaboradores - No existen colaboradores")
    void siInvocoFindByOrderByReputacionDescYNoExisteAlMenosUnColaboradorEntoncesRetornarUnaListaVacia(){

         //Arrange
         List <Colaborador> resultado;
         List <Colaborador> lista= new ArrayList<>();
         when(colaboradorRepository.findByOrderByReputacionDesc(Sort.by(Sort.Order.desc("reputacion")))).thenReturn(lista);

         //Act
         resultado= colaboradorService.getTopColaboradores();

         //Assert
         assertNotNull(resultado);
         assertEquals(0, resultado.size());
         
    }
}
