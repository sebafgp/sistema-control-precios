
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Vista;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/colaborador")
public class ControladorColaborador {
    @Autowired
    ServicioColaborador colaboradorService;

    @Autowired
    ServicioActualizacion actualizacionService;

    @JsonView(Vista.Colaborador.class)
    @GetMapping("")
    public List<Colaborador> list() {
        return colaboradorService.listAllColaboradores();
    }

    @JsonView(Vista.Colaborador.class)
    @GetMapping("/{colaboradorID}")
    public ResponseEntity<Colaborador> get(@PathVariable Integer colaboradorID) {
        try {
            Colaborador colaborador = colaboradorService.getColaborador(colaboradorID);
            return new ResponseEntity<Colaborador>(colaborador, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Colaborador>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Colaborador colaborador) {
        colaboradorService.saveColaborador(colaborador);
    }

    @PutMapping("/{colaboradorID}")
    public ResponseEntity<?> update(@RequestBody Colaborador colaborador, @PathVariable Integer colaboradorID) {
        try {
            Colaborador existColaborador = colaboradorService.getColaborador(colaboradorID);
            colaborador.setColaboradorID(colaboradorID);
            colaboradorService.saveColaborador(colaborador);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer colaboradorID) {

        colaboradorService.deleteColaborador(colaboradorID);
    }

    @JsonView(Vista.Actualizacion.class)
    @GetMapping("/valoraciones/{colaboradorID}")
    public ResponseEntity<List<Actualizacion>> getValoraciones(@PathVariable Integer colaboradorID) {
        try {
            Colaborador colaborador = colaboradorService.getColaborador(colaboradorID);
            List<Actualizacion> list = actualizacionService.encontrarPorColaborador(colaborador);
            return new ResponseEntity<List<Actualizacion>>(list, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Actualizacion>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/topColaboradores")
    public ResponseEntity<List<String>> getTopColaboradores() {
        try {
            List <String> colaboradores = colaboradorService.getTopColaboradores();
           
            return new ResponseEntity<List<String>>(colaboradores, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<String>>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/reputacionYActualizacionesDeColaboradorPorNickname/{nickname}")
    public ResponseEntity<List<Object>> getReputacionYActualizacionesDeColaboradorPorNickname(@PathVariable String nickname) {
        try {
            List<Object> colaborador = colaboradorService.getColaboradorRepAct(nickname);
            
            if(!colaborador.isEmpty()){
                return new ResponseEntity<List<Object>>(colaborador, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Object>>(HttpStatus.NOT_FOUND);
        }
    }
}
