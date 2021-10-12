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
public class CasillaCalle extends Casilla {
    private TituloPropiedad tituloPropiedad;
    private float importe;
 
    /**
     * Constructor de casillas de tipo CALLE
     */
    CasillaCalle(TituloPropiedad titulo) {
        super(titulo.getNombre());
        tituloPropiedad = titulo;
        importe = titulo.getPrecioCompra();
    }

    /**
   * Metodo recibeJugador_calle
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla de tipo calle recibe al jugador actual al que se le aplica el efecto de ésta 
   *    y se informa al diario.
   */
  @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos) {
        if(jugadorCorrecto(iactual, todos)){
            informe(iactual, todos);
            Jugador jugador = todos.get(iactual);
            if (!tituloPropiedad.tienePropietario())
                jugador.puedeComprarCasilla();
            else
                tituloPropiedad.tramitarAlquiler(jugador);
        }
    }

    /**
     * Consultor del atributo tituloPropiedad
     */
    TituloPropiedad getTituloPropiedad() {
        return tituloPropiedad;
    }

    /**
     * Sobrecarga del metodo toString() de java
     */
    @Override
    public String toString() {
        return "CasillaCalle{ nombre = " +  super.getNombre() + ", tituloPropiedad = " + tituloPropiedad + ", importe = " + importe + '}';
    }

    

}
