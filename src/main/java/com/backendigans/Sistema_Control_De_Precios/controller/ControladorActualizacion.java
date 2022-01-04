package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Cadena;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/actualizaciones")
public class ControladorActualizacion {

    @Autowired
    ServicioActualizacion servicioActualizacion;

    @PutMapping("/{id}")
    public ResponseEntity<?> agregarComentario(@RequestBody String comentario, @PathVariable Integer id) {
        try {
            Actualizacion actualizacion = servicioActualizacion.encontrarPorId(id);
            if(servicioActualizacion.agregarComentario(comentario, actualizacion)) {
                servicioActualizacion.saveActualizacion(actualizacion);
                return new ResponseEntity<>(actualizacion, HttpStatus.OK);
            } else
                throw new NoSuchElementException();
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(comentario, HttpStatus.NOT_FOUND);
        }
    }

}
