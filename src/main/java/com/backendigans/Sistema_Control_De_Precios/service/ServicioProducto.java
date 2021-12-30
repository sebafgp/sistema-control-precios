package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;

public interface ServicioProducto {

    List<Producto> listAllProductos();

    Producto saveProducto(Producto producto);

    Producto getProducto(Integer productoID);

    void deleteProducto(Integer productoID);

    void colaboradorGuardaProducto(Producto producto, Colaborador colaborador);

    List<Producto> getByNombre(String nombre);

}