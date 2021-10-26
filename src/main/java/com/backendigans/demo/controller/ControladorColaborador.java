package com.backendigans.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.backendigans.demo.model.Colaborador;
import com.backendigans.demo.service.ServicioColaborador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
@RestController
@RequestMapping("/colaborador")
public class ControladorColaborador {
    @Autowired
    ServicioColaborador servColaborador;

    @GetMapping("")
    public List<Colaborador> list() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colaborador> get(@PathVariable Integer id) {
        try {
            Colaborador colaborador = servColaborador.getColaborador(id);
            return new ResponseEntity<Colaborador>(colaborador, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Colaborador>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Colaborador colaborador) {
        servColaborador.guardarColaborador(colaborador);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Colaborador colaborador, @PathVariable Integer id) {
        try {
            Colaborador existeColaborador = servColaborador.getColaborador(id);
            colaborador.setColaboradorID(id);;            
            servColaborador.guardarColaborador(colaborador);;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servColaborador.borrarColaborador(id);
    }
}
