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
public class SorpresaPorJugador extends Sorpresa {
    
    /*
    Constructor con parámetros
    Crea una sorpresa para el resto de sorpresas
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo, valor y texto
     */
    SorpresaPorJugador( int valor, String texto) {
        super(texto, valor, null);
    }

    /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Aplica al jugador la sorpresa 
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            SorpresaPagarCobrar pagar = new SorpresaPagarCobrar(getValor() * (-1), "Todos pagan al jugador " + todos.get(actual).getNombre());
            SorpresaPagarCobrar cobrar = new SorpresaPagarCobrar(getValor() * (todos.size() - 1), "Jugador " + todos.get(actual).getNombre() + " cobra " + getValor() + " del resto de jugadores");

            cobrar.aplicarAJugador(actual, todos);
            for (int i = 0; i < todos.size(); i++)
                if (i != actual)
                    pagar.aplicarAJugador(i, todos);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPorJugador{ texto="+ super.getTexto() + ", valor=" + getValor() + '}';
    }
    
    
    
}
