/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author marina
 */
public class TestP1 {
    public static void main(String[] args) {

      // Comprobacion metodo quienEmpieza

      int cont1, cont2, cont3, cont4, aux;

      cont1=cont2=cont3=cont4=0;

      for ( int i=0; i<100; i++ ) {

        aux = Dado.getInstance().quienEmpieza(4);
        if(aux == 0)
          cont1++;
        else if (aux == 1)
          cont2++;
        else if (aux == 2)
          cont3++;
        else
          cont4++;

      }

      System.out.println("Prueba probabilidades método quienEmpieza(): ");
      System.out.println(" ");

      System.out.println("Probabilidad de que salga el jugador número 1: " + cont1 + "%");
      System.out.println("Probabilidad de que salga el jugador número 2: " + cont2 + "%");
      System.out.println("Probabilidad de que salga el jugador número 3: " + cont3 + "%");
      System.out.println("Probabilidad de que salga el jugador número 4: " + cont4 + "%");

      System.out.println(" ");
      System.out.println("-----------------------------------------------------------------");
      System.out.println(" ");

      // Prueba modo debug dado
        System.out.println("Prueba modo debug dado: ");
        System.out.println(" ");
        System.out.println("DADO EN MODO DEBUG:");

        Dado.getInstance().setDebug(true, Diario.getInstance());

        for (int i = 0; i < 10; i++) {
            Dado.getInstance().tirar();
            System.out.print(Dado.getInstance().getUltimoResultado() + " ");
        }

        System.out.println(" ");
        System.out.println("DADO EN MODO NO DEBUG:");

        Dado.getInstance().setDebug(false, Diario.getInstance());

        for (int i = 0; i < 10; i++) {
            Dado.getInstance().tirar();
            System.out.print(Dado.getInstance().getUltimoResultado() + " ");
        }



      System.out.println("\n");
      System.out.println("-----------------------------------------------------------------");
      System.out.println(" ");



      System.out.println("Prueba getUltimoResultado() y salgoDeLaCarcel(): ");
      System.out.println(" ");

      for(int i=0; i<10; i++){
        if(Dado.getInstance().salgoDeLaCarcel())
          System.out.println("Tirada: " + Dado.getInstance().getUltimoResultado() + " --> Sales de la Cárcel");
        else
          System.out.println("Tirada: " + Dado.getInstance().getUltimoResultado() + " --> Te quedas en la Cárcel");
      }

      System.out.println(" ");
      System.out.println("-----------------------------------------------------------------");
      System.out.println(" ");


      System.out.println("Prueba Enumerados:\n");

      System.out.println("Tipos Casilla: "+ TipoCasilla.CALLE + ", " + TipoCasilla.SORPRESA);
      System.out.println("Tipos Sorpresa: "+ TipoSorpresa.PAGARCOBRAR + ", " + TipoSorpresa.IRCARCEL);
      System.out.println("Estados Juego: "+ EstadosJuego.INICIO_TURNO + ", " + EstadosJuego.DESPUES_CARCEL);

      System.out.println(" ");
      System.out.println("-----------------------------------------------------------------");
      System.out.println(" ");

      System.out.println("Prueba MazoSorpresas:\n");

      MazoSorpresas mazo = new MazoSorpresas();
      Sorpresa s1 = new Sorpresa(TipoSorpresa.IRCASILLA,2, "Sorpresa 1");
      Sorpresa s2 = new Sorpresa(TipoSorpresa.IRCASILLA,2,"Sorpresa 2");

      mazo.alMazo(s1);
      mazo.alMazo(s2);

      Sorpresa s3 =  mazo.siguiente();

      mazo.inhabilitarCartaEspecial(s2);

      mazo.habilitarCartaEspecial(s2);

      System.out.println("Contenido de Diario: ");

      while (Diario.getInstance().eventosPendientes())
        System.out.println(Diario.getInstance().leerEvento());

      System.out.println(" ");
      System.out.println("-----------------------------------------------------------------");

      System.out.println("Prueba Tablero:\n");

      System.out.println("TABLERO INCORRECTO: ");

      System.out.println("Casilla Cárcel en una posición incorrecta");

      Tablero tableroIncorrecto1 = new Tablero (5);

      System.out.println("Añadimos casillas al tablero ");

      for(int i=1; i < 9; i++){
        if (i==tableroIncorrecto1.getCarcel())
              i++;
        Casilla casilla = new Casilla ("Casilla " + i);
        tableroIncorrecto1.añadeCasilla(casilla);
      }

      tableroIncorrecto1.añadeJuez();

      System.out.println("Consultamos una casilla, nos debe devolver nil, ya que no hay carcel");

      if(tableroIncorrecto1.getCasilla(3) == null)
        System.out.println("El tablero no es correcto");


      System.out.println("······································");

      System.out.println("Consultamos una casilla erronea: ");

      Tablero tableroIncorrecto2 = new Tablero (5);

      System.out.println("Añadimos casillas al tablero ");

      for(int i=1; i < 9; i++){
        if (i==tableroIncorrecto2.getCarcel())
              i++;
        Casilla casilla = new Casilla ("Casilla " + i);
        tableroIncorrecto2.añadeCasilla(casilla);
      }

      System.out.println("Consultamos una casilla, nos debe devolver nil, ya que no hay juez");

      if(tableroIncorrecto2.getCasilla(3) == null)
        System.out.println("El tablero no es correcto");

        System.out.println("······································");

        System.out.println("No hay juez: ");

        Tablero tableroIncorrecto3 = new Tablero (5000);

        System.out.println("Añadimos casillas al tablero ");

        for(int i=1; i < 9; i++){
          if (i==tableroIncorrecto3.getCarcel())
                i++;
          Casilla casilla = new Casilla ("Casilla " + i);
          tableroIncorrecto3.añadeCasilla(casilla);
        }

        tableroIncorrecto3.añadeJuez();

      System.out.println("Consultamos una casilla inexistente, nos debe devolver nil");

      if(tableroIncorrecto3.getCasilla(1000000000) == null)
        System.out.println("No existe esa casilla");

      System.out.println("TABLERO CORRECTO: ");

      Tablero tablero = new Tablero (5);

      for(int i=1; i < 9; i++){
        if (i==tablero.getCarcel())
              i++;
        Casilla casilla = new Casilla ("Casilla " + i);
        tablero.añadeCasilla(casilla);
      }

      tablero.añadeJuez();

      int playerPos = 0;
      int newPlayerPos = 0;
      int numVueltas = 0;
      boolean preso = false;
      System.out.println( "Comienza el juego en posición: " + playerPos );
      System.out.println( "Casilla Actual: " + tablero.getCasilla(playerPos).getNombre() );

      for ( int i=0; i<15; i++ ){
          System.out.println( "······································"  );


          if(playerPos == tablero.getCarcel() && preso){
            System.out.println("Estás en la cárcel");

            if (Dado.getInstance().salgoDeLaCarcel()){
                System.out.print("Has sacado " + Dado.getInstance().getUltimoResultado());
                System.out.println(". Sales de la cárcel");
                newPlayerPos = tablero.nuevaPosicion(playerPos, Dado.getInstance().tirar());
                preso = false;
                System.out.println( "Tirada: " + tablero.calcularTirada(playerPos, newPlayerPos) );
                System.out.println( "Nueva Posición: " + newPlayerPos );
                System.out.println( "Casilla Actual: " + tablero.getCasilla(newPlayerPos).getNombre() );
            } else {
                System.out.print("Has sacado " + Dado.getInstance().getUltimoResultado());
                System.out.println(". Sigues en la cárcel");
                newPlayerPos = playerPos;
            }


          } else {
              newPlayerPos = tablero.nuevaPosicion(playerPos, Dado.getInstance().tirar());
              System.out.println("Tirada: " + tablero.calcularTirada(playerPos, newPlayerPos));
              System.out.println("Nueva Posición: " + newPlayerPos);
              System.out.println("Casilla Actual: " + tablero.getCasilla(newPlayerPos).getNombre());

          }

           if ( tablero.getCasilla(newPlayerPos).getNombre() == "Juez" ){ //CASILLA JUEZ
            newPlayerPos =  tablero.getCarcel();
            System.out.println("Vas a la cárcel");
            preso = true;
          }


          playerPos=newPlayerPos;

          if (tablero.getPorSalida() > 0)
              numVueltas ++;

          System.out.println( "Vuelta: " + numVueltas );

      }


    }

}
