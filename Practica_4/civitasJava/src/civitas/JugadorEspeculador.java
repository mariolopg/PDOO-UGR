/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author mariolopezgonzalez
 */
public class JugadorEspeculador extends Jugador {
    
    private static final int FactorEspeculador = 2;
    private float fianza;

    public JugadorEspeculador(Jugador otro, float fianza) {
        super(otro);
        this.fianza = fianza;
        for (int i=0; i<getPropiedades().size(); i++){
            getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
            
    }


  /**
   * Metodo debeSerEncarcelado 
   *  Devuelve: 
   *    true si el jugador no esta encarcelado y no tiene salvoconducto false en caso contrario
   */
  @Override
  protected boolean debeSerEncarcelado() {
    Boolean vaACarcel = super.debeSerEncarcelado();
    if (vaACarcel){
      if (puedoGastar(fianza)){
        modificarSaldo(-fianza);
        vaACarcel=false;
        Diario.getInstance().ocurreEvento("El jugador " + getNombre() + " paga " + fianza + "€ para evitar la cárcel.");
      }
    }
    
    return vaACarcel;
  }
 
  /**
   * Método pagaImpuesto
   *  El jugador paga el impuesto (la cantidad pasada como parámetro)
   */
  @Override
  boolean pagaImpuesto(float cantidad) {
    Boolean pagaCantidad = false;
    if (!isEncarcelado()) {
      pagaCantidad = paga(cantidad/2);
    }
    return pagaCantidad;
  }

    /**
   * Consultor del atributo HotelMax
   */
  @Override
  protected int getHotelesMax() {
    return HotelesMax*FactorEspeculador;
  }
  
    /**
   * Consultor del atributo CasasMax
   */
  @Override
  protected int getCasasMax() {
    return CasasMax*FactorEspeculador;
  }

  @Override
  public String toString() {
      return "JugadorEspeculador{" + "encarcelado=" + encarcelado + ", nombre=" + super.getNombre() + ", numCasillaActual=" + super.getNumCasillaActual()
      + ", puedeComprar=" + super.getPuedeComprar() + ", saldo=" + super.getSaldo() + ", fianza=" + fianza + '}';
  }
  
  
    
}
