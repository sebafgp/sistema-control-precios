package com.backendigans.demo.controller;

import java.util.List;
import java.util.NoSuchElementException;

import com.backendigans.demo.model.Producto;
import com.backendigans.demo.service.ServicioProducto;
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
@RequestMapping("/producto")
public class ControladorProducto {
    @Autowired
    ServicioProducto servProducto;

    @GetMapping("")
    public List<Producto> list() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> get(@PathVariable Integer id) {
        try {
            Producto producto = servProducto.getProducto(id);
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public void add(@RequestBody Producto producto) {
        servProducto.guardarProducto(producto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Integer id) {
        try {
            Producto existeProducto = servProducto.getProducto(id);
            producto.setProductoID(id);;            
            servProducto.guardarProducto(producto);;
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servProducto.borrarProducto(id);
    }
}
