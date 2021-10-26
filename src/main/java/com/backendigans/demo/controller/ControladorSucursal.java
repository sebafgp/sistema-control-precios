package com.backendigans.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.backendigans.demo.model.Sucursal;
import com.backendigans.demo.service.ServicioSucursal;
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
@RequestMapping("/sucursal")
public class ControladorSucursal {
    @Autowired
    ServicioSucursal servSucursal;

    @GetMapping("")
    public List<Sucursal> list() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> get(@PathVariable Integer id) {
        try {
            Sucursal sucursal = servSucursal.getSucursal(id);
            return new ResponseEntity<Sucursal>(sucursal, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Sucursal>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Sucursal sucursal) {
        servSucursal.guardarSucursal(sucursal);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Sucursal sucursal, @PathVariable Integer id) {
        try {
            Sucursal existeSucursal = servSucursal.getSucursal(id);
            sucursal.setSucursalID(id);;            
            servSucursal.guardarSucursal(sucursal);;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servSucursal.borrarSucursal(id);
    }
}
