package com.backendigans.Sistema_Control_De_Precios.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "actualizacion")
public class Actualizacion {


    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int actualizacionID;

    @JsonView(Vista.Actualizacion.class)
    @ManyToOne
    @JoinColumn(name = "colaboradorID", referencedColumnName = "colaboradorID")
    private Colaborador colaborador;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "inventarioID", referencedColumnName = "inventarioID")
    private Inventario inventario;

    private int precio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime fechaActualizacion;


    @Value("${some.key:0}")
    private int valoracion;



    public Actualizacion(){

    }

    public Actualizacion (int actualizacionID, Colaborador colaborador, Inventario inventario, int precio, LocalDateTime fechaActualizacion, int valoracion){

        this.actualizacionID = actualizacionID;
        this.colaborador = colaborador;
        this.inventario = inventario;
        this.precio = precio;
        this.fechaActualizacion = fechaActualizacion;
        this.valoracion = valoracion;
    }

    public Actualizacion (Colaborador colaborador, Inventario inventario, int precio){

        this.actualizacionID = 0;
        this.colaborador = colaborador;
        this.inventario = inventario;
        this.precio = precio;
        this.fechaActualizacion = LocalDateTime.now();
        this.valoracion = 0;
    }


    public Colaborador getColaborador() {
        return colaborador;
    }
    public Inventario getInventario() {
        return inventario;
    }
    public int getPrecio() {
        return precio;
    }
    public int getValoracion() {
        return valoracion;
    }
    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }
    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }
    public void setPrecio(int precio) {
        this.precio = precio;
    }
    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

	public void addPuntos(int i) {
        this.valoracion = this.valoracion + i;
	}

}
