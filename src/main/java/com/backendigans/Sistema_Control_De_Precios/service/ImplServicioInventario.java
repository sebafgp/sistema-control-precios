package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioInventario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioInventario implements ServicioInventario{
    @Autowired
    private RepositorioInventario inventarioRepository;

    @Override
    public Inventario buscarInventarioPorProductoYSucursal(Producto producto, Sucursal sucursal){
        return inventarioRepository.findBySucursalAndProducto(sucursal, producto).get();
    }

    @Override
    public Inventario buscarInventarioPorId(int inventarioID) {
        return inventarioRepository.findById(inventarioID).get();
    }

    @Override
    public void saveInventario(Inventario inventario) {
        inventarioRepository.save(inventario);
    }

    @Override
    public List<Inventario> getInventariosPorPrecio(Integer precio) {
        return inventarioRepository.findByPrecioLessThanEqual(precio);
    }

}