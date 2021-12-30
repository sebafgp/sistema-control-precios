package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/inventarios")
public class ControladorInventario {
    @Autowired
    ServicioProducto servicioProducto;
    @Autowired
    ServicioColaborador servicioColaborador;
    @Autowired
    ServicioActualizacion servicioActualizacion;
    @Autowired
    ServicioSucursal servicioSucursal;
    @Autowired
    ServicioInventario servicioInventario;

    static public class puntuarProductoWrapper {
        String email;
        String contrasena;
        Boolean like;

        public puntuarProductoWrapper() {
        }

        public puntuarProductoWrapper(String email, String contrasena, Boolean like) {
            this.email = email;
            this.contrasena = contrasena;
            this.like = like;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        public Boolean getLike() {
            return like;
        }

        public void setLike(Boolean like) {
            this.like = like;
        }
    }

    @PostMapping("/puntuar/{id}")
    @RequestMapping(value = "/puntuar/{id}" , produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> puntuarInventario(@RequestBody puntuarProductoWrapper datos, @PathVariable Integer inventarioID){
        String email = datos.email;
        String contrasena = datos.contrasena;
        Boolean like = datos.like;
        try {
            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            colaborador.addPuntos(1);

            Inventario inventario = servicioInventario.buscarInventarioPorId(inventarioID);
            Actualizacion actualizacion = servicioActualizacion.encontrarUltimaPorInventario(inventario);

            if (like){
                actualizacion.addPuntos(1);
            }else{
                actualizacion.addPuntos(-1);
            }

            servicioColaborador.saveColaborador(colaborador);
            servicioActualizacion.saveActualizacion(actualizacion);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    static public class RequestWrapperActualizar {
        int precio;
        String email;
        String contrasena;

        public RequestWrapperActualizar() {
        }

        public RequestWrapperActualizar(int precio, String email, String contrasena) {
            this.precio = precio;
            this.email = email;
            this.contrasena = contrasena;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarPrecio(@RequestBody RequestWrapperActualizar datos, @PathVariable Integer inventarioID){
        String email = datos.email;
        String contrasena = datos.contrasena;
        int precio = datos.precio;
        try {
            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            colaborador.addPuntos(1);

            Inventario inventario = servicioInventario.buscarInventarioPorId(inventarioID);
            inventario.setPrecio(precio);

            Actualizacion actualizacion = new Actualizacion(colaborador, inventario, precio);
            inventario.addActualizacion(actualizacion);


            servicioInventario.saveInventario(inventario);
            servicioColaborador.saveColaborador(colaborador);
            servicioActualizacion.saveActualizacion(actualizacion);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}
