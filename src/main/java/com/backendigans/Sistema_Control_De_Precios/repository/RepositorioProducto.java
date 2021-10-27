package com.backendigans.Sistema_Control_De_Precios.repository;

import com.backendigans.Sistema_Control_De_Precios.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProducto extends JpaRepository<Producto, Integer>{
    
}
