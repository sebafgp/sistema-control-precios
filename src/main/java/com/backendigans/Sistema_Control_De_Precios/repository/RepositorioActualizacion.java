package com.backendigans.Sistema_Control_De_Precios.repository;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Actualizacion;
import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;
import com.backendigans.Sistema_Control_De_Precios.model.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioActualizacion extends JpaRepository<Actualizacion, Integer>{
    List<Actualizacion> findByColaborador(Colaborador colaborador); 

    Optional<Actualizacion> findFirstByProductoOrderByFechaActualizacionDesc(Producto producto);

}
