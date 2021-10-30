package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;

public interface ServicioActualizacion {

    public List<Actualizacion> listAllActualizacion();

    List<Actualizacion> encontrarPorColaborador(Colaborador colaborador); 
}