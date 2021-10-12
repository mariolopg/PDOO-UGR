package juegoTexto;

import civitas.Barrio;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import civitas.Casilla;
import civitas.CivitasJuego;
import civitas.Jugador;
import civitas.OperacionesJuego;
import civitas.SalidasCarcel;
import civitas.TituloPropiedad;
import civitas.Respuestas; 
import civitas.Tablero;
 
 class VistaTextual {
  
  CivitasJuego juegoModel; 
  int iGestion=-1;
  int iPropiedad=-1;
  private static String separador = "=====================";
  
  private Scanner in;
  
   VistaTextual () {
    in = new Scanner (System.in);
  }
  
  void mostrarEstado(String estado) {
    System.out.println (estado);
  }
              
  void pausa() {
    System.out.print ("Pulsa una tecla ");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

  SalidasCarcel salirCarcel() {
    int opcion = menu ("Elige la forma para intentar salir de la carcel",
      new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
    return (SalidasCarcel.values()[opcion]);
  }

//  Respuestas comprar() {
//    int opcion = menu ("¿Deseas comprar " + juegoModel.getCasillaActual().getNombre() + "?",
//      new ArrayList<> (Arrays.asList("Si", "No")));
//    return (Respuestas.values()[opcion]);
//  }

  void gestionar () {
    iGestion= menu("¿Qué gestión quieres realizar?",
      new ArrayList<> (Arrays.asList("Vender", "Hipotecar", "Cancelar hipoteca", "Construir casa", "Construir hotel", "Terminar")));

      ArrayList<String> nombresPropiedades = new ArrayList();

    for(int i = 0; i < juegoModel.getJugadorActual().getPropiedades().size(); i++)
      nombresPropiedades.add(juegoModel.getJugadorActual().getPropiedades().get(i).toString());

    if (iGestion != 5) // Si la gestion no es terminar
        iPropiedad = menu("¿Sobre qué propiedad?", nombresPropiedades);
    
  }
  
  public int getGestion(){
    return iGestion;
  }
  
  public int getPropiedad(){
    return iPropiedad;
  }
    

  void mostrarSiguienteOperacion(OperacionesJuego operacion) {
    System.out.println("La siguiente operación que se va a realizar es " + operacion);
  }


  void mostrarEventos() {
    while(Diario.getInstance().eventosPendientes())
      System.out.println(Diario.getInstance().leerEvento());
  }
  
  public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();
  }
  
  void actualizarVista(){
    System.out.println("Jugador actual: " + juegoModel.infoJugadorTexto());
    System.out.println("Casilla actual: " + juegoModel.getCasillaActual().toString());
  } 
  
  //Nuevo
  Respuestas comprar(){
      
      int njug = juegoModel.getIndiceJugadorActual();
      Jugador jugadorActual = juegoModel.getJugadorActual();
      int numCasilla = jugadorActual.getNumCasilaActual();
      Tablero tablero = Tablero.getInstance();
      ArrayList <Casilla> casillas = tablero.getCasillas();
      Casilla casilla = tablero.getCasilla(numCasilla);
      TituloPropiedad titulo = casilla.getTituloPropiedad();
      Barrio barrio = titulo.getBarrio();
      String estados;
      int opcion = menu (barrio.getEstadoInmuebles(),
      new ArrayList<> (Arrays.asList("Si","No")));
      return Respuestas.values()[opcion];
  }
  
}
