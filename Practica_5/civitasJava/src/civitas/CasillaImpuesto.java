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
public class CasillaImpuesto extends Casilla  {
     
    private float importe;

    /**
     * Constructor de casillas de tipo IMPUESTO
     */
    CasillaImpuesto(float cantidad, String nombre){
        super(nombre);
        importe = cantidad;
    }

     /**
   * Metodo recibeJugador_impuesto
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla de tipo impuesto recibe al jugador actual al que se le aplica el efecto de ésta 
   *    y se informa al diario.
   */
  @Override
  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)){
        informe(iactual, todos);
        todos.get(iactual).pagaImpuesto(importe);
        Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " paga un impuesto de " + importe + "€. ");
    }
  }

    /**
     * Sobrecarga del metodo toString() de java
     */
    @Override
    public String toString() {
        return "CasillaCalle{ nombre = " +  super.getNombre() + ", importe = " + importe + '}';
    }

    public float getImporte() {
      return importe;
    }
}
