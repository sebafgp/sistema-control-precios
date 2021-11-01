
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioProducto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> addProducto(@RequestBody RequestWrapper datos){
        String email = datos.email;
        String contrasena = datos.contrasena;
        Producto producto = datos.producto;
        producto.setFecha_actualizacion(LocalDateTime.now());


        try {
            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            colaborador.addPuntos(1);

            Actualizacion actualizacion = new Actualizacion(colaborador, producto, producto.getPrecio());

            servicioActualizacion.saveActualizacion(actualizacion);
            servicioColaborador.saveColaborador(colaborador);
            servicioProducto.colaboradorGuardaProducto(producto, colaborador);
            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }

    static public class puntuarProductoWrapper {
        String email;
        String contrasena;
        Boolean like;

		public puntuarProductoWrapper(String email, String contrasena, Boolean like) {
            this.email = email;
            this.contrasena = contrasena;
            this.like = like;
		}
    }

    @PostMapping("/puntuar/{id}")
    @RequestMapping(value = "/puntuar" , produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> addProducto(@RequestBody puntuarProductoWrapper datos, @PathVariable Integer id){
        String email = datos.email;
        String contrasena = datos.contrasena;
        Boolean like = datos.like;
        try {
            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            colaborador.addPuntos(1);
            servicioColaborador.saveColaborador(colaborador);

            Producto producto = servicioProducto.getProducto(id);

            Actualizacion actualizacion = servicioActualizacion.encontrarUltimaPorProducto(producto).get();

            if (like){
                actualizacion.addPuntos(1);
            }else{
                actualizacion.addPuntos(-1);
            }

            servicioActualizacion.saveActualizacion(actualizacion);

            return new ResponseEntity<Object>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
        }
    }



    static public class RequestWrapperActualizar {
        int precio;
        String email;
        String contrasena;
        public RequestWrapperActualizar(int precio, String email, String contrasena) {
            this.precio = precio;
            this.email = email;
            this.contrasena = contrasena;
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarPrecio(@RequestBody RequestWrapperActualizar datos, @PathVariable Integer id){
        String email = datos.email;
        String contrasena = datos.contrasena;
        int precio = datos.precio;
        try {
            Producto producto = servicioProducto.getProducto(id);
            producto.setPrecio(precio);
            producto.setFecha_actualizacion(LocalDateTime.now());

            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            colaborador.addPuntos(1);
            Actualizacion actualizacion = new Actualizacion(colaborador, producto, precio);


            servicioProducto.saveProducto(producto);
            servicioColaborador.saveColaborador(colaborador);
            servicioActualizacion.saveActualizacion(actualizacion);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
