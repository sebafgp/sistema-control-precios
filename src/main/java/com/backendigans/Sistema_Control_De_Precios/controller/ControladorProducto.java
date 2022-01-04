
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.*;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/productos")
public class ControladorProducto {
    @Autowired
    ServicioProducto servicioProducto;
    @Autowired
    ServicioColaborador servicioColaborador;
    @Autowired
    ServicioActualizacion servicioActualizacion;
    @Autowired
    ServicioSucursal servicioSucursal;
    @Autowired
    ServicioInventario servicioInventario;

    @JsonView(Vista.Producto.class)
    @GetMapping("")
    public List<Producto> list() {
        return servicioProducto.listAllProductos();
    }

    @JsonView(Vista.Producto.class)
    @GetMapping("/{id}")
    public ResponseEntity<Producto> get(@PathVariable Integer id) {
        try {
            Producto producto = servicioProducto.getProducto(id);
            return new ResponseEntity<>(producto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    static public class RequestWrapper {
        Producto producto;
        String email;
        String contrasena;
        int sucursalID;
        int precio;

        public RequestWrapper(){

        }

		public RequestWrapper(Producto producto, String email, String contrasena, int sucursalID, int precio) {
            this.producto = producto;
            this.email = email;
            this.contrasena = contrasena;
            this.sucursalID = sucursalID;
            this.precio = precio;
		}

        public Producto getProducto() {
            return producto;
        }

        public void setProducto(Producto producto) {
            this.producto = producto;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        public int getSucursalID() {
            return sucursalID;
        }

        public void setSucursalID(int sucursalID) {
            this.sucursalID = sucursalID;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }
    }

    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> addProducto(@RequestBody RequestWrapper datos){
        try {
            String email = datos.email;
            String contrasena = datos.contrasena;
            int sucursalID = datos.sucursalID;
            Producto producto = datos.producto;
            int precio = datos.precio;

            if(producto == null){
                throw new NoSuchElementException();
            }

            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            Sucursal sucursal = servicioSucursal.getSucursal(sucursalID);
            Inventario inventario = new Inventario(precio, sucursal, producto);
            inventario.setSucursal(sucursal);
            sucursal.addInventario(inventario);
            colaborador.addProducto(producto);
                    
            colaborador.addPuntos(1);
            servicioColaborador.saveColaborador(colaborador);
            servicioProducto.colaboradorGuardaProducto(producto, colaborador);
            
            return new ResponseEntity<>(producto, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(datos.producto, HttpStatus.BAD_REQUEST);
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

    @JsonView(Vista.Producto.class)
    @GetMapping("/buscarPorNombre/{nombre}")
    public ResponseEntity<List<Producto>> getByNombre(@PathVariable String nombre) {
        try {
            List<Producto> productos = servicioProducto.getByNombre(nombre);
            if(!productos.isEmpty()){
                return new ResponseEntity<>(productos, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @JsonView(Vista.Producto.class)
    @GetMapping("/buscarPorPrecio/{precio}")
    public ResponseEntity<List<Producto>> buscarPorPrecio(@PathVariable Integer precio){
        try{
            List<Inventario> inventarios = servicioInventario.getInventariosPorPrecio(precio);
            List<Producto> productos = servicioProducto.getProductosDeInventarios(inventarios);
            if(!productos.isEmpty()){
                return new ResponseEntity<>(productos, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/topSucursales/{idProducto}")
    public ResponseEntity<Object> getTopSucursalesMasBaratas(@PathVariable Integer idProducto){
        try{
            Producto producto = servicioProducto.getProducto(idProducto);
            List<Inventario> inventarios = servicioInventario.getInventariosDeProducto(producto);
            if(inventarios.isEmpty()) throw new NoSuchElementException();
            List<Sucursal> sucursales = servicioActualizacion.getTopSucursalesPorInventarios(inventarios);
            if(!sucursales.isEmpty()){
                return new ResponseEntity<>(sucursales, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
