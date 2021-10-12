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
public class SorpresaIrCasilla extends Sorpresa {

    /*
    Constructor con parámetros
    Crea una sorpresa de tipo IRCASILLA
    Parametros:
      Recibe como parámetros los valores de los atributos: tablero, valor y texto
     */
    SorpresaIrCasilla(Tablero tablero, int valor, String texto) {
        super(texto, valor, tablero);
    }
 
     /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
       Envia al jugador actual a la nueva casilla
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasillaActual();
            int tirada = getTablero().calcularTirada(casillaActual, getValor());
            int nuevaPos = getTablero().nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPos);
            getTablero().getCasilla(nuevaPos).recibeJugador(actual, todos);
        }
    }

    @Override
    public String toString() {
        return "SorpresaIrCasilla{ texto="+ super.getTexto() + '}';
    }
    
    
}
