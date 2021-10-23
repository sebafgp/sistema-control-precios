package com.backendigans.demo.service;

import java.util.List;
import com.backendigans.demo.model.Cadena;

public interface ServicioCadena {

    public List<Cadena> getCadenas();

    public Cadena getCadena(int id);

    public void guardarCadena(Cadena cadena);

    public void borrarCadena(int id);
}
