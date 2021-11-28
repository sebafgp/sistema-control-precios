package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Service
@Transactional
public class ImplServicioProducto implements ServicioProducto {
    @Autowired
    private RepositorioProducto productoRepository;
    
    @Override
    public List<Producto> listAllProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto saveProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Producto getProducto(Integer productoID) {
        return productoRepository.findById(productoID).get();
    }

    @Override
    public void deleteProducto(Integer productoID) {
        productoRepository.deleteById(productoID);
    }

    @Override
    public void colaboradorGuardaProducto(Producto producto, Colaborador colaborador) {
        if(producto == null || colaborador == null)
            throw new IllegalArgumentException();
        producto.addColaborador(colaborador);
        saveProducto(producto);
    }

    @Override
    public List<Producto> getByNombre(String nombre) {
        // TODO Auto-generated method stub
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> getProductoPorPrecio(Integer precio){
        List<Producto> productos;
        productos = productoRepository.findByPrecioLessThanEqual(precio);
        return productos;
    }
}
