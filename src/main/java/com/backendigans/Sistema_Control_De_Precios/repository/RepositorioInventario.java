package com.backendigans.Sistema_Control_De_Precios.repository;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RepositorioInventario extends JpaRepository<Inventario, Integer>{

    Optional<Inventario> findBySucursalAndProducto(Sucursal sucursal, Producto producto);

    List<Inventario> findByPrecioLessThanEqual(int precio);

    List<Inventario> findByProducto(Producto producto);



}
