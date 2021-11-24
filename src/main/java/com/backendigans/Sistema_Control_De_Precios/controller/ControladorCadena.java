
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Cadena;
import com.backendigans.Sistema_Control_De_Precios.model.Vista;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioCadena;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/cadenas")
public class ControladorCadena {
    @Autowired
    ServicioCadena servicioCadena;
    ServicioColaborador servicioColaborador;

    @JsonView(Vista.Cadena.class)
    @GetMapping("")
    public List<Cadena> list() {
        return servicioCadena.listAllCadenas();
    }

    @JsonView(Vista.Cadena.class)
    @GetMapping("/{id}")
    public ResponseEntity<Cadena> get(@PathVariable Integer id) {
        try {
            Cadena cadena = servicioCadena.getCadena(id);
            return new ResponseEntity<Cadena>(cadena, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Cadena>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Cadena cadena) {
        servicioCadena.saveCadena(cadena);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Cadena cadena, @PathVariable Integer id) {
        try {
            Cadena existCadena = servicioCadena.getCadena(id);
            cadena.setCadenaID(id);
            servicioCadena.saveCadena(cadena);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servicioCadena.deleteCadena(id);
    }
}