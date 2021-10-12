 
package civitas;

import java.util.ArrayList;

/** @author Marina Muñoz Cano y Mario López González */
public class Jugador implements Comparable<Jugador> {

  // Atributos de clase constantes
  protected static final int CasasMax = 4;
  protected static final int CasasPorHotel = 4;
  protected static final int HotelesMax = 4;
  protected static final float PasoPorSalida = 1000;
  protected static final float PrecioLibertad = 200;
  private static final float SaldoInicial = 7500;

  // Atributos de instancia
  protected boolean encarcelado;
  private String nombre;
  private int numCasillaActual;
  private boolean puedeComprar;
  private float saldo;

  // Atributos de referencia
  private ArrayList<TituloPropiedad> propiedades;
  private Sorpresa salvoconducto; // tipo salir carcel

  /**
   * Constructor con parametros 
   *  Parametro: 
   *    nombre = nombre del jugador
   */
  Jugador(String nombre) {
    this.nombre = nombre;
    encarcelado = false;
    numCasillaActual = 0;
    saldo = SaldoInicial;
    puedeComprar = true;
    propiedades = new ArrayList<TituloPropiedad>();
  }

  /**
   * Constructor de copia 
   *  Parametro: 
   *    otro = objeto que va a ser copiado
   */
  protected Jugador(Jugador otro) {
    this.nombre = otro.nombre;
    this.encarcelado = otro.encarcelado;
    this.numCasillaActual = otro.numCasillaActual;
    this.saldo = Jugador.SaldoInicial;
    this.puedeComprar = otro.puedeComprar;
  }

  /** 
   * Método cancelarhipoteca
     Efecto:
      Cancela la hipoteca de la propiedad ip del que llama al metodo
     Devuelve:
      Si se ha podido cancelar o no 
   */ 
  boolean cancelarHipoteca(int ip) {
    boolean result = false;
    if (encarcelado)
      return result;

    if (existeLaPropiedad(ip)) {
      TituloPropiedad propiedad = propiedades.get(ip);
      float cantidad = propiedad.getImporteCancelarHipoteca();
      boolean puedoGastar = puedoGastar(cantidad);
      if (puedoGastar) {
        result = propiedad.cancelarHipoteca(this);
        if (result)
          Diario.getInstance()
              .ocurreEvento("El jugador " + nombre + " cancela la hipoteca de la propiedad " + propiedad.getNombre());
      }
    }

    return result;
  }

  /**
   * Método cantidadCasasHoteles 
   *  Devuelve: El numero de casas y hoteles que tiene
   *  el jugador en total
   */
  int cantidadCasasHoteles() {
    int numCasasHoteles = 0;
    for (int i = 0; i < propiedades.size(); i++)
      numCasasHoteles += propiedades.get(i).getNumCasas() + propiedades.get(i).getNumHoteles();
    return numCasasHoteles;
  }

  /**
   * Metodo compareTo 
   *  Parametros: 
   *    otro = jugador con el que se va a compara el que llama al metodo 
   * Devuelve: 
   *  -1 si el saldo del jugador es menor que el de otro, 0 si son iguales o 1 si el del jugador es mayor
   */
  public int compareTo(Jugador otro) {
    return Float.compare(getSaldo(), otro.getSaldo());
  }

  /**
   * Metodo comprar
   *  Parámetro
   *    titulo = titulo que se va a comprar
   *  Devuelve:
   *    Si el jugador ha podido o no comprar el título
   */
  boolean comprar(TituloPropiedad titulo) {
    boolean result = false;
    if (encarcelado)
      return result;
    
    if (puedeComprar){
      float precio = titulo.getPrecioCompra();
      if (puedoGastar(precio)){
        result =titulo.comprar(this);
        if (result){
          propiedades.add(titulo);
          Diario.getInstance().ocurreEvento("El jugador " + nombre + " compra la propiedad " + titulo.getNombre());
        }
           puedeComprar = false;
      }
    }

    return result;

  }

  /**
   * Método construirCasa
   *  Parámetro:
   *    ip = indice de la propiedad
   *  Efecto:
   *    Construye una casa en la propiedad de indice ip
   *  Devuelve:
   *    Si se ha podido construir el hotel o no. 
   */
  boolean construirCasa(int ip) {
    boolean result = false, puedoEdificarCasa = false;
    if(encarcelado)
      return result;
  
      if(existeLaPropiedad(ip)){
        TituloPropiedad propiedad = propiedades.get(ip);
        puedoEdificarCasa = puedoEdificarCasa(propiedad);
        
          if(puedoEdificarCasa)
            result = propiedad.construirCasa(this);
          
          if(result)
            Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " construye casa en la propoiedad " + propiedad.getNombre());
      }
    

    return result;

  }

  /**
   * Método construirHotel
   *  Parámetro:
   *    ip = indice de la propiedad
   *  Efecto:
   *    Construye un hotel en la propiedad de indice ip
   *  Devuelve:
   *    Si se ha podido construir el hotel o no.
   */
  boolean construirHotel(int ip) {
    boolean result = false;
    if(encarcelado)
      return result;
    if(existeLaPropiedad(ip)){
      TituloPropiedad propiedad = propiedades.get(ip);
      boolean puedoEdificarHotel = puedoEdificarHotel(propiedad);

      if(puedoEdificarHotel){
        result = propiedad.construirHotel(this);
        int casasPorHotel = getCasasPorHotel();
        propiedad.derruirCasas(casasPorHotel, this);
        Diario.getInstance().ocurreEvento("El jugador " + nombre + " construye hotel en la propiedad " + ip);
      }

    }
    return result; 
  }

  /**
   * Metodo debeSerEncarcelado 
   *  Devuelve: 
   *    true si el jugador no esta encarcelado y no tiene salvoconducto false en caso contrario
   */
  protected boolean debeSerEncarcelado() {
    Boolean vaACarcel = true;
    if (isEncarcelado())
      vaACarcel = false;
    else if (tieneSalvoconducto()) {
      vaACarcel = false;
      perderSalvoconducto();
      Diario.getInstance()
          .ocurreEvento("El jugador " + getNombre() + " se libra de la cárcel usando la carta Salir Cárcel");
    }

    return vaACarcel;

  }

  /**
   * Metodo enBancarrota 
   *  Devuelve: 
   *    Si el jugador está o no en bancarrota
   */
  boolean enBancarrota() {
    Boolean enBancarrota = false;
    if (getSaldo() < 0)
      enBancarrota = true;
    return enBancarrota;
  }

  /**
   * Metodo encarcelar 
   * Parametro: 
   *  numCasillaCarcel = numero de casilla en el que se encuentra la carcel 
   * Devuelve: 
   *  true si el jugador ha sido encarcelado y false en caso contrario
   */
  boolean encarcelar(int numCasillaCarcel) {
    if (debeSerEncarcelado()) {
      moverACasilla(numCasillaCarcel);
      encarcelado = true;
      Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " va a la cárcel");
    }
    return encarcelado;
  }

  /**
   * Metodo existeLaPropiedad
   * Parametro: 
   *  ip = indice de la propiedad en el vector
   * Devuelve: true si el indice es válido y false en caso contrario
   */
  private boolean existeLaPropiedad(int ip) {
    return (ip >= 0 && ip < propiedades.size());
  }

  /**
   * Consultor del atributo CasasMax
   */
  private int getCasasMax() {
    return CasasMax;
  }

  /**
   * Metodo getCasasPorHotel 
   *  Devuelve:
   *  el numero de casas que hacen falta para poder construir un hotel
   */
  int getCasasPorHotel() {
    return CasasPorHotel;
  }

  /**
   * Consultor del atributo HotelMax
   */
  private int getHotelesMax() {
    return HotelesMax;
  }

  /**
   * Consultor del nombre del jugador
   */
  protected String getNombre() {
    return nombre;
  }

  /**
   * Consultor del numero de casilla actual del jugador
   */
  public int getNumCasilaActual() {
    return numCasillaActual;
  }

  /**
   * Consultor del precio de libertad
   */
  private float getPrecioLibertad() {
    return PrecioLibertad;
  }

  /**
   * Consultor del premio del paso por salida
   */
  private float getPremioPasoPorSalida() {
    return PasoPorSalida;
  }

  /**
   * Consultor del atributo propiedades
   */
  public ArrayList<TituloPropiedad> getPropiedades() {
    return propiedades;
  }

  /**
   * Consultor del atributo puede comprar
   */
  boolean getPuedeComprar() {
    return puedeComprar;
  }
 
  /**
   * Consultor del saldo del jugador
   */
  protected float getSaldo() {
    return saldo;
  }
 
  /*
  Método hipotecar
    Parámetro:
      ip: propiedad que va a ser hipotecada
    Devuelve:
      true si se puede hipotecar la propiedad por el jugador, false en caso contrario
  */
  boolean hipotecar(int ip) {
    boolean result = false;
    if (encarcelado)
      return result;

    if (existeLaPropiedad(ip)){
      TituloPropiedad propiedad = propiedades.get(ip);
      result = propiedad.hipotecar(this);
    
      if (result)
        Diario.getInstance().ocurreEvento("El jugador " + nombre + " hipoteca la propiedad " + propiedad.getNombre());
    }
    
    return result;
  }

  /**
   * Informa de si el jugador esta encarcelado
   */
  public boolean isEncarcelado() {
    return encarcelado;
  }

  /**
   * Metodo modificarSaldo 
   *  modifica el saldo del jugador sumandole la cantidad pasada como parametro
   */
  boolean modificarSaldo(float cantidad) {
    saldo += cantidad;
    Diario.getInstance().ocurreEvento("El nuevo saldo del jugador " + getNombre() + " es " + getSaldo());
    return true;
  }

  /**
   * Método moverACasilla 
   *   Mueve el jugador a la casilla de indice numCasilla
   */
  boolean moverACasilla(int numCasilla) {
    Boolean esMovido = true;
    if (isEncarcelado())
      esMovido = false;
    else {
      numCasillaActual = numCasilla;
      puedeComprar = false;
      Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " se ha movido a la casilla " + getNumCasilaActual());
    }
    return esMovido;
  }

  /**
   * Método obtenerSalvoconducto 
   *    El jugador obtiene la carta sorpresa que evita la carcel
   */
  boolean obtenerSalvoconducto(Sorpresa sorpresa) {
    Boolean obtenido = false;
    if (!isEncarcelado()) {
      obtenido = true;
      salvoconducto = sorpresa;
    }
    return obtenido;
  }

  /**
   * Método paga 
   *  El jugador paga la cantidad pasada como parámetro
   */
  boolean paga(float cantidad) {
    return modificarSaldo(cantidad * (-1));
  }

  /**
   * Método pagaAlquiler 
   *  El jugador paga el alquiler (la cantidad pasada como parámetro)
   */
  boolean pagaAlquiler(float cantidad) {
    Boolean pagaCantidad = false;
    if (!isEncarcelado()) {
      pagaCantidad = paga(cantidad);
    }
    return pagaCantidad;
  }

  /**
   * Método pagaImpuesto
   *  El jugador paga el impuesto (la cantidad pasada como parámetro)
   */
  boolean pagaImpuesto(float cantidad) {
    Boolean pagaCantidad = false;
    if (!isEncarcelado()) {
      pagaCantidad = paga(cantidad);
    }
    return pagaCantidad;
  }

  /**
   * Metodo pasaPorSalida 
   *   El jugador cobra el premio por pasar por la salida
   */
  boolean pasaPorSalida() {
    modificarSaldo(getPremioPasoPorSalida());
    Diario.getInstance()
        .ocurreEvento("El jugador " + getNombre() + " recibe " + getPremioPasoPorSalida() + " por pasar por la Salida");
    return true;
  }

  /**
   * Método perderSalvoconducto 
   *  El jugador pierde la carta sopresa que evita la carcel
   */
  private void perderSalvoconducto() {
    salvoconducto.usada();
    salvoconducto = null;
  }

  /**
   * Método puedeComprarCasilla 
   *  Informa de si el jugador puede compra casillas o no
   */
  boolean puedeComprarCasilla() {
    puedeComprar = true;
    if (isEncarcelado()) {
      puedeComprar = false;
    }
    return puedeComprar;
  }

  /**
   * Método puedeSalirCarcelPagando
   *  Devuelve 
   *    true si tiene saldo suficiente para pagar la fianza, false en caso contrario
   */
  private boolean puedeSalirCarcelPagando() {
    return (getSaldo() >= getPrecioLibertad());
  }

  /**
   * Método puedoEdificarCasa 
   *  Devuelve true si puede edificar una casa en la propiedad pasada como parametro, false en caso contrario
   */
  private boolean puedoEdificarCasa(TituloPropiedad propiedad) {
    boolean puedoEdificarCasa = false;
    float precio = propiedad.getPrecioEdificar();
    if ( puedoGastar(precio) && propiedad.getNumCasas() < getCasasMax() )
      puedoEdificarCasa = true;
    return puedoEdificarCasa;
  }

  /**
   * Método puedoEdificarHotel 
   *  Devuelve true si puede edificar un hotel en la propiedad pasada como parametro, false en caso contrario
   */
  private boolean puedoEdificarHotel(TituloPropiedad propiedad) {
    boolean puedoEdificarHotel = false;
    float precio = propiedad.getPrecioEdificar();
    if (puedoGastar(precio) && propiedad.getNumHoteles() < getHotelesMax() && propiedad.getNumCasas() >= getCasasPorHotel())
      puedoEdificarHotel = true;
    return puedoEdificarHotel;
  }

  /**
   * Método puedoGastar
   *  Devuelve
   *    true si el jugador puede o no gastar la cantidad pasada como parametro
   */
  private boolean puedoGastar(float precio) {
    return (!isEncarcelado() && getSaldo() >= precio);
  }

  /**
   * Método recibe
   *  El jugador recibe la cantidad pasada como parametro
   */
  boolean recibe(float cantidad) {
    Boolean recibe = false;
    if (!isEncarcelado()) {
      recibe = modificarSaldo(cantidad);
    }
    return recibe;
  }

  /**
   * Método salirCarcelPagando
   *  Devuelve
   *    true si el jugador actual puede salir de la carcel pagando, false en caso contrario. En caso afirmativo sale de la carcel
   */
  boolean salirCarcelPagando() {
    Boolean salir = false;
    if (isEncarcelado() && puedeSalirCarcelPagando()) {
      salir = true;
      paga(getPrecioLibertad());
      Diario.getInstance()
          .ocurreEvento("El jugador " + getNombre() + " paga " + getPrecioLibertad() + " para salir de la cárcel");
    }
    return salir;
  }

  /**
   * Método salirCarcelTirando 
   *  Devuelve 
   *    true si el jugador actual puede salir de la carcel tirando, false en caso contrario. En caso afirmativo sale de la carcel
   */
  boolean salirCarcelTirando() {
    Boolean salir = false;
    if (Dado.getInstance().salgoDeLaCarcel()) {
      encarcelado = false;
      salir = true;
      Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " ha sacado "
          + Dado.getInstance().getUltimoResultado() + " para salir de la cárcel");
    }
    return salir;
  }

  /**
   * Método tieneAlgoQueGestionar
   *  Informa de si el jugador tiene o no propiedades
   */
  boolean tieneAlgoQueGestionar() {
    return (propiedades.size() > 0);
  }

  /**
   * Método tieneSalvoconducto
   *  Informa de si el jugador tiene o no la carta para evitar la carcel
   */
  boolean tieneSalvoconducto() {
    return (salvoconducto != null);
  }

  @Override
  public String toString() {
    return "Jugador{" + "encarcelado=" + encarcelado + ", nombre=" + nombre + ", numCasillaActual=" + numCasillaActual
        + ", puedeComprar=" + puedeComprar + ", saldo=" + saldo + '}';
  }

  /**
   * Método vender 
   *  El jugador vende la propiedad de indice ip
   */
  boolean vender(int ip) {
    Boolean vendido = false;
    if (!isEncarcelado() && existeLaPropiedad(ip)) {
      vendido = propiedades.get(ip).vender(this);
      Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " ha vendido la propiedad " + propiedades.get(ip).getNombre());
      propiedades.remove(ip);
    }
    return vendido;
  }
}
