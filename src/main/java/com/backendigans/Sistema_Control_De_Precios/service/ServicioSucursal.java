package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioSucursal;

public interface ServicioSucursal {

    public List<Sucursal> listAllSucursals();

    public void saveSucursal(Sucursal sucursal);

    public Sucursal getSucursal(Integer sucursalID);

    public void deleteSucursal(Integer sucursalID);

}
