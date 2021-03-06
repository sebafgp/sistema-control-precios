package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioProducto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.*;

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
        if(producto == null) throw new IllegalArgumentException();
        return productoRepository.save(producto);
    }

    @Override
    public Producto getProducto(Integer productoID) {
        Producto producto = productoRepository.findById(productoID).orElse(null);
        if (producto == null) throw new NoSuchElementException();
        return producto;
    }

    @Override
    public void deleteProducto(Integer productoID) {
        productoRepository.deleteById(productoID);
    }

    @Override
    public void colaboradorGuardaProducto(Producto producto, Colaborador colaborador) {
        if(producto == null || colaborador == null) throw new IllegalArgumentException();
        producto.setColaborador(colaborador);
        saveProducto(producto);
    }

    @Override
    public List<Producto> getByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
    }

    @Override
    public List<Producto> getProductosDeInventarios(List<Inventario> inventarios) {
        List<Producto> productos = new ArrayList<>();
        for(Inventario inv : inventarios){
            productos.add(inv.getProducto());
        }
        return productos;
    }

}
