
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

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

    @GetMapping("")
    public List<Colaborador> list() {
        return colaboradorService.listAllColaboradores();
    }

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
}