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
public class SorpresaPorCasaHotel extends Sorpresa {
    

    /*
    Constructor con parámetros
    Crea una sorpresa para el resto de sorpresas
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo, valor y texto
     */
    SorpresaPorCasaHotel (int valor, String texto){
        super(texto, valor, null);
    }

     /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
       Modifica el saldo del jugador con el valor de la sorpresa multiplicado por el numero de casas y hoteles del jugador
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            int saldo = getValor() * todos.get(actual).cantidadCasasHoteles();
            todos.get(actual).modificarSaldo(saldo);
        }
    }

    @Override
    public String toString() {
        return "SorpresaPorCasaHotel{ texto="+ super.getTexto() +  '}';
    }
    
    
}
