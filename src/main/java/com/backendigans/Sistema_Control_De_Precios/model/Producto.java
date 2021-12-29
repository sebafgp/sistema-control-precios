package com.backendigans.Sistema_Control_De_Precios.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


@Entity
@Table(name = "producto")
@JsonIgnoreProperties(ignoreUnknown = true,
		value = {"productos", "recompensas", "actualizaciones"})
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
	@Column(name = "unidad")
	private String unidad;
	@Column(name = "fechaActualizacion")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime fechaActualizacion;

	@ManyToOne
	@JoinColumn(name = "colaboradorID" , referencedColumnName = "colaboradorID")
	private Colaborador colaborador;

	@OneToMany (mappedBy = "producto")
	private Inventario inventario;

	public Producto() {
	}

	public Producto(int productoID, String nombre, String marca, int cantidad, String unidad, LocalDateTime fechaActualizacion) {
		this.productoID = productoID;
		this.nombre = nombre;
		this.marca = marca;
		this.cantidad = cantidad;
		this.unidad = unidad;
		this.fechaActualizacion = fechaActualizacion;
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
	public String getUnidad() {
		return unidad;
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
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

}
