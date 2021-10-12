/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author mariolopezgonzalez
 */ 
public class SorpresaRegalo extends Sorpresa {
    
    private ArrayList<Regalo> cajonRegalos;
    
    SorpresaRegalo(String nombre, ArrayList<Regalo> cajonRegalos){ 
        super(nombre, -1, null);
        this.cajonRegalos = cajonRegalos;
    }
    
    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            if(cajonRegalos.size() > 0){
                Collections.shuffle(cajonRegalos);
                todos.get(actual).sacarRegalo(cajonRegalos);
            }
        }
    }
    
    @Override
    public String toString() {
        return "SorpresaRegalo{ texto="+ super.getTexto() + cajonRegalos + '}';
    }
}
