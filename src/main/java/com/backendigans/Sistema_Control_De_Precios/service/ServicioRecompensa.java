package com.backendigans.Sistema_Control_De_Precios.service;

import java.util.List;
import com.backendigans.Sistema_Control_De_Precios.model.Recompensa;

public interface ServicioRecompensa {
	
    public List<Recompensa> listAllRecompensas();

	public void saveRecompensa(Recompensa recompensa);

    public Recompensa getRecompensa(Integer recompensaID);

    public void deleteRecompensa(Integer recompensaID);
}