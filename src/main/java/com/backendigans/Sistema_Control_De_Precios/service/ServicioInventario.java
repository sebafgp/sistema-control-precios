package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;

import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;

public interface ServicioInventario {
    Inventario buscarInventarioPorProductoYSucursal(Producto producto, Sucursal sucursal);

    Inventario buscarInventarioPorId(int inventarioID);

    void saveInventario(Inventario inventario);

    List<Inventario> getInventariosPorPrecio(Integer precio);
}
