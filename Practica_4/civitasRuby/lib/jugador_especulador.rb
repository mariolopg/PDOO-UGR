# encoding:utf-8
module Civitas
  class Jugador_especulador < Jugador
    @@FACTOR_ESPECULADOR = 2
    def initialize(otro, fianza)
      super(otro.nombre)
      copia(otro)
      @fianza=fianza
      for i in 0..propiedades.size-1
        propiedades[i].actualiza_propietario_por_conversion(self)
      end 
    end
    
   # Metodo debeSerEncarcelado 
   # Devuelve: true si el jugador no esta encarcelado y
   #   no tiene salvoconducto false en caso contrario
    def debe_ser_encarcelado
       va_a_carcel = super
       
      if va_a_carcel
        if (puedo_gastar(@fianza))
          modificar_saldo(-@fianza)
          va_a_carcel = false
          Diario.instance.ocurre_evento("El jugador #{nombre} paga #{@fianza}€ de fianza para evitar la carcel. ")
        end
      end

      return va_a_carcel

    end
    
    # Consultor de CASAS_MAX
    def CASAS_MAX
      return @@CASAS_MAX * @@FACTOR_ESPECULADOR
    end
    
    # Consultor de HOTELES_MAX
    def HOTELES_MAX
      return @@HOTELES_MAX * @@FACTOR_ESPECULADOR
    end


    # Método paga_impuesto
    #   El jugador paga el impuesto (la cantidad pasada como parámetro)
    def paga_impuesto(cantidad)
      paga = false    # Si esta encarcelado
      if (!is_encarcelado)
        paga = paga(cantidad/2)
      end
      return paga
    end

    def to_s
      return "Jugador Especulador -> Nombre: #{nombre}, saldo: #{saldo}, numero casilla actual: #{num_casilla_actual}, puede comprar: #{puede_comprar}, encarcelado: #{encarcelado}, propiedades: #{propiedades}, fianza: #{@fianza}"
    end
    
  end
end
