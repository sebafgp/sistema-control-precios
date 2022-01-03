package com.backendigans.Sistema_Control_De_Precios.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "sucursal")
@JsonIgnoreProperties(ignoreUnknown = true,
        value = {"productos", "cadena"})
public class Sucursal {
    @Id
    @Column(name = "sucursalID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sucursalID;
    @Column(name = "ciudad")
    private String ciudad;
    @Column(name = "calle")
    private String calle;
    @Column(name = "numero")
    private int numero;

    @JsonView(Vista.Sucursal.class)
    @ManyToOne(/*optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER*/)
    @JoinColumn(name = "cadenaID")
    private Cadena cadena;

    @OneToMany(mappedBy = "sucursal")
    private Set<Inventario> inventarios = new HashSet<>();

    public Sucursal(){

    }

    public Sucursal(int sucursalID, int cadenaID, String ciudad, String calle, int numero){
        this.sucursalID = sucursalID;
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
    }

    public Sucursal(String ciudad, String calle, int numero, Cadena cadena) {
        this.ciudad = ciudad;
        this.calle = calle;
        this.numero = numero;
        this.cadena = cadena;
    }

    public int getSucursalID() {
        return this.sucursalID;
    }

    public void setSucursalID(int sucursalID) {
        this.sucursalID = sucursalID;
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

    public boolean addInventario(Inventario inventario){
        return inventarios.add(inventario);
    }

}
