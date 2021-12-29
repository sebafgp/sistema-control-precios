package com.backendigans.Sistema_Control_De_Precios.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int inventarioID;
    @Column(name = "precio")
    private int precio;

    @ManyToOne
    @JoinColumn(name = "sucursalID" , referencedColumnName = "sucursalID")
    private Sucursal sucursal;

    @ManyToOne
    @JoinColumn(name = "productoID" , referencedColumnName = "productoID")
    private Producto producto;

    @OneToMany (mappedBy = "inventario")
    private Set<Actualizacion> actualizaciones = new HashSet<>();

    public Inventario(){}

    public Inventario(int precio, Sucursal sucursal, Producto producto) {
        this.precio = precio;
        this.sucursal = sucursal;
        this.producto = producto;
    }

    public int getInventarioID() {
        return inventarioID;
    }

    public int getPrecio() {
        return precio;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public Producto getProducto() {
        return producto;
    }

    public Set<Actualizacion> getActualizaciones() {
        return actualizaciones;
    }

    public boolean addActualizacion(Actualizacion actualizacion){
        return actualizaciones.add(actualizacion);
    }
}
