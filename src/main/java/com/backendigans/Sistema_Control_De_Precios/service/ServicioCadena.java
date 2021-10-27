package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import com.backendigans.Sistema_Control_De_Precios.model.Cadena;

public interface ServicioCadena {


    public List<Cadena> listAllCadenas();

    public void saveCadena(Cadena cadena);

    public Cadena getCadena(Integer cadenaID);

    public void deleteCadena(Integer cadenaID);
}
