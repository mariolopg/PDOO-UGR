 
package civitas;
 
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/** @author Marina Muñoz Cano y Mario López González */

public class Casilla {

  private String nombre;  
  
  /**
   * Constructor de casillas de tipo DESCANSO
   */
  Casilla(String nombre) {
    this.nombre = nombre;
  }  
 
  /**
   * Consultor del atributo nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Metodo informe
   *  Efecto:
   *    Informa al diario de que el jugador ha caido en la casilla que llama al metodo
   */
  protected void informe(int iactual, ArrayList<Jugador> todos) {
    String evento = "El jugador " + todos.get(iactual).getNombre() + " ha caido en la casilla " + toString();
    Diario.getInstance().ocurreEvento(evento);
  }


  /**
   * Metodo jugadorCorrecto
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Devuelve:
   *    si el jugador de indice iacutal existe
   */
  public boolean jugadorCorrecto(int iactual, ArrayList<Jugador> todos) {
    Boolean correcto=false;
    if ( iactual>=0 && iactual<todos.size())
      correcto=true;
    return correcto;
  }


  /**
   * Metodo jugadorCorrecto
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla recibe al jugador actual al que se le aplica el efecto de ésta e indica si el jugador es correcto o no
   */
  void recibeJugador(int iactual, ArrayList<Jugador> todos) { //cambiamos el tipo devuelto para no repetir código en las subClases
    if(jugadorCorrecto(iactual, todos)){
      informe(iactual, todos);
    }
  }

  /**
   * Sobrecarga del metodo toString() de java
   */
  @Override
  public String toString() {         
    return "Casilla:  nombre = " + nombre;
  }
}
