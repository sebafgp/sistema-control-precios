package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;

public interface ServicioProducto {

    public List<Producto> listAllProductos();

    public void saveProducto(Producto producto);

    public Producto getProducto(Integer productoID);

    public void deleteProducto(Integer productoID);

    public void colaboradorGuardaProducto(Producto producto, Colaborador colaborador);

    public List<Producto> getByNombre(String nombre);

    public List<Producto> getProductoPorPrecio(Integer precio);
}