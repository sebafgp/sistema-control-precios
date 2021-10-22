package com.backendigans.demo.repository;

import com.backendigans.demo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioProducto extends JpaRepository<Producto, Integer>{
    
}
