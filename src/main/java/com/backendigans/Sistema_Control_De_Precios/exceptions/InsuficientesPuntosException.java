package com.backendigans.Sistema_Control_De_Precios.exceptions;

public class InsuficientesPuntosException extends Exception{
    public InsuficientesPuntosException(){
        super("No hay suficientes puntos para esta transaccion");
    }
    public InsuficientesPuntosException(String msj){
        super(msj);
    }
}
