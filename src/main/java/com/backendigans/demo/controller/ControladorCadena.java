package com.backendigans.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.backendigans.demo.model.Cadena;
import com.backendigans.demo.service.ServicioCadena;
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

@RestController
@RequestMapping("/cadena")
public class ControladorCadena {
    @Autowired
    ServicioCadena servCadena;

    @GetMapping("")
    public List<Cadena> list() {
        return servCadena.getCadenas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cadena> get(@PathVariable Integer id) {
        try {
            Cadena cadena = servCadena.getCadena(id);
            return new ResponseEntity<Cadena>(cadena, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Cadena>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public void add(@RequestBody Cadena cadena) {
        servCadena.guardarCadena(cadena);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Cadena cadena, @PathVariable Integer id) {
        try {
            Cadena existeCadena = servCadena.getCadena(id);
            cadena.setCadenaID(id);;            
            servCadena.guardarCadena(cadena);;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servCadena.borrarCadena(id);
    }
}
