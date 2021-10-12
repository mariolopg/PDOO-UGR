/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/** @author Marina Muñoz Cano y Mario López González */
public class Casilla {

  private static int carcel;
  private float importe;
  private String nombre;

  // Artibutos de referencia
  private TipoCasilla tipo;
  private TituloPropiedad tituloPropiedad;
  private Sorpresa sorpresa;
  private MazoSorpresas mazo;
  
  /**
   * Constructor de casillas de tipo DESCANSO
   */
  Casilla(String nombre) {
    init();
    tipo = TipoCasilla.DESCANSO;
    this.nombre = nombre;
  }

  /**
   * Constructor de casillas de tipo CALLE
   */
  Casilla(TituloPropiedad titulo) {
    init();
    tipo = TipoCasilla.CALLE;
    nombre = titulo.getNombre();
    importe = titulo.getPrecioCompra();
  }

  /**
   * Constructor de casillas de tipo IMPUESTO
   */
  Casilla(float cantidad, String nombre) {
    init();
    tipo = TipoCasilla.CALLE;
    this.nombre = nombre;
    importe = cantidad;
  }

  /**
   * Constructor de casillas de tipo JUEZ
   */
  Casilla(int numCasillaCarcel, String nombre) {
    init();
    tipo = TipoCasilla.JUEZ;
    this.nombre = nombre;
    carcel = numCasillaCarcel;
  }

  /**
   * Constructor de casillas de tipo SORPRESA
   */
  Casilla(MazoSorpresas mazo, String nombre) {
    init();
    tipo = TipoCasilla.SORPRESA;
    this.mazo = mazo;
    this.nombre = nombre;
  }

  /**
   * Consultor del atributo nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Consultor del atributo tituloPropiedad
   */
  TituloPropiedad getTituloPropiedad() {
    return tituloPropiedad;
  }

  /**
   * Metodo informe
   *  Efecto:
   *    Informa al diario de que el jugador ha caido en la casilla que llama al metodo
   */
  private void informe(int iactual, ArrayList<Jugador> todos) {
    String evento = "El jugador " + todos.get(iactual).getNombre() + " ha caido en la casilla " + this.toString();
    Diario.getInstance().ocurreEvento(evento);
  }

  /**
   * Método init
   *  Efecto: 
   *   Inicializa todos los atributos a un valor adecuado asumiendo 
   *   que no se proporciona al constructor un valor para ese atributo
   */
  private void init() {
    carcel = 1;
    nombre = " ";
    importe = (float)0.0;
    mazo = null;
    tituloPropiedad = null;
    tipo = null;
    sorpresa = null;
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

  void recibeJugador(int iactual, ArrayList<Jugador> todos) {}

  //private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {}

  /**
   * Metodo recibeJugador_impuesto
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto: 
   *    El jugador paga el importe del impuesto y se informa de ello al diario
   */
  private void recibeJugador_impuesto(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)){
      informe(iactual, todos);
      todos.get(iactual).pagaImpuesto(importe);
    }
  }

  /**
   * Metodo recibeJugador_juez
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto: 
   *    El jugador es encarcelado y se informa de ello al diario
   */
  private void recibeJugador_juez(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)){
      informe(iactual, todos);
      todos.get(iactual).encarcelar(carcel);
    }
  }

  //private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos) {}

  /**
   * Sobrecarga del metodo toString() de java
   */
  @Override
  public String toString() {
    String mensaje;
    switch (tipo) {
      case DESCANSO:
          mensaje = "Casilla:  nombre = " + nombre + " tipo = " + tipo ;
          break;

      case CALLE:
          mensaje = "Casilla:  nombre = " + nombre + " tipo = " + tipo + " importe = " + importe;
          break;

      case IMPUESTO:
          mensaje = "Casilla:  nombre = " + nombre + " tipo = " + tipo + " importe = " + importe;
          break;

      case JUEZ:
          mensaje = "Casilla:  nombre = " + nombre + " tipo = " + tipo + " numero de la Casilla Carcel: " + carcel;
          break;

      case SORPRESA:
          mensaje = "Casilla:  nombre = " + nombre + " tipo = " + tipo + " mazo = " + mazo;
          break;
          
      default:
          mensaje = "La casilla no tiene tipo";
    }
    return mensaje;
  }
}
