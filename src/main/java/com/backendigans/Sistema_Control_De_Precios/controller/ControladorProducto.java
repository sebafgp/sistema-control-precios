
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import com.backendigans.Sistema_Control_De_Precios.model.Vista;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioProducto;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioSucursal;
import com.fasterxml.jackson.annotation.JsonView;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioActualizacion;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioColaborador;

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
            return new ResponseEntity<Producto>(producto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
        }
    }

    static public class RequestWrapper {
        Producto producto;
        String email;
        String contrasena;
        int sucursalID;

        public RequestWrapper(){

        }

		public RequestWrapper(Producto producto, String email, String contrasena, int sucursalID) {
            this.producto = producto;
            this.email = email;
            this.contrasena = contrasena;
            this.sucursalID = sucursalID;
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
    }

    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> addProducto(@RequestBody RequestWrapper datos){
        try {
            String email = datos.email;
            String contrasena = datos.contrasena;
            int sucursalID = datos.sucursalID;
            Producto producto = datos.producto;

            if(producto == null || servicioProducto.getProducto(producto.getProductoID()) == null){
                throw new NoSuchElementException();
            }

            producto.setFechaActualizacion(LocalDateTime.now());

            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            Actualizacion actualizacion = new Actualizacion(colaborador, producto, producto.getPrecio());
            Sucursal sucursal = servicioSucursal.getSucursal(sucursalID);
            producto.addSucursal(sucursal);
            sucursal.addProducto(producto);
                    
            colaborador.addPuntos(1);
            servicioColaborador.saveColaborador(colaborador);
            servicioProducto.colaboradorGuardaProducto(producto, colaborador);
            servicioActualizacion.saveActualizacion(actualizacion);
            
            return new ResponseEntity<Object>(producto, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Object>(datos.producto, HttpStatus.BAD_REQUEST);
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
    @RequestMapping(value = "/puntuar/{id}" , produces = "application/json", method = RequestMethod.POST)
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

        public RequestWrapperActualizar() {
        }

        public RequestWrapperActualizar(int precio, String email, String contrasena) {
            this.precio = precio;
            this.email = email;
            this.contrasena = contrasena;
        }

        public int getPrecio() {
            return precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
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
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Object> actualizarPrecio(@RequestBody RequestWrapperActualizar datos, @PathVariable Integer id){
        String email = datos.email;
        String contrasena = datos.contrasena;
        int precio = datos.precio;
        try {
            Producto producto = servicioProducto.getProducto(id);
            producto.setPrecio(precio);
            producto.setFechaActualizacion(LocalDateTime.now());

            Colaborador colaborador = servicioColaborador.buscarColaboradorPorEmail(email, contrasena);
            colaborador.addPuntos(1);
            Actualizacion actualizacion = new Actualizacion(colaborador, producto, precio);


            servicioProducto.saveProducto(producto);
            servicioColaborador.saveColaborador(colaborador);
            servicioActualizacion.saveActualizacion(actualizacion);

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
        }
    }
    
    @JsonView(Vista.Producto.class)
    @GetMapping("/buscarPorPrecio/{precio}")
    public ResponseEntity<List<Producto>> buscarPorPrecio(@PathVariable Integer precio){
        try{
            List<Producto> productos = servicioProducto.getProductoPorPrecio(precio);
            if(!productos.isEmpty()){
                return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);
            }else{
                throw new NoSuchElementException();
            }
        } catch (NoSuchElementException e) {
            return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
        }
    }


}
