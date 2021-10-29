
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioProducto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {
    @Autowired
    ServicioProducto servicioProducto;
    @Autowired
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
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public void add(@RequestBody Producto producto) {
        servicioProducto.saveProducto(producto);
    }

    static public class RequestWrapper {
        Producto producto;
        String email;
        String contrasena;

		public RequestWrapper(Producto producto, String email, String contrasena) {
            this.producto = producto;
            this.email = email;
            this.contrasena = contrasena;

		}
    }

    @PostMapping("/colaborador")
    @RequestMapping(value = "/colaborador", produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> addProducto(@RequestBody RequestWrapper datos){
        String email = datos.email;
        String contrasena = datos.contrasena;
        Producto producto = datos.producto;
        try {
            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            servicioProducto.colaboradorGuardaProducto(producto, colaborador);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
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
