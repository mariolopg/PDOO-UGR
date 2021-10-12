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
public class SorpresaEspeculador extends Sorpresa{
    
    SorpresaEspeculador(int fianza, String texto){
       super(texto, fianza, null);
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Jugador jugador = todos.get(actual);
            JugadorEspeculador jugadorEspeculador = new JugadorEspeculador(jugador, super.getValor());
            todos.set(actual, jugadorEspeculador);
        }
    }

    @Override
    public String toString() {
        return "SorpresaEspeculador{ "+ super.getTexto() + ", fianza=" + super.getValor() + '}';
    }
}
