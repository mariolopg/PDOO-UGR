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
public class Sorpresa {

    String texto;
    int valor;

    // Atributos de referencia
    private TipoSorpresa tipo;
    private MazoSorpresas mazo;
    private Tablero tablero;

    /*
    Constructor con parámetros
    Crea una sorpresa de tipo IRCARCEL
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo y tablero
     */
    Sorpresa(TipoSorpresa tipo, Tablero tablero) {
        init();
        this.tipo = tipo;
        this.tablero = tablero;
        texto = "Vas a la carcel. Muévete a la casilla Cárcel";
    }

    /*
    Constructor con parámetros
    Crea una sorpresa de tipo IRCASILLA
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo, tablero, valor y texto
     */
    Sorpresa(TipoSorpresa tipo, Tablero tablero, int valor, String texto) {
        init();
        this.tipo = tipo;
        this.tablero = tablero;
        this.valor = valor;
        this.texto = texto;
    }

    /*
    Constructor con parámetros
    Crea una sorpresa para el resto de sorpresas
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo, valor y texto
     */
    Sorpresa(TipoSorpresa tipo, int valor, String texto) {
        init();
        this.tipo = tipo;
        this.valor = valor;
        this.texto = texto;
    }

    /*
    Constructor con parámetros
    Crea una sorpresa de tipo SALIRCARCEL
    Parametros:
      Recibe como parámetros los valores de los atributos: tipo y tablero
     */
    Sorpresa(TipoSorpresa tipo, MazoSorpresas mazo) {
        init();
        this.tipo = tipo;
        this.mazo = mazo;
        texto = "Te libras de la cárcel. Si caes en la cárcel y tienes esta carta puedes salir de ella.";
    }

    /*
    Método aplicarAJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Aplica al jugador la sorpresa dependiendo del tipo
     */
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {

        switch (tipo) {
            case IRCASILLA:
                aplicarAJugador_irACasilla(actual, todos);
                break;

            case IRCARCEL:
                aplicarAJugador_irCarcel(actual, todos);
                break;

            case PAGARCOBRAR:
                aplicarAJugador_pagarCobrar(actual, todos);
                break;

            case PORCASAHOTEL:
                aplicarAJugador_porCasaHotel(actual, todos);
                break;

            case PORJUGADOR:
                aplicarAJugador_porJugador(actual, todos);
                break;

            case SALIRCARCEL:
                aplicarAJugador_salirCarcel(actual, todos);
                break;
        }
    }

    /*
    Método aplicarAJugador_irACasilla()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Envia al jugador actual a la nueva casilla
     */
    private void aplicarAJugador_irACasilla(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            int casillaActual = todos.get(actual).getNumCasilaActual();
            int tirada = tablero.calcularTirada(casillaActual, valor);
            int nuevaPos = tablero.nuevaPosicion(casillaActual, tirada);
            todos.get(actual).moverACasilla(nuevaPos);
            tablero.getCasilla(nuevaPos).recibeJugador(actual, todos);
        }
    }

    /*
    Método aplicarAJugador_irCarel()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Envia al jugador actual a la cárcel
     */
    private void aplicarAJugador_irCarcel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }

    /*
    Método aplicarAJugador_pagarCobrar()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      El jugador paga o cobra el valor indicado en la carta
     */
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            todos.get(actual).modificarSaldo(valor);
        }
    }

    /*
    Método aplicarAJugador_porCasaHotel()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Modifica el saldo del jugador con el valor de la sorpresa multiplicado por el numero de casas y hoteles del jugador
     */
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            int saldo = valor * todos.get(actual).cantidadCasasHoteles();
            todos.get(actual).modificarSaldo(saldo);
        }
    }

    /*
    Método aplicarAJugador_porJugador()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      El jugador actual recibe dinero de todos los jugadores
     */
    private void aplicarAJugador_porJugador(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Sorpresa pagar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * (-1), "Todos pagan al jugador " + todos.get(actual).getNombre());
            Sorpresa cobrar = new Sorpresa(TipoSorpresa.PAGARCOBRAR, valor * (todos.size() - 1), "Jugador " + todos.get(actual).getNombre() + " cobra " + valor + " del resto de jugadores");

            cobrar.aplicarAJugador_pagarCobrar(actual, todos);
            for (int i = 0; i < todos.size(); i++) {
                if (i != actual) {
                    pagar.aplicarAJugador_pagarCobrar(i, todos);
                }
            }
        }
    }

    /*
    Método aplicarAJugador_salirCarcel()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Si nadie tiene la carta para salir de la carcel, la obtiene el jugador actual
     */
    private void aplicarAJugador_salirCarcel(int actual, ArrayList<Jugador> todos) {
        if (jugadorCorrecto(actual, todos)) {
            informe(actual, todos);
            Boolean cartaDisponible = true;

            for (int i = 0; i < todos.size() && cartaDisponible; i++) {
                if (todos.get(i).tieneSalvoconducto()) {
                    cartaDisponible = false;
                }
            }

            if (cartaDisponible) {
                todos.get(actual).obtenerSalvoconducto(this);
            }

        }
    }

    /*
    Método aplicarAJugador_salirCarcel()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Efecto:
      Informa al diario que se está aplicando una sorpresa al jugador actual
     */
    private void informe(int actual, ArrayList<Jugador> todos) {
        Diario.getInstance().ocurreEvento("Se está aplicando una sorpresa al jugador " + todos.get(actual).getNombre());
    }

    /*
  Método init()
  Efecto:
    Punto de partida en los constructores
     */
    private void init() {
        valor = -1;
        mazo = null;
        tablero = null;
        texto = " ";
    }

    /*
    Método aplicarAJugador_salirCarcel()
    Parámetros:
      actual: indice del jugador actual
      todos: array que contiene los jugadores
    Return:
      Devuelve true si el jugador es correcto
     */
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos) {
        return (actual >= 0 && actual < todos.size());
    }

    /*
    Método salirMazo
    Efecto:
      Si la sorpresa es la que inhabilita la cárcel, inhabilita la carta en el mazo
     */
    void salirDelMazo() {
        if (tipo == TipoSorpresa.SALIRCARCEL) {
            mazo.inhabilitarCartaEspecial(this);
        }

    }

    /**
   * Sobrecarga del metodo toString() de java
   */
    @Override
    public String toString() {
        return ("Sorpresa de tipo: " + tipo + " Texto: " + texto);
    }

    /*
    Método usada()
    Efecto:
      Si la sorpresa es la que inhabilita la cárcel, habilita la carta en el mazo
     */
    void usada() {
        if (tipo == TipoSorpresa.SALIRCARCEL) {
            mazo.habilitarCartaEspecial(this);
        }
    }
}
