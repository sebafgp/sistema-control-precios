package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;

public interface ServicioSucursal {

    List<Sucursal> listAllSucursals();

    void saveSucursal(Sucursal sucursal);

    Sucursal getSucursal(Integer sucursalID);

    void deleteSucursal(Integer sucursalID);

}
