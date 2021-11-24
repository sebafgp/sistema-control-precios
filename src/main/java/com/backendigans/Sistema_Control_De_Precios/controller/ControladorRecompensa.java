
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Recompensa;
import com.backendigans.Sistema_Control_De_Precios.model.Vista;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioRecompensa;
import com.fasterxml.jackson.annotation.JsonView;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/recompensas")
public class ControladorRecompensa {
    @Autowired
    ServicioRecompensa servicioRecompensa;
    ServicioColaborador servicioColaborador;

    @JsonView(Vista.Recompensa.class)
    @GetMapping("")
    public List<Recompensa> list() {
        return servicioRecompensa.listAllRecompensas();
    }

    @JsonView(Vista.Recompensa.class)
    @GetMapping("/{id}")
    public ResponseEntity<Recompensa> get(@PathVariable Integer id) {
        try {
            Recompensa recompensa = servicioRecompensa.getRecompensa(id);
            return new ResponseEntity<Recompensa>(recompensa, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Recompensa>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Recompensa recompensa) {
        servicioRecompensa.saveRecompensa(recompensa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Recompensa recompensa, @PathVariable Integer id) {
        try {
            Recompensa existRecompensa = servicioRecompensa.getRecompensa(id);
            recompensa.setRecompensaID(id);
            servicioRecompensa.saveRecompensa(recompensa);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servicioRecompensa.deleteRecompensa(id);
    }
}