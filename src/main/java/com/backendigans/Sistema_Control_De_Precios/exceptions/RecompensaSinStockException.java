package com.backendigans.Sistema_Control_De_Precios.exceptions;

public class RecompensaSinStockException extends Exception{
    public RecompensaSinStockException(){
        super("No hay stock de recompensa para esta transaccion");
    }
    public RecompensaSinStockException(String msj){
        super(msj);
    }
}
