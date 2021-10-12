/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
 
import java.util.ArrayList;

/**
 * @author Marina Muñoz Cano y Mario López González
 */
public abstract class Sorpresa {

    private String texto;
    private int valor;
    private Tablero tablero;

    Sorpresa(String texto, int valor, Tablero tablero) {
        this.texto = texto;
        this.valor = valor;
        this.tablero = tablero;
    }

    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);

    /*
    Método informe()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Informa al diario que se está aplicando una sorpresa al jugador actual
     */
    protected void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se está aplicando una sorpresa al jugador " + todos.get(actual).getNombre());
    }


    /*
    Método jugadorCorrecto()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Return:
      Devuelve true si el jugador es correcto
     */
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (actual >= 0 && actual < todos.size());
    }

    //Public para GUI
    public String getTexto() {
        return texto;
    }

    protected int getValor(){
      return valor;
    }

    protected Tablero getTablero(){
      return tablero;
    }

}
