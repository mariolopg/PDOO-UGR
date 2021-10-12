/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
 
import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;


/** @author Marina Muñoz Cano y Mario López González */
public class CivitasJuego {

  private int indiceJugadorActual;

  // Atributos de referencia
  private Tablero tablero;
  private MazoSorpresas mazo;
  private ArrayList<Jugador> jugadores;
  private EstadosJuego estado;
  private GestorEstados gestorEstados;
 

 /**
  * Constructor con parametros
  *   Recibe como parametros los nombres de los jugadores
  */
  public CivitasJuego(ArrayList<String> nombres) {
    jugadores = new ArrayList<Jugador>();
    for (int i=0; i<nombres.size(); i++)
      jugadores.add(new Jugador(nombres.get(i)));

    gestorEstados = new GestorEstados();
    estado = gestorEstados.estadoInicial();

    indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

    mazo = new MazoSorpresas(true);
    inicializarTablero(mazo);
    inicializarMazoSorpresas(tablero);
 
  }

  /**
   * Avanza jugador
   *  El jugador actual tira el dado y se mueve a la casilla correspondiente
   */
  private void avanzaJugador(){
    Jugador jugadorActual = getJugadorActual();
    int posicionActual = jugadorActual.getNumCasillaActual();
    int tirada = Dado.getInstance().tirar();
    int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);  
    Casilla casilla = tablero.getCasilla(posicionNueva);
    contabilizarPasosPorSalida(jugadorActual);
    jugadorActual.moverACasilla(posicionNueva);
    casilla.recibeJugador(indiceJugadorActual, jugadores);
    contabilizarPasosPorSalida(jugadorActual);
  }

  /**
   * Cancelar hipoteca
   *    Cancela la hipoteca de la propiedad ip del jugador actual
   */
  
  public boolean cancelarHipoteca(int ip) {
    return getJugadorActual().cancelarHipoteca(ip);
  }

  /** 
   * Comprar 
   *  El jugador actual compra el título de la casilla en la que se encuentra
   */
  public boolean comprar(){
    Jugador jugadorActual = getJugadorActual();
    int numCasillaActual = jugadorActual.getNumCasillaActual();
    CasillaCalle casilla = (CasillaCalle) tablero.getCasilla(numCasillaActual);
    TituloPropiedad titulo = casilla.getTituloPropiedad();
    return jugadorActual.comprar(titulo);
  }

  /**
   * Construir Casa
   *  Construye una casa en la propiedad ip del jugador actual
   */
  public boolean construirCasa(int ip) {
    return getJugadorActual().construirCasa(ip);
  }


  /**
   * Construir Hotel
   *  Construye un hotel en la propiedad ip del jugador actual
   */
  public boolean construirHotel(int ip) {
    return getJugadorActual().construirHotel(ip);
  }

  /**
   * Contabiliza pasos por salida
   *  Contabiliza las veces que ha pasado el jugador actual pro la salida
   */
  private void contabilizarPasosPorSalida(Jugador jugadorActual) {
    while (tablero.getPorSalida()>0)
      jugadorActual.pasaPorSalida();
  }

  /**
   * Final del juego
   *  Devuelve true si hay algun jugador en bancarrota, false en caso contrario
   */
  public boolean finalDelJuego() {
    boolean fin = false;
    for (int i=0; i<jugadores.size() && !fin; i++)
      if (jugadores.get(i).enBancarrota())
        fin = true;
    return fin;
  }

  /**
   * get casilla actual
   *  devuelve la casilla en la que se encuentra el jugador actual
   */
  public Casilla getCasillaActual() {
    int indiceCasilla = getJugadorActual().getNumCasillaActual();
    return tablero.getCasilla(indiceCasilla);
  }

  /**
   * get jugador actual
   *    Devuelve el jugador que esta jugando en el momento
   */
  public Jugador getJugadorActual() {
    return jugadores.get(indiceJugadorActual);
  }

  /**
   * hipotecar
   *  Hipoteca la propieddad ip del jugador actual
   */
  public boolean hipotecar(int ip) {
    return getJugadorActual().hipotecar(ip);
  }
 
  /**
   * info jugador texto
   *  Devuelve un string con la informacion del jugador actual
   */
  public String infoJugadorTexto() {
    return getJugadorActual().toString();
  }

  /**
   * inicializar Mazo Sorpresas
   *  Inicializa el mazo sorpresas a partir del tablero. Crea todas las cartas y las añade al mazo
   */
  private void inicializarMazoSorpresas(Tablero tablero) {
    Random random = new Random();     //Creamos una variable random para randomizar los valores de las cartas
    int valor;

    valor = random.nextInt(1) + 100;
    mazo.alMazo(new SorpresaEspeculador(valor, "Te conviertes en jugador especulador."));
    
    //Creamos la Carta sorpresa pagar
    valor = random.nextInt(901) + 100;     //Valor random entre 100 y 1000
    mazo.alMazo(new SorpresaPagarCobrar(-valor , "Has sido multado. Debes pagar " + valor + "€."));

    //Creamos la Carta sorpresa cobrar
    valor = random.nextInt(901) + 100;
    mazo.alMazo(new SorpresaPagarCobrar(valor, "¡Enhorabuena! Recibes " + valor + "€ por buen comportamiento. "));

    //Creamos las Cartas ir casilla  (dos normales y una para la carcel)
    valor = random.nextInt(tablero.getCasillas().size());   // Generamos un entero aleatorio entre 0 y el numero total de casillas
    while (valor == tablero.getCarcel())  //Volvemos a generarlo si es el numero de la carcel
      valor = random.nextInt(tablero.getCasillas().size());
    mazo.alMazo(new SorpresaIrCasilla(tablero, valor,"Muévete a la casilla " + tablero.getCasilla(valor).getNombre()));


    valor = random.nextInt(tablero.getCasillas().size()); // Generamos un entero aleatorio entre 0 y el numero total de casillas
    while (valor == tablero.getCarcel())  //Volvemos a generarlo si es el numero de la carcel
      valor = random.nextInt(tablero.getCasillas().size());
    mazo.alMazo(new SorpresaIrCasilla(tablero, valor,"Muévete a la casilla " + tablero.getCasilla(valor).getNombre()));

    mazo.alMazo(new SorpresaIrCasilla(tablero, tablero.getCarcel(), "Vas de visita a la cárcel"));

    // Creamos las Cartas por casa hotel
    valor = random.nextInt(151) + 50;  //Paga entre 50 y 250 por casa u hotel
    mazo.alMazo(new SorpresaPorCasaHotel(-valor , "Has sido multado. Debes pagar " + valor + "€. Por cada casa u hotel que tengas"));

    valor = random.nextInt(151) + 50;  //Paga entre 50 y 250 por casa u hotel
    mazo.alMazo(new SorpresaPorCasaHotel(valor , "¡Enhorabuena! Recibes " + valor + "€. Por cada casa u hotel que tengas"));

    //Creamos las Cartas por jugador
    valor = random.nextInt(201) + 50;  //Paga entre 50 y 300 a cada jugador
    mazo.alMazo(new SorpresaPorJugador(-valor , "Has sido multado. Debes pagar " + valor + "€ a cada jugador"));

    valor = random.nextInt(201) + 50;  //Cobra entre 50 y 300 de cada jugador
    mazo.alMazo(new SorpresaPorJugador(valor , "¡Enhorabuena! Recibes " + valor + "€ de cada jugador"));

    //Creamos la Carta Salir Carcel
    mazo.alMazo(new SorpresaSalirCarcel(mazo));

    //Creamos la Carta Ir Carcel
    mazo.alMazo(new SorpresaIrCarcel(tablero));
  
  }

  /**
   * inicializar tablero
   *  Crea el tablero y lo inicializa añadiendole todas las Casillas
   */
  private void inicializarTablero(MazoSorpresas mazo) {
    tablero = new Tablero(5);

    //Creamos las Casillas 
    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Moscú", 20, (float)1.05, 100 , 200, 125)));
    
    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Nairobi", 60, (float)0.95, 150, 300, 125)));
    
    //Sorpresa
    tablero.añadeCasilla(new CasillaSorpresa (mazo, "Sorpresa"));

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Manila", 50, (float)0.5, 250, 500, 250)));
  
    //Se añade automaticamente la carcel

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Marsella", 70, (float)1.5, 300, 700, 250)));
    
    //sorpresa
    tablero.añadeCasilla(new CasillaSorpresa (mazo, "Sorpresa"));

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Palermo", 80, (float)0.85, 400, 960, 500)));
    
    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Berlin", 100, (float)1.15, 500, 1000, 500)));
    
    //Parking
    tablero.añadeCasilla(new Casilla ("Descanso"));

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Helsinki", 120, (float)1.25, 600, 1200, 625)));
    
    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Lisboa", 130, (float)0.75, 650, 1300, 625)));
   
    //impuesto
    Random random = new Random();
    int cantidad = random.nextInt(451)+50; //Impuesto random entre 50 y 500
    tablero.añadeCasilla(new CasillaImpuesto ((float)cantidad, "Impuesto"));

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Estocolmo", 150, (float)0.8, 750, 1500, 750)));
    
    //Juez
    tablero.añadeJuez();

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Denver", 170, (float)1.2, 850, 1700, 875)));
    

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Rio", 180, (float)1.1, 900, 1800, 875)));
    
    // sorpresa
    tablero.añadeCasilla(new CasillaSorpresa (mazo, "Sorpresa"));

    tablero.añadeCasilla(new CasillaCalle (new TituloPropiedad("Tokio", 200, (float)0.9, 1000, 2000, 1000)));
      
  }

  /**
   * pasar turno
   *  pasa el turno al siguiente jugador
   */
  private void pasarTurno() {
    indiceJugadorActual++;
    indiceJugadorActual%=jugadores.size();
  }

  /**
   * ranking
   *  ordena a los jugadores en funcion de su saldo de mayor a menor
   */
  public ArrayList<Jugador> ranking() {
    Collections.sort(jugadores);
    Collections.reverse(jugadores);
    return jugadores;
  }

  /**
   * salir carcel pagando
   *  Devuelve true si el jugador actual puede salir de la carcel pagando, false en caso contrario. 
   *  En caso afirmativo sale de la carcel
   */
  public boolean salirCarcelPagando() {
    return getJugadorActual().salirCarcelPagando();
  }

  /**
   * salir carcel tirando
   *  Devuelve true si el jugador actual puede salir de la carcel tirando, false en caso contrario.  
   *  En caso afirmativo sale de la carcel
   */
  public boolean salirCarcelTirando() {
    return getJugadorActual().salirCarcelTirando();
  }

  /**
   * siguiente paso 
   *   Devuelve la siguiene operacion que se puede llevar a cabo
   */
  public OperacionesJuego siguientePaso() {
    Jugador jugador = getJugadorActual();
    OperacionesJuego operacion = gestorEstados.operacionesPermitidas(jugador, estado);
    if (operacion == OperacionesJuego.PASAR_TURNO){
      pasarTurno();
      siguientePasoCompletado(operacion);
    }
    else if (operacion == OperacionesJuego.AVANZAR){
      avanzaJugador();
      siguientePasoCompletado(operacion);
    }

      return operacion;
  }

  /**
   * siguiente paso completado
   *  Se actualiza el estado del juego obteniendo el siguiente del gestor de estados
   */
  public void siguientePasoCompletado(OperacionesJuego operacion) {
    estado = gestorEstados.siguienteEstado(getJugadorActual(), estado, operacion);
  }

  /**
   * vender
   *  Vende la propiedad ip del jugador actual
   */
  public boolean vender(int ip) {
    return getJugadorActual().vender(ip);
  }

}
