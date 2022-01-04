package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.*;

import javax.transaction.Transactional;

import com.backendigans.Sistema_Control_De_Precios.model.*;
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

    @Override
    public List<Sucursal> getTopSucursalesPorInventarios(List<Inventario> inventarios) {
        SortedSet<Actualizacion> ultimasActualizaciones = new TreeSet<>(Comparator.comparingInt(Actualizacion::getPrecio));
        for(Inventario inventario : inventarios){
            try{
                ultimasActualizaciones.add(encontrarUltimaPorInventario(inventario));
            } catch (Exception e){
                ultimasActualizaciones.add(new Actualizacion(inventario.getProducto().getColaborador(), inventario, inventario.getPrecio()));
            }
        }

        int x = Math.min(ultimasActualizaciones.size(), 3);

        List<Actualizacion> listaActualizaciones = new ArrayList<>(ultimasActualizaciones);
        List<Sucursal> sucursales = new ArrayList<>();

        for (int i = 0; i < x; i++){
            sucursales.add(listaActualizaciones.get(i).getInventario().getSucursal());
        }

        return sucursales;
    }

}
