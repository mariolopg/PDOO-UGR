/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
package civitas;

import java.util.ArrayList;
 
/**
 *
 * @author mariolopezgonzalez
 */
public class CasillaJuez extends Casilla{
     
    private static int carcel = 1;

    /**
     * Constructor de casillas de tipo JUEZ
     */
    CasillaJuez(int numCasillaCarcel, String nombre) {
        super(nombre);
        carcel = numCasillaCarcel;
    }

    
    /**
     * Metodo recibeJugador_juez
     *  Parámetros:
     *    iactual = indice del jugador actual
     *    todos = Lista con todos los jugadores
     *  Efecto:
     *    La casilla de tipo juez recibe al jugador actual al que se le aplica el efecto de ésta 
     *    y se informa al diario.
     */
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            todos.get(iactual).encarcelar(carcel);
        }
    }

    /**
     * Sobrecarga del metodo toString() de java
     */
    @Override
    public String toString() {
        return "Casilla:  nombre = " + super.getNombre() + " numero de la Casilla Carcel = " + carcel;
    }
}
