package com.backendigans.demo.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.backendigans.demo.model.Sucursal;
import com.backendigans.demo.repository.RepositorioSucursal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ImplServicioSucursal implements ServicioSucursal {

    @Autowired
    RepositorioSucursal repSucursal;

    @Override
    public List<Sucursal> getSucursales() {
        return repSucursal.findAll();
    }

    @Override
    public Sucursal getSucursal(int id) {
        return repSucursal.findById(id).get();
    }

    @Override
    public void guardarSucursal(Sucursal sucursal) {
        repSucursal.save(sucursal);
    }

    @Override
    public void borrarSucursal(int id) {
        repSucursal.deleteById(id);
    }
}