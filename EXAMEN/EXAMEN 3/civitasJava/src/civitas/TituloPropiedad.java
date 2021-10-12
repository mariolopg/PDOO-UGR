
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 * @author Marina Muñoz Cano y Mario López González
 */
public class TituloPropiedad {

    private float alquilerBase;
    private static float factorInteresesHipoteca = (float) 1.1;
    private float factorRevalorizacion;
    private float hipotecaBase;
    private Boolean hipotecado;
    private String nombre;
    private int numCasas;
    private int numHoteles;
    private float precioCompra;
    private float precioEdificar;
    // Atributos de referencia
    private Jugador propietario;

    /*
    Constructor con parámetros
    Parametros:
      Recibe como parámetros los valores de los atributos: nombre, alquilerBase, factorRevalorizacion, hipotecaBase, precioCompra, precioEdificar
     */
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe) {
        nombre = nom;
        alquilerBase = ab;
        factorRevalorizacion = fr;
        hipotecaBase = hb;
        hipotecado = false;
        numCasas = 0;
        numHoteles = 0;
        precioCompra = pc;
        precioEdificar = pe;
        propietario = null;
    }

    /*
    Método actualizaPropietarioPorConversion
    Parametros:
      Recibe un jugador
    Efecto:
      El propietario de la propiedad se convierte en el jugador pasado como parámetro
     */
    void actualizaPropietarioPorConversion(Jugador jugador) {
        propietario = jugador;
    }

    /**
     *  Método cancelarhipoteca
        Efecto:
          Cancela la hipoteca del título que llama al metodo
        Devuelve:
          Si se ha podido cancelar o no  
     */
    Boolean cancelarHipoteca(Jugador jugador) {
      boolean result = false;
      if (hipotecado)
        if (esEsteElPropietario(jugador)) {
          jugador.paga(getImporteCancelarHipoteca());
          hipotecado = false;
          result = true;
        }
      return result;
    }

    /*
    Método cantidadCasasHoteles
    Devuelve:
      El número de casas más hoteles
     */
    int cantidadCasasHoteles() {
        return numCasas + numHoteles;
    }

     /* 
     Metodo comprar
       Parámetro
         jugador = jugador que va a comprar este titulo
       Devuelve:
         Si el jugador ha podido o no comprar el título 
      */
    boolean comprar(Jugador jugador) {
      boolean result= false;
      if (!tienePropietario()) {
        propietario = jugador;
        result = true;
        propietario.paga(getPrecioCompra());
      }
      return result;
    }

     /* 
     Metodo construirCasa
       Parámetro
         jugador = jugador que va a construir un hotel en esta propiedad
       Devuelve:
         Si el jugador ha podido o no construir el hotel
      */
     boolean construirCasa(Jugador jugador) {
         boolean result = false;
        if(esEsteElPropietario(jugador)){
          jugador.paga(getPrecioEdificar());
          numCasas+= 1;
          result = true;
        }
        return result;
     }

    /* 
     Metodo construirHotel
       Parámetro
         jugador = jugador que va a construir un hotel en esta propiedad
       Devuelve:
         Si el jugador ha podido o no construir el hotel
      */
    boolean construirHotel(Jugador jugador) {
      boolean result = false;
      if(esEsteElPropietario(jugador)){
        jugador.paga(getPrecioEdificar());
        numHoteles += 1;
        result = true;
      }
      return result;
    }
 
    /*
    Método actualizaPropietarioPorConversion
    Parametros:
     n: numero de casas a derruir
     jugador: que va a derruir las casas
    Efecto:
      Si el jugador es el propietario de la propiedad, se decrementa el numero de casas en n unidades
    Devuelve:
      true si se derruyen casas, false en caso contrario
     */
    Boolean derruirCasas(int n, Jugador jugador) {
        boolean derruidas = false;

        if (esEsteElPropietario(jugador) && numCasas >= n) {
            numCasas -= n;
            derruidas = true;
        }

        return derruidas;
    }

    /*
    Método esEsteElPropietario
    Parametros:
     jugador: jugador del que queremos saber si es el propietario
    Devuelve:
      true si el jugador es el propietario de la propiedad, false en caso contrario
     */
    private Boolean esEsteElPropietario(Jugador jugador) {
        boolean esPropietario = false;
        if (jugador == propietario) {
            esPropietario = true;
        }
        return esPropietario;
    }

    /*
    Método getHipotecado
    Devuelve:
      el valor de hipotecado
     */
    public Boolean getHipotecado() {
        return hipotecado;
    }

    /*
    Método getImporteCancelarHipoteca
    Devuelve:
      El valor de cancelar una hipoteca
     */
    float getImporteCancelarHipoteca() {
        return getImporteHipoteca() * factorInteresesHipoteca;
    }

    /*
    Consultor de la hipoteca base
     */
    private float getImporteHipoteca() {
        return (float) (hipotecaBase * (1 + (numCasas*0.5) + (numHoteles*2.5)));
    }

    /*
    Consultor del nombre de la propiedad
     */
    public String getNombre() { //public para GUI
        return nombre;
    }

    /*
    Consultor del número de casas de la propiedad
     */
    public int getNumCasas() { //public para GUI
        return numCasas;
    }

    /*
    Consultor del número de hoteles de la propiedad
     */
    public int getNumHoteles() { //public para GUI
        return numHoteles;
    }
 
    /*
    Método getPrecioAlquiler
    Devuelve:
      el valor asociado al alquiler de una propiedad
     */
    private float getPrecioAlquiler() {
        return (float) (alquilerBase * (1 + (numCasas * 0.5) + (numHoteles * 2.5))); //Convertir double en float --> (float) <double>
    }

    /*
    Consultor del precio de compra de la propiedad
     */
    public float getPrecioCompra() { //Public para GUI
        return precioCompra;
    }

    /*
    Consultor del precio por edificar la propiedad
     */
    float getPrecioEdificar() {
        return precioEdificar;
    }

    /*
    Método getPrecioVenta
    Devuelve:
      el precio por vender la propiedad
     */
    private float getPrecioVenta() {
        return precioCompra + precioEdificar * (numCasas + 5 * numHoteles) * factorRevalorizacion;
    }

    /*
    Consultor del propietario de la propiedad
     */
    Jugador getPropietario() {
        return propietario;
    }

    /*
    Método hipotecar
    Parámetro:
      jugador: jugador que va a hipotecar la propiedad
    Devuelve:
      true si se puede hipotecar la propiedad por el jugador, false en caso contrario
    */
    boolean hipotecar(Jugador jugador) {
      boolean salida = false;
      if (!hipotecado && esEsteElPropietario(jugador)){
        jugador.recibe(getImporteHipoteca());
        hipotecado = true;
        salida = true;
      }
      return salida;
    }

    /*
    Método propietarioEncarcelado
    Devuelve:
      true si el propietario de la propiedad está encarcelado, false en caso contrario
     */
    private Boolean propietarioEncarcelado() {
        boolean encarcelado = false;
        if (tienePropietario() && propietario.isEncarcelado()) {
            encarcelado = true;
        }
        return encarcelado;
    }


    /*
    Método tienePropietario
    Devuelve:
      true si la propiedad tiene propietario, false en caso contrario
     */
    Boolean tienePropietario() {
        boolean tienePropietario = false;
        if (propietario != null) {
            tienePropietario = true;
        }
        return tienePropietario;
    }

    /**
     * Sobrecarga del metodo toString() de java
     */
    @Override
    public String toString() {
        return "TituloPropiedad{" + "Nombre=" + nombre + ", alquilerBase=" + alquilerBase + ", factorRevalorizacion=" + factorRevalorizacion + ", hipotecaBase=" + hipotecaBase + ", hipotecado=" + hipotecado +  ", numCasas=" + numCasas + ", numHoteles=" + numHoteles + ", precioCompra=" + precioCompra + ", precioEdificar=" + precioEdificar + ", propietario=" + propietario + '}';
    }

    /*
    Método tramitarAlquiler
    Parámetros:
      jugador: jugador que va a tramitar el alquiler
    Efecto:
      Si el jugador pasado como argumento no es el propietario de la propiedad paga el alquiler y el propietario recibe el dinero
     */
    void tramitarAlquiler(Jugador jugador) {
        if (tienePropietario() && !esEsteElPropietario(jugador)) {
            jugador.pagaAlquiler(getPrecioAlquiler());
            propietario.recibe(getPrecioAlquiler());
            Diario.getInstance().ocurreEvento("El jugador " + jugador.getNombre() + " paga " + getPrecioAlquiler() + "€ a " +
                propietario.getNombre() + " por el alquiler de la propiedad " + getNombre());
        }
    }

    /*
    Método vender
    Parámetros:
      jugador: jugador que va a vender la propiedad
    Efecto:
      Si el jugador es el propietario y no está hipotecado, recibe el precio de la venta, se desvincula de la propiedad y se eliminan las casas y hoteles
    Devuelve:
      true si se efectua la venta, false en caso contrario
     */
    Boolean vender(Jugador jugador) {
        boolean vendido = false;
        if (esEsteElPropietario(jugador) && !getHipotecado()) {
            propietario.recibe(getPrecioVenta());
            numCasas = 0;
            numHoteles = 0;
            propietario = null;
            vendido = true;
        }
        return vendido;
    }
}
