package com.backendigans.demo.service;

import java.util.List;
import com.backendigans.demo.model.Producto;

public interface ServicioProducto {
    public List<Producto> getProductos();

    public Producto getProducto(int id);

    public void guardarProducto(Producto producto);

    public void borrarProducto(int id);
}
