package com.backendigans.Sistema_Control_De_Precios.repository;

import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioProducto extends JpaRepository<Producto, Integer>{
    List<Producto> findByNombre(String nombre);

}
