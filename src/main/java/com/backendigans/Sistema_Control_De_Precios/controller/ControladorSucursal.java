
package com.backendigans.Sistema_Control_De_Precios.controller;

import com.backendigans.Sistema_Control_De_Precios.model.*;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioCadena;
import com.backendigans.Sistema_Control_De_Precios.service.ServicioSucursal;
import com.fasterxml.jackson.annotation.JsonView;
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

    @Autowired
    ServicioColaborador servicioColaborador;

    @Autowired
    ServicioCadena servicioCadena;

    @JsonView(Vista.Sucursal.class)
    @GetMapping("")
    public List<Sucursal> list() {
        return servicioSucursal.listAllSucursals();
    }

    @JsonView(Vista.Sucursal.class)
    @GetMapping("/{id}")
    public ResponseEntity<Sucursal> get(@PathVariable Integer id) {
        try {
            Sucursal sucursal = servicioSucursal.getSucursal(id);
            return new ResponseEntity<Sucursal>(sucursal, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Sucursal>(HttpStatus.NOT_FOUND);
        }
    }

    public static class RequestWrapperAgregarSucursal{
        private String email, contrasena, ciudad, calle;
        private int numero, cadenaID;

        public RequestWrapperAgregarSucursal() {
        }

        public RequestWrapperAgregarSucursal(String email, String contrasena, String ciudad, String calle, int numero, int cadenaID) {
            this.email = email;
            this.contrasena = contrasena;
            this.ciudad = ciudad;
            this.calle = calle;
            this.numero = numero;
            this.cadenaID = cadenaID;
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

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

        public int getCadenaID() {
            return cadenaID;
        }

        public void setCadenaID(int cadenaID) {
            this.cadenaID = cadenaID;
        }
    }

    @PostMapping("/")
    @RequestMapping(produces = "application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody RequestWrapperAgregarSucursal datos) {
        try{
            servicioColaborador.buscarColaboradorPorEmail(datos.getEmail(), datos.getContrasena());
            Cadena cadena = servicioCadena.getCadena(datos.getCadenaID());
            Sucursal sucursal = new Sucursal(datos.getCiudad(), datos.getCalle(), datos.getNumero(), cadena);
            cadena.addSucursal(sucursal);
            servicioCadena.saveCadena(cadena);
            servicioSucursal.saveSucursal(sucursal);
            return new ResponseEntity<>(sucursal, HttpStatus.CREATED);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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