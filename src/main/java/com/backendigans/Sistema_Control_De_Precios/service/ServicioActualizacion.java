package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;

public interface ServicioActualizacion {

    public List<Actualizacion> listAllActualizacion();

    public List<Actualizacion> encontrarPorColaborador(Colaborador colaborador);

    public Optional<Actualizacion> encontrarUltimaPorProducto(Producto producto);

    public void saveActualizacion(Actualizacion actualizacion);
}
