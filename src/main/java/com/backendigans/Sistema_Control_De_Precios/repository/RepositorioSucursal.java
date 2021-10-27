package com.backendigans.Sistema_Control_De_Precios.repository;

import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioSucursal extends JpaRepository<Sucursal, Integer>{
    
}
