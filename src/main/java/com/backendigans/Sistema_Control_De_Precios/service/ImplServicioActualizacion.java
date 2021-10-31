package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioActualizacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ImplServicioActualizacion implements ServicioActualizacion{
    @Autowired
    private RepositorioActualizacion actualizacionRepository;

    @Override
    public List<Actualizacion> listAllActualizacion() {
        return actualizacionRepository.findAll();
    }

    @Override
    public List<Actualizacion> encontrarPorColaborador(Colaborador colaborador) {
        return actualizacionRepository.findByColaborador(colaborador);
    }

    @Override
    public Optional<Actualizacion> encontrarUltimaPorProducto(Producto producto) {
        return actualizacionRepository.findFirstByProductoOrderByFechaActualizacionDesc(producto);
    }

    @Override
    public void saveActualizacion(Actualizacion actualizacion) {
        actualizacionRepository.save(actualizacion);
    }

}