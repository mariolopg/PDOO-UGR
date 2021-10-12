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
public class SorpresaIrCarcel extends Sorpresa {

    /*
    Constructor con parámetros
    Crea una sorpresa de tipo IRCARCEL
    Parametros:
      Recibe como parámetros los valores de los atributos: tablero
     */
    SorpresaIrCarcel(Tablero tablero) {
        super("Vas a la carcel. Muévete a la casilla Cárcel", -1, tablero);
    }

    /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
       Envia al jugador actual a la cárcel
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(getTablero().getCarcel());
        }
       
    }

    @Override
    public String toString() {
        return "SorpresaIrCarcel{ "+ super.getTexto() + ", tablero=" + getTablero() + '}';
    }
    
    
}
