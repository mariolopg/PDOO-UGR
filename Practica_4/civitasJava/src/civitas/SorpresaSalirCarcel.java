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
public class SorpresaSalirCarcel extends Sorpresa {
    private MazoSorpresas mazo;

    /*
    Constructor con parámetros
    Crea una sorpresa de tipo SALIRCARCEL
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo y tablero
     */
    SorpresaSalirCarcel( MazoSorpresas mazo) {
        super ("Te libras de la cárcel. Si caes en la cárcel y tienes esta carta puedes salir de ella.", -1, null);
        this.mazo = mazo;
    }

    /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Si nadie tiene la carta para salir de la carcel, la obtiene el jugador actual
     */
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Boolean cartaDisponible = true;

            for (int i = 0; i < todos.size() && cartaDisponible; i++) {
                if (todos.get(i).tieneSalvoconducto()) {
                    cartaDisponible = false;
                } 
            }

            if (cartaDisponible) {
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }

    /*
    Método salirMazo
    Efecto:
      Si la sorpresa es la que inhabilita la cárcel, inhabilita la carta en el mazo
     */
    void salirDelMazo() {
        mazo.inhabilitarCartaEspecial(this);
    }

    
    /*
    Método usada()
    Efecto:
      Si la sorpresa es la que inhabilita la cárcel, habilita la carta en el mazo
     */
    void usada() {
        mazo.habilitarCartaEspecial(this);
    }

    @Override
    public String toString() {
        return "SorpresaSalirCarcel{ texto="+ super.getTexto() + ", mazo=" + mazo + '}';
    }
    
    
    
}
