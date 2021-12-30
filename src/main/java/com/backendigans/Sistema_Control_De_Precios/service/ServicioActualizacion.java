package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;

public interface ServicioActualizacion {

    List<Actualizacion> listAllActualizacion();

    List<Actualizacion> encontrarPorColaborador(Colaborador colaborador);

    void saveActualizacion(Actualizacion actualizacion);
}
