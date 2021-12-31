package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
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
    public void saveActualizacion(Actualizacion actualizacion) {
        if(actualizacion == null) throw new IllegalArgumentException();
        actualizacionRepository.save(actualizacion);
    }

    @Override
    public Actualizacion encontrarUltimaPorInventario(Inventario inventario) {
        return actualizacionRepository.findFirstByInventario_InventarioIDOrderByFechaActualizacionDesc(inventario.getInventarioID()).get();
    }

    @Override
    public List<Actualizacion> listarTodasLasActualizacionesDeInventario(Inventario inventario) {
        return actualizacionRepository.findByInventario(inventario);
    }

    @Override
    public Actualizacion encontrarPorId(int actualizacionId) {
        return actualizacionRepository.findByActualizacionID(actualizacionId).get();
    }

    @Override
    public boolean agregarComentario(String comentario, Actualizacion actualizacion) {
        return actualizacion.agregarComentario(comentario);
    }

}
