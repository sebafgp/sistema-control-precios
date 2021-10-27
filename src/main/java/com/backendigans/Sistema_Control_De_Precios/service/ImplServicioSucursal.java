package com.backendigans.Sistema_Control_De_Precios.service;

import com.backendigans.Sistema_Control_De_Precios.model.Sucursal;
import com.backendigans.Sistema_Control_De_Precios.repository.RepositorioSucursal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
public class ImplServicioSucursal implements ServicioSucursal {
    @Autowired
    private RepositorioSucursal sucursalRepository;
    @Override
    public List<Sucursal> listAllSucursals() {
        return sucursalRepository.findAll();
    }

    @Override
    public void saveSucursal(Sucursal sucursal) {
        sucursalRepository.save(sucursal);
    }

    @Override
    public Sucursal getSucursal(Integer sucursalID) {
        return sucursalRepository.findById(sucursalID).get();
    }

    @Override
    public void deleteSucursal(Integer sucursalID) {
        sucursalRepository.deleteById(sucursalID);
    }
}