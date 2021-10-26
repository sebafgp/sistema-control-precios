package com.backendigans.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.backendigans.demo.model.Recompensa;
import com.backendigans.demo.service.ServicioRecompensa;
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
@RequestMapping("/recompensa")
public class ControladorRecompensa {
    @Autowired
    ServicioRecompensa servRecompensa;

    @GetMapping("")
    public List<Recompensa> list() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recompensa> get(@PathVariable Integer id) {
        try {
            Recompensa recompensa = servRecompensa.getRecompensa(id);
            return new ResponseEntity<Recompensa>(recompensa, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Recompensa>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Recompensa recompensa) {
        servRecompensa.guardarRecompensa(recompensa);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Recompensa recompensa, @PathVariable Integer id) {
        try {
            Recompensa existeRecompensa = servRecompensa.getRecompensa(id);
            recompensa.setRecompensaID(id);;            
            servRecompensa.guardarRecompensa(recompensa);;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servRecompensa.borrarRecompensa(id);
    }
}
