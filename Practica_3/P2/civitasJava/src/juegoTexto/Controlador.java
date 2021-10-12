/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoTexto;

import civitas.CivitasJuego;
import civitas.GestionesInmobiliarias;
import civitas.OperacionInmobiliaria;
import civitas.OperacionesJuego;
import civitas.Respuestas;
import civitas.SalidasCarcel;

/** @author Marina Muñoz Cano y Mario López González */
public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;

    public Controlador (CivitasJuego juego, VistaTextual vista){
        this.juego = juego;
        this.vista = vista;
    }

    public void juega(){
        vista.setCivitasJuego(juego);
        while (!juego.finalDelJuego()){
            vista.actualizarVista();
            vista.pausa();
            OperacionesJuego operacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);
            if (operacion != OperacionesJuego.PASAR_TURNO)
                vista.mostrarEventos();
            
            if (!juego.finalDelJuego()){
                if (operacion ==  OperacionesJuego.COMPRAR){
                    if ( vista.comprar() == Respuestas.SI)
                        juego.comprar();
                         
                    juego.siguientePasoCompletado(operacion);
                }
                    

                if (operacion ==  OperacionesJuego.GESTIONAR){
                    vista.gestionar();
                    int iGestion = vista.getGestion();
                    int iPropiedad = vista.getPropiedad();
                    GestionesInmobiliarias gestionElegida = GestionesInmobiliarias.values()[iGestion];
                    OperacionInmobiliaria operacionInmob = new OperacionInmobiliaria ( gestionElegida, iPropiedad);
                    if (gestionElegida == GestionesInmobiliarias.VENDER)
                        juego.vender(iPropiedad);
                    else 
                        if (gestionElegida == GestionesInmobiliarias.HIPOTECAR)
                            juego.hipotecar(iPropiedad);
                        else 
                            if (gestionElegida == GestionesInmobiliarias.CANCELAR_HIPOTECA)
                                juego.cancelarHipoteca(iPropiedad);
                            else 
                                if (gestionElegida == GestionesInmobiliarias.CONSTRUIR_CASA)
                                    juego.construirCasa(iPropiedad);
                                else 
                                    if (gestionElegida == GestionesInmobiliarias.CONSTRUIR_HOTEL)
                                        juego.construirHotel(iPropiedad);
                                    else // Gestion == TERMINAR
                                        juego.siguientePasoCompletado(operacion);
                }

                if (operacion ==  OperacionesJuego.SALIR_CARCEL){
                    SalidasCarcel salir = vista.salirCarcel();
                    if (salir == SalidasCarcel.PAGANDO)
                        juego.salirCarcelPagando();
                    else  // salir == TIRANDO
                        juego.salirCarcelTirando();
                    juego.siguientePasoCompletado(operacion);
                }
                    
        }

        }
        
         System.out.println("***RANKING***");
         System.out.println(juego.ranking());
    }
}
