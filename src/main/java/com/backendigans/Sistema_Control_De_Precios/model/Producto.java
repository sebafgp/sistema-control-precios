package com.backendigans.Sistema_Control_De_Precios.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "producto")
public class Producto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productoID;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "marca")
	private String marca;
	@Column(name = "cantidad")
	private int cantidad;
	@Column(name = "precio")
	private int precio;
	@Column(name = "fecha_actualizacion")
	private LocalDate fecha_actualizacion;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "producto_colaborador",
		joinColumns = {@JoinColumn(name = "productoID")},
		inverseJoinColumns = {@JoinColumn(name = "colaboradorID")}
	)
	private Set<Colaborador> colaboradores = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productos")
	private Set<Sucursal> sucursales = new HashSet<>();


    @OneToMany(mappedBy = "producto")
    private Set<Actualizacion> actualizacion = new HashSet<>();
	
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

	public Set<Sucursal> getSucursales() {
		return this.sucursales;
	}

	public void setSucursales(Set<Sucursal> sucursales) {
		this.sucursales = sucursales;
	}

	public Set<Colaborador> getColaboradores() {
		return this.colaboradores;
	}

	public void setColaboradores(Set<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
	}

    public void addColaborador(Colaborador colaborador){
        this.colaboradores.add(colaborador);
    }

	public Set<Actualizacion> getActualizacion() {
		return actualizacion;
	}
	public void setActualizacion(Set<Actualizacion> actualizacion) {
		this.actualizacion = actualizacion;
	}

}