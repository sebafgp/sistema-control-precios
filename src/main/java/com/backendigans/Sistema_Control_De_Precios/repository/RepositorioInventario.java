package com.backendigans.Sistema_Control_De_Precios.repository;

import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioInventario extends JpaRepository<Inventario, Integer>{
    Optional<Inventario> findBySucursal_SucursalIDAndProducto_ProductoID(int sucursalID, int productoID);

    List<Inventario> findByPrecioLessThanEqual(int precio);



}
