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
public class Tablero {

    private int numCasillaCarcel;
    private int porSalida;
    private Boolean tieneJuez;

    // Atributos de referencia
    private ArrayList<Casilla> casillas;

    //Nuevo
    static private Tablero instance = null;
    private ArrayList <Barrio> barrios;

    Tablero(int nCasCarcel) {
        if (nCasCarcel >= 1) {
            numCasillaCarcel = nCasCarcel;
        } else {
            numCasillaCarcel = 1;
        }

        casillas = new ArrayList();
        Casilla salida = new Casilla("Salida");
        casillas.add(salida);
        porSalida = 0;
        tieneJuez = false;
        barrios = new ArrayList();
    }

    /*
   * Metodo Correcto Devuelve: Si el tablero es correcto o no Efecto: El tablero
   * es correcto si la posicion de la carcel está dentro del tablero y si tiene
   * juez.
     */
    private Boolean correcto() {
        boolean correcto = false;

        if (casillas.size() > numCasillaCarcel && tieneJuez == true) {
            correcto = true;
        }

        return correcto;
    }

    /*
   * Metodo Correcto Parámetros: numCasilla: indice de una Casilla Devuelve: Si el
   * tablero es correcto o no Efecto: El tablero es correcto si la posicion de la
   * carcel está dentro del tablero y si tiene juez. Y si numCasilla es un indice
   * válido ( 0 <= numCasilla < size ).
     */
    private Boolean correcto(int numCasilla) {
        boolean correcto = false;

        if (correcto() && casillas.size() > numCasilla && numCasilla >= 0) {
            correcto = true;
        }

        return correcto;
    }

    /*
   * Consultor getCarcel Devuelve: El numero de casilla en la que se encuenta la
   * Carcel
     */
    int getCarcel() {
        return numCasillaCarcel;
    }

    /*
   * Metodo getPorSalida Devuelve: Las veces que se ha pasado por la salida
   * Efecto: Si el numero de veces es >0 lo decrementa y devuelve el valor que
   * tenía nates de ser decrementado.
     */
    int getPorSalida() {

        int pSalida = porSalida;

        if (porSalida > 0) {
            porSalida--;
        }

        return pSalida;
    }

    /*
   * Metodo añadeCasilla Parametros: casilla: Casilla que se va a añadir al
   * tablero Efecto: Añade la casilla al final del tablero. Si la casilla se va a
   * añadir en la posicion donde deberia ir la carcel, se añade primero ésta. Si
   * al añadir la casilla la siguiente casilla es la carcel, la añade.
     */
    void añadeCasilla(Casilla casilla) {

        if (casillas.size() == numCasillaCarcel) {
            Casilla carcel = new Casilla("Carcel");
            casillas.add(carcel);
        }

        casillas.add(casilla);

        if (casillas.size() == numCasillaCarcel) {
            Casilla carcel = new Casilla("Carcel");
            casillas.add(carcel);
        }
    }

    /*
   * Metodo añadeJuez Efecto: Si el tablero no tiene casilla jue, la añade al
   * final de éste.
     */
    void añadeJuez() {
        if (!tieneJuez) {
            Casilla juez = new Casilla(numCasillaCarcel, "Juez");
            casillas.add(juez);
            tieneJuez = true;
        }
    }

    /*
   * Metodo getCasilla Parametros: numCasilla: indice de la casilla que se quiere
   * consultar Devuelve: La casilla de indice numCasilla Efecto: Si el indice es
   * correcto devuelve la casilla de indice numCasilla Excepción: Si el indice no
   * es corrrecto devuelve null.
     */
    public Casilla getCasilla(int numCasilla) {

        if (correcto(numCasilla)) {
            return casillas.get(numCasilla);
        } else {
            return null;
        }
    }

    /*
   * Método Constultor del atibuto casillas
     */
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    /*
   * Metodo nuevaPosicion 
   *  Parametros: 
   *    actual: posicion actual del jugador 
   *    tirada: valor de la tirada del dado Devuelve: La nueva posicion del jugador 
   * Efecto:
   *    Calcula la casilla a la que tiene que moverse el jugador. 
   * Excepción: 
   * Su la posicion no es correcta devuelve -1.
     */
    int nuevaPosicion(int actual, int tirada) {

        int nuevaPosicion = -1;

        if (correcto(actual)) {
            nuevaPosicion = (actual + tirada) % casillas.size();

            if (nuevaPosicion != (actual + tirada)) {
                porSalida++;
            }
        }

        return nuevaPosicion;
    }

    /*
   * Metodo calcularTirada Parametros: origen: casilla en la que se encuentra el
   * jugador destino: casilla a la que se mueve el jugador Devuelve: La tirada que
   * ha sacado el jugador para moverse a la casilla destino Efecto: Devuelve la
   * tirada que se ha necesitado para ir de origen a destino.
     */
    int calcularTirada(int origen, int destino) {

        int tirada = destino - origen;
        if (tirada < 0) {
            tirada += casillas.size();
        }

        return tirada;
    }

    @Override
    public String toString() {
        return "Tablero{" + "numCasillaCarcel=" + numCasillaCarcel + ", casillas=" + casillas + ", porSalida=" + porSalida
                + ", tieneJuez=" + tieneJuez + '}';
    }

    //Nuevo
    static public Tablero getInstance(){
        return instance;
    }
    
    public static void setInstance(int carcel) {
        if (instance == null) {
            instance = new Tablero(carcel);
        }
    }
    
    void aniadeBarrio(Barrio barrio){
        barrios.add(barrio);
    }
    
    public ArrayList <TituloPropiedad> getInmueblesEnBarrio(Barrio barrio){
        ArrayList <TituloPropiedad> propiedadesEnBarrio = new ArrayList();
        for(int i = 1; i < casillas.size(); i++){
            Casilla unaCasilla = Tablero.getInstance().getCasillas().get(i);
            TipoCasilla tipo = unaCasilla.getTipo();
            if(tipo == TipoCasilla.CALLE){
                TituloPropiedad unTitulo = unaCasilla.getTituloPropiedad();
                Barrio unBarrio = unTitulo.getBarrio();
                if(barrio == unBarrio)
                    propiedadesEnBarrio.add(unTitulo);
            }
        }
        
        return propiedadesEnBarrio;
    }

    ArrayList<Barrio> getBarrios() {
        return barrios;
    }
    
    

}
