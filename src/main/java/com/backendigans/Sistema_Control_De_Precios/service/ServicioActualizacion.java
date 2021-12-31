package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Inventario;

public interface ServicioActualizacion {

    List<Actualizacion> listAllActualizacion();

    List<Actualizacion> encontrarPorColaborador(Colaborador colaborador);

    void saveActualizacion(Actualizacion actualizacion);

    Actualizacion encontrarUltimaPorInventario(Inventario inventario);

    List<Actualizacion> listarTodasLasActualizacionesDeInventario(Inventario inventario);

    Actualizacion encontrarPorId(int actualizacionId);

    boolean agregarComentario(String comentario, Actualizacion actualizacion);
}
