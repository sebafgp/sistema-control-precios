package com.backendigans.Sistema_Control_De_Precios.repository;

import com.backendigans.Sistema_Control_De_Precios.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioInventario extends JpaRepository<Inventario, Integer>{

}
