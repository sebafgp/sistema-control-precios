
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioProducto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {
    @Autowired
    ServicioProducto servicioProducto;
    ServicioColaborador servicioColaborador;

    @GetMapping("")
    public List<Producto> list() {
        return servicioProducto.listAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> get(@PathVariable Integer id) {
        try {
            Producto producto = servicioProducto.getProducto(id);
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/")
    public void add(@RequestBody Producto producto) {
        servicioProducto.saveProducto(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Producto producto, @PathVariable Integer id) {
        try {
            Producto existProducto = servicioProducto.getProducto(id);
            producto.setProductoID(id);
            servicioProducto.saveProducto(producto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        servicioProducto.deleteProducto(id);
    }
}