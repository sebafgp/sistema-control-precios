package com.backendigans.demo.service;

import java.util.List;

import com.backendigans.demo.model.Sucursal;

public interface ServicioSucursal {
    public List<Sucursal> getSucursales();

    public Sucursal getSucursal(int id);

    public void guardarSucursal(Sucursal sucursal);

    public void borrarSucursal(int id);
}
