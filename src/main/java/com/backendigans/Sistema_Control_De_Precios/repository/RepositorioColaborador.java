package com.backendigans.Sistema_Control_De_Precios.repository;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import com.backendigans.Sistema_Control_De_Precios.model.Colaborador;

public interface RepositorioColaborador extends JpaRepository<Colaborador, Integer> {
    Optional<Colaborador> findFirstByEmailAndContrasena(String email, String contrasena);  
    
    List<Colaborador> findByOrderByReputacionDesc(Sort sort);

    Colaborador findFirstByNickname(String nickname);

}  
