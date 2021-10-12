/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.CivitasJuego;
import civitas.Dado;
import civitas.Dado;
import civitas.Diario;
import civitas.Diario;
import java.util.ArrayList;
import juegoTexto.Controlador;
import juegoTexto.VistaTextual;


/**
 * @author Marina Muñoz Cano y Mario López González
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VistaTextual vista = new VistaTextual();
        Dado.getInstance().setDebug(true, Diario.getInstance());
        ArrayList<String> nombres = new ArrayList<String>();
        nombres.add("Marina");
        nombres.add("Mario");
        CivitasJuego juego = new CivitasJuego (nombres);
        Controlador controlador = new Controlador (juego, vista);
        controlador.juega();
    }
    
}
