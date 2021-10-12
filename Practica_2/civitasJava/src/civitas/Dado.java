/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;
import java.util.Random;

/**
 *
 * @author Marina Muñoz Cano y Mario López González
 */
public class Dado {
  private Random random;
  private int ultimoResultado;
  private Boolean debug;

  static private Dado instance = new Dado();
  static private int SalidaCarcel = 5;

  /*
    Constructor sin parámetros de la clase Dado.
    Inicializa todos los atributos de instancia de la clase Dado.
  */
  private Dado(){
    random = new Random();
    ultimoResultado = 0;
    debug = false;
  }

  /*
    Consultor del atributo instance de la clase Dado
  */
  static public Dado getInstance(){
    return instance;
  }

  /*
    Método Tirar
      Devuelve:
        Un numero entre 1 y 6 correspondiente a la tirada del dado.
      Efecto:
        Si modo debug esta activado devuelve 1.
        Si no debuelve un numero aleatorio entre 1 y 6.
  */
  int tirar(){
    if(debug == false)
      ultimoResultado = random.nextInt(6) + 1; //random.nextInt(6) da un valor [0,5] entonces sumamos 1 para que sea [1,6] (valores de dados)
    else
      ultimoResultado = 1;
    return ultimoResultado;
  }

  /*
    Método salgoDeLaCarcel
      Devuelve:
        Si se puede o no salir de la cárcel.
      Efecto:
        Tira el dado, si la tirada es 5 o 6 devuelve true.
        En caso contrario devuelve false.
  */
  Boolean salgoDeLaCarcel(){
    Boolean salgo=false;
    if(tirar() >= SalidaCarcel)
      salgo = true;
    return salgo;
  }

  /*
    Método Quien Empieza
      Parámetros:
        n: número de jugadores
      Devuelve:
        El índice del jugador que empieza.
      Efecto:
        Devuelve un número aleatorio entre 0 y n-1.
  */

  int quienEmpieza(int n){
    return random.nextInt(n);
  }

  /*
    Modificador de Debug
      Parámetros:
        d: nuevo valor de debug (true o false)
      Efecto:
        Pone en debug el valor de d y deja constancia del cambio en el diario
  */
  void setDebug(Boolean d, Diario diario){
    debug = d;

    if ( d==true )
      Diario.getInstance().ocurreEvento("Dado en modo debug");
    else
      Diario.getInstance().ocurreEvento("Dado en modo no debug");

  }

  /*
    Consultor de Ultimo Resultado
      Devuelve:
        El último resultado obtenido al lanzar el dado
  */
  int getUltimoResultado(){
    return ultimoResultado;
  }

    @Override
    public String toString() {
        return "Dado{" + "random=" + random + ", ultimoResultado=" + ultimoResultado + ", debug=" + debug + '}';
    }

  
  
}
