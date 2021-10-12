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
public class SorpresaPagarCobrar extends Sorpresa {

    /*
    Constructor con parámetros
    Crea una sorpresa para el resto de sorpresas
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo, valor y texto
     */
    SorpresaPagarCobrar( int valor, String texto) {
        super(texto, valor, null);
    }

     /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      El jugador paga o cobra el valor indicado en la carta
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(getValor());
        }
    }

    @Override
    public String toString() {
        return "SorpresaPagarCobrar{ texto="+ super.getTexto() + ", valor=" + getValor() + '}';
    }
    
}
