package com.backendigans.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
	private int productoID;
	private String nombre;
	private String marca;
	private int cantidad;
	private int precio;
	private LocalDate fecha_actualizacion;

	@ManyToMany (mappedBy = "productos")
	private List <Sucursal> sucursales;

	@ManyToMany (cascade = {CascadeType.ALL})
    @JoinTable(
        name = "producto_colaborador",
        joinColumns = {@JoinColumn(name = "productoID")},
        inverseJoinColumns = {@JoinColumn(name = "colaboradorID")}
    )
    private List<Colaborador> colaboradores;
	
	public Producto() {
	}

	public Producto(int productoID, String nombre, String marca, int cantidad, int precio , LocalDate fecha_actualizacion) {
		this.productoID = productoID;
		this.nombre = nombre;
		this.marca = marca;
		this.cantidad = cantidad;
		this.precio = precio;
		this.fecha_actualizacion = fecha_actualizacion;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return productoID;
	}

    public String getNombre(){
        return nombre;
    }

    public String getMarca(){
        return marca;
    }    

    public int getCantidad(){
        return cantidad;
    }

    public int getPrecio(){
        return precio;
    }

    public LocalDate getFecha_actualizacion(){
        return fecha_actualizacion;
    }

	public int getProductoID() {
		return this.productoID;
	}

	public void setProductoID(int productoID) {
		this.productoID = productoID;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public void setPrecio(int precio) {
		this.precio = precio;
	}
	public void setFecha_actualizacion(LocalDate fecha_actualizacion) {
		this.fecha_actualizacion = fecha_actualizacion;
	}

	public List<Sucursal> getSucursales() {
		return this.sucursales;
	}

	public void setSucursales(List<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public List<Colaborador> getColaboradores() {
		return this.colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

}
