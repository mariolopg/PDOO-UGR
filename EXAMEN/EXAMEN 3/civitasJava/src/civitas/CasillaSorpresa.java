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
public class CasillaSorpresa extends Casilla  {

    private Sorpresa sorpresa;
    private MazoSorpresas mazo;
     
    /**
     * Constructor de casillas de tipo SORPRESA
     */
    CasillaSorpresa(MazoSorpresas mazo, String nombre) {
        super(nombre);
        this.mazo = mazo;
    }
    
    //Public para GUI
    public Sorpresa getSorpresa(){
        return sorpresa;
    }

     /**
   * Metodo recibeJugador_sorpresa
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla de tipo sorpresa recibe al jugador actual al que se le aplica el efecto de ésta 
   *    y se informa al diario.
   */
  @Override
   void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    if(jugadorCorrecto(iactual, todos)){
      informe(iactual, todos);
      sorpresa = mazo.siguiente();
      sorpresa.aplicarAJugador(iactual, todos);
    }
  }
   

    /**
     * Sobrecarga del metodo toString() de java
     */
    @Override
    public String toString() {
        return "CasillaCalle{ nombre = " +  super.getNombre() + " mazo = " + mazo.toString() + '}';
    }
}
