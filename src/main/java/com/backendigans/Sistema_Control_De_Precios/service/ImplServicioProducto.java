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
    public void saveProducto(Producto producto) {
        productoRepository.save(producto);
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
        Set <Colaborador> colab = new HashSet<>();
        colab.add(colaborador);
        producto.setColaboradores(colab);

        Set <Producto> prod = new HashSet<>();
        prod.add(producto);
        colaborador.setProductos(prod);
    }
}