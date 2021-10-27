
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioSucursal;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/sucursales")
public class ControladorSucursal {
    @Autowired
    ServicioSucursal servicioSucursal;
    ServicioColaborador servicioColaborador;

    @GetMapping("")
    public List<Sucursal> list() {
        return servicioSucursal.listAllSucursals();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> get(@PathVariable Integer id) {
        try {
            Sucursal sucursal = servicioSucursal.getSucursal(id);
            return new ResponseEntity<Sucursal>(sucursal, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Sucursal>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public void add(@RequestBody Sucursal sucursal) {
        servicioSucursal.saveSucursal(sucursal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Sucursal sucursal, @PathVariable Integer id) {
        try {
            Sucursal existSucursal = servicioSucursal.getSucursal(id);
            sucursal.setSucursalID(id);
            servicioSucursal.saveSucursal(sucursal);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servicioSucursal.deleteSucursal(id);
    }
}