package com.backendigans.demo.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @Id
    @Column(name = "sucursalID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sucursalID;
    @Column(name = "cadenaID")
    private int cadenaID;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numero")
    private int numero;

    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn(name = "cadenaID")
    private Cadena cadena;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "sucursal_producto",
        joinColumns = {@JoinColumn(name = "sucursalID")},
        inverseJoinColumns = {@JoinColumn(name = "productoID")}
    )
    private Set<Producto> productos = new HashSet<>();

    public Sucursal(){

    }

    public Sucursal(int sucursalID, int cadenaID, String ciudad, String calle, int numero){
        this.sucursalID = sucursalID;
        this.cadenaID = cadenaID;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }
 
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

    public Set<Producto> getProductos() {
        return this.productos;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }

}
