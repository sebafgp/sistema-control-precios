package com.backendigans.demo.model;

package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Table(name = "producto")
public class Producto {
	private int productoID;
	private String nombre;
	private String marca;
	private int cantidad;
	private int precio;
	private LocalDate fecha_actualizacion;
	
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
//other setters and getters
    
}
