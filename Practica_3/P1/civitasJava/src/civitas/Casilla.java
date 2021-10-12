
package civitas;
 
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

/** @author Marina Muñoz Cano y Mario López González */
public class Casilla {

  private static int carcel = 1;
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
    tituloPropiedad = titulo;
    tipo = TipoCasilla.CALLE;
    nombre = titulo.getNombre();
    importe = titulo.getPrecioCompra();
  }

  /**
   * Constructor de casillas de tipo IMPUESTO
   */
  Casilla(float cantidad, String nombre) {
    init();
    tipo = TipoCasilla.IMPUESTO;
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
  public TituloPropiedad getTituloPropiedad() {
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


  /**
   * Metodo jugadorCorrecto
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla recibe al jugador actual al que se le aplica el efecto de ésta
   */
  void recibeJugador(int iactual, ArrayList<Jugador> todos) {
    switch(tipo){
      case CALLE:
        recibeJugador_calle(iactual, todos);
        break;

      case IMPUESTO:
        recibeJugador_impuesto(iactual, todos);
        break;

      case JUEZ:
        recibeJugador_juez(iactual, todos);
        break;

      case SORPRESA:
        recibeJugador_sorpresa(iactual, todos);
        break;

      default:
        informe(iactual, todos);

    }
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
  private void recibeJugador_calle(int iactual, ArrayList<Jugador> todos) {
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
   * Metodo recibeJugador_impuesto
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla de tipo impuesto recibe al jugador actual al que se le aplica el efecto de ésta 
   *    y se informa al diario.
   */
  private void recibeJugador_impuesto(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)){
      informe(iactual, todos);
      todos.get(iactual).pagaImpuesto(importe);
      Diario.getInstance().ocurreEvento("El jugador " + todos.get(iactual).getNombre() + " paga un impuesto de " + importe + "€. ");
    }
  }

  /**
   * Metodo recibeJugador_juez
   *  Parámetros:
   *    iactual = indice del jugador actual
   *    todos = Lista con todos los jugadores
   *  Efecto:
   *    La casilla de tipo juez recibe al jugador actual al que se le aplica el efecto de ésta 
   *    y se informa al diario.
   */
  private void recibeJugador_juez(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)){
      informe(iactual, todos);
      todos.get(iactual).encarcelar(carcel);
    }
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
  private void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos) {
    if (jugadorCorrecto(iactual, todos)){
      sorpresa = mazo.siguiente();
      informe(iactual, todos);
      sorpresa.aplicarAJugador(iactual, todos);
    }
  }

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
  
 //Nuevo
  public TipoCasilla getTipo(){
      return tipo;
  }
}
