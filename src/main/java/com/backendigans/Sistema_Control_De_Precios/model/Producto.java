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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name = "producto")
@JsonIgnoreProperties(ignoreUnknown = true, 
                      value = {"colaboradores", "sucursales", "actualizaciones"})
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
	@Column(name = "fechaActualizacion")
	private LocalDateTime fechaActualizacion;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "producto_colaborador",
		joinColumns = {@JoinColumn(name = "productoID")},
		inverseJoinColumns = {@JoinColumn(name = "colaboradorID")}
	)
	private Set<Colaborador> colaboradores = new HashSet<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productos")
	private Set<Sucursal> sucursales = new HashSet<>();


    @OneToMany(mappedBy = "producto")
    private Set<Actualizacion> actualizaciones = new HashSet<>();
	
	public Producto() {
	}

	public Producto(int productoID, String nombre, String marca, int cantidad, int precio , LocalDateTime fechaActualizacion) {
		this.productoID = productoID;
		this.nombre = nombre;
		this.marca = marca;
		this.cantidad = cantidad;
		this.precio = precio;
		this.fechaActualizacion = fechaActualizacion;
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

    public LocalDateTime getFechaActualizacion(){
        return fechaActualizacion;
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
	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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

	public Set<Actualizacion> getActualizaciones() {
		return actualizaciones;
	}
	public void setActualizaciones(Set<Actualizacion> actualizaciones) {
		this.actualizaciones = actualizaciones;
	}

}
