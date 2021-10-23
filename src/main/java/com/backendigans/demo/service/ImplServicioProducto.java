package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.backendigans.demo.model.Producto;
import com.backendigans.demo.repository.RepositorioProducto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioProducto implements ServicioProducto {

    @Autowired
    RepositorioProducto repProducto;

    @Override
    public List<Producto> getProductos() {
        return repProducto.findAll();
    }

    @Override
    public Producto getProducto(int id) {
        return repProducto.findById(id).get();
    }

    @Override
    public void guardarProducto(Producto producto) {
        repProducto.save(producto);
    }

    @Override
    public void borrarProducto(int id) {
        repProducto.deleteById(id);
    }
    
}
