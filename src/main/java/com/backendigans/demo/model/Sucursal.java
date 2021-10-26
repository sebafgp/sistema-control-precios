package com.backendigans.demo.model;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sucursal")
public class Sucursal {
    private int sucursalID;
    private int cadenaID;
    private String ciudad;
    private String calle;
    private int numero;

    @ManyToOne
    @JoinColumn(name="cadenaID")
    private Cadena cadena;

    @ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
        name = "sucursal_producto",
        joinColumns = {@JoinColumn(name = "sucursalID")},
        inverseJoinColumns = {@JoinColumn(name = "productoID")}
    )
    private List<Producto> productos;

    public Sucursal(){

    }

    public Sucursal(int sucursalID, int cadenaID, String ciudad, String calle, int numero){
        this.sucursalID = sucursalID;
        this.cadenaID = cadenaID;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    public int getSucursalID() {
        return this.sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
    }

    public int getCadenaID() {
        return this.cadenaID;
    }

    public void setCadenaID(int cadenaID) {
        this.cadenaID = cadenaID;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return this.calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cadena getCadena() {
        return this.cadena;
    }

    public void setCadena(Cadena cadena) {
        this.cadena = cadena;
    }

    public List<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

}
