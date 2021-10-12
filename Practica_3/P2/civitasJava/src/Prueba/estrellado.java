/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Prueba;
import civitas.*;
import java.util.ArrayList;
import juegoTexto.Controlador;
import juegoTexto.VistaTextual;
/**
 *
 * @author mariolopezgonzalez
 */
public class estrellado {
    
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
