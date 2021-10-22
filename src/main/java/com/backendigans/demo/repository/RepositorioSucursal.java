package com.backendigans.demo.repository;

import com.backendigans.demo.model.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioSucursal extends JpaRepository<Sucursal, Integer>{
    
}
