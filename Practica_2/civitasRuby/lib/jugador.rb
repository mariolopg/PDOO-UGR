# encoding:utf-8
module Civitas
  class Jugador

    attr_reader :CASAS_MAX
    attr_reader :CASAS_POR_HOTEL
    attr_reader :HOTELES_MAX
    attr_reader :nombre
    attr_reader :PRECIO_LIBERTAD
    attr_reader :puede_comprar
    attr_reader :num_casilla_actual
    attr_reader :PASO_POR_SALIDA

    #para constructor de copia:
    attr_accessor :encarcelado, :num_casilla_actual, :puede_comprar, :saldo, :salvoconducto, :propiedades
    
      @@CASAS_MAX = 4
      @@CASAS_POR_HOTEL = 4
      @@HOTELES_MAX = 4
      @@PASO_POR_SALIDA = 1000.0
      @@PRECIO_LIBERTAD = 200.0
      @@SALDO_INICIAL = 7500.0


    # Constructor con parametros
    #   Parametro: nombre = nombre del jugador
    def initialize(nombre)
      @nombre = nombre
      @encarcelado = false
      @num_casilla_actual = 0
      @puede_comprar = true
      @saldo = @@SALDO_INICIAL
      @salvoconducto = nil
      @propiedadades = Array.new
    end

#     public # para la prueba en civitas juego

    # def cancelar_hipoteca(ip)
      
    # end

    # Método cantidad_casas_hoteles Devuelve: El numero de casas y hoteles que tiene
    #   el jugador en total
    def cantidad_casas_hoteles
      num_casas_hoteles = 0
      for i in 0..propiedades.size-1
        num_casas_hoteles += @propiedadades.get(i).num_casas + @propiedadades.get(i).num_hoteles
      end
      return num_casas_hoteles
    end

    # def comprar(titulo)

    # end

    # def construir_casa(ip)

    # end

    # def construir_hotel(ip)

    # end

    # Metodo en_bancarrota 
    #   Devuelve: Si el jugador está o no en bancarrota
    def en_bancarrota
      bancarrota = false;
      if (@saldo < 0)
        bancarrota = true
      end
    end

    # Metodo encarcelar 
    #   Parametro: numCasillaCarcel = numero de casilla en el que se encuentra la carcel 
    #   Devuelve: true si el jugador ha sido encarcelado y false en caso contrario
    def encarcelar(num_casilla_carcel)
      if (debe_ser_encarcelado)
        mover_a_casilla(num_casilla_carcel)
        @encarcelado = true
        Diario.instance.ocurre_evento("El jugador #{@nombre} va a la Cárcel. ")
      end
      return @encarcelado
    end

    # Metodo existe_la_propiedad 
    #   Parametro: ip = indice de la propiedad en el vector
    #   Devuelve: true si el indice es válido y false en caso contrario
    def existe_la_propiedad(ip)
      return (ip>=0 && ip<@propiedadades.size)
    end

    # def hipotecar(ip)

    # end

    # Metodo modificar_saldo
    #   modifica el saldo del jugador sumandole la cantidad pasada como parametro
    def modificar_saldo(cantidad)
      @saldo += cantidad
      Diario.instance.ocurre_evento("El nuevo saldo de el jugador #{@nombre} es #{@saldo}.")
      return true
    end

    # Método mover_a_casilla 
    #   Mueve el jugador a la casilla de indice numCasilla
    def mover_a_casilla(num_casilla)
      if is_encarcelado
        es_movido = false
      else
        @num_casilla_actual = num_casilla 
        puede_comprar = false
        Diario.instance.ocurre_evento("El jugador #{nombre} se ha movido a la casilla #{num_casilla_actual}.")
        es_movido = true       
      end
      return es_movido
    end

    # Método obtenerSalvoconducto
    #   El jugador obtiene la carta sorpresa que evita la carcel
    def obtener_salvoconducto(sorpresa)
      obtenido = false    # Si esta encarcelado
      if (!is_encarcelado)
        obtenido = true
        @salvoconducto = sorpresa
      end
      return obtenido
    end

    # Método paga
    #   El jugador paga la cantida pasada como parámetro
    def paga(cantidad)
      return modificar_saldo(cantidad * (-1))
    end

    # Método paga_alquiler
    #   El jugador paga el alquiler (la cantidad pasada como parámetro)
    def paga_alquiler(cantidad)
      paga = false    # Si esta encarcelado
      if (!is_encarcelado)
        paga = paga(cantidad)
      end
      return paga
    end

    # Método paga_impuesto
    #   El jugador paga el impuesto (la cantidad pasada como parámetro)
    def paga_impuesto(cantidad)
      paga = false    # Si esta encarcelado
      if (!is_encarcelado)
        paga = paga(cantidad)
      end
      return paga
    end

    # Metodo pasa_por_salida
    #   El jugador cobra el premio por pasar por la salida
    def pasa_por_salida
      modificar_saldo(@@PASO_POR_SALIDA)
      Diario.instance.ocurre_evento("El jugador #{@nombre} recibe #{@@PASO_POR_SALIDA} por pasar por la Salida")
      return true
    end

    # Método perder_salvoconducto
    #   El jugador pierde la carta sopresa que evita la carcel
    def perder_salvoconducto
      @salvoconducto.usada()
      @salvoconducto = nil
    end

    # Método puede_comprar_casilla
    #   Informa de si el jugador puede compra casillas o no
    def puede_comprar_casilla
      if (is_encarcelado)
        @puede_comprar = false
      else
        @puede_comprar = true
      end
      return @puede_comprar
    end

    # Método puede_salir_carcel_pagando
    #   Devuelve true si tiene saldo suficiente para pagar la fianza, false en caso contrario
    def puede_salir_carcel_pagando
      return (@saldo >= @@PRECIO_LIBERTAD)
    end

    # Método puedo_edificar_casa
    #    Devuelve true si puede edificar una casa en la propiedad pasada como parametro, false en caso contrario
    def puedo_edificar_casa(propiedad)
      return (@propiedad.num_casas < @@CASAS_MAX && @saldo>=@propiedad.precio_edificar)
    end

    # Método puedo_edificar_hotel
    #   Devuelve true si puede edificar un hotel en la propiedad pasada como parametro, false en caso contrario
    def puedo_edificar_hotel(propiedad)
      return (@propiedad.num_hoteles < @@HOTELES_MAX && @saldo>=@propiedad.precio_edificar && @propiedad.num_casas == @@CASAS_MAX)
    end

    # Método puedo_gastar
    #   Devuelve true si el jugador puede o no gastar la cantidad pasada como parametro
    def puedo_gastar(precio)
      puedo = false   # Si esta encarcelado
      if (!is_encarcelado)
        puedo = @saldo >= precio
      end
      return puedo
    end

    # Método recibe
    #   El jugador recibe la cantidad pasada como parametro
    def recibe(cantidad)
      recibida = false    # Si esta encarcelado
      if (!is_encarcelado)
        recibida = modificar_saldo(cantidad)
      end
      return recibida
    end

    # Método salir_carcel_pagando
    #   Devuelve true si el jugador actual puede salir de la carcel pagando, false en caso contrario. 
    #   En caso afirmativo sale de la carcel
    def salir_carcel_pagando
      salir = false   # Si esta encarcelado o no puede salir pagando
      if (is_encarcelado && puede_salir_carcel_pagando)
        paga(@@PRECIO_LIBERTAD)
        @encarcelado = false
        Diario.instance.ocurre_evento("El jugador #{@nombre} paga #{@@PRECIO_LIBERTAD} por slair de la Cárcel. ")
        salir = true
      end
      return salir
    end

    # Método salir_carcel_tirando
    #   Devuelve true si el jugador actual puede salir de la carcel tirando, false en caso contrario. 
    #   En caso afirmativo sale de la carcel
    def salir_carcel_tirando
      salir = Dado.instance.salgo_de_la_carcel
      if salir
        @encarcelado = true
        Diario.instance.ocurre_evento("El jugador #{@nombre} ha sacado #{Dado.instance.ultimo_resultado}, por tanto, sale de la Cárcel.")
      end
      return salir
    end

    # Método tiene_algo_que_gestionar
    #   Informa de si el jugador tiene o no propiedades
    def tiene_algo_que_gestionar
      return (@propiedadades.size>=0)
    end

    # Método tiene_salvoconducto
    #   Informa de si el jugador tiene o no la carta para evitar la carcel
    def tiene_salvoconducto
      return (@salvoconducto != nil)
    end

    # Método vender
    #   El jugador vende la propiedad de indice ip
    def vender(ip)
      vendido = false   # Si no existe o si esta encarcelado
      if (!is encarcelado && existe_la_propiedad(ip))
          vendido = @propiedades.get(ip).vender(self)
          Diario.instance.ocurre_evento("El jugador #{@nombre} ha vendido la propiedad #{@propiedadades.get(ip).nombre}. ")
      end
      return false
    end

#----------------------------------------------

    public

    # Metodo compareTo Parmaetros: otro = jugador con el que se va a compara el que
    #   llama al metodo Devuelve: -1 si el saldo del jugador es menor que el de otro,
    #   0 si son iguales o 1 si el del jugador es mayor
    def <=> (otro)
      return self.get_saldo <=> otro.get_saldo
    end

    def is_encarcelado
      return @encarcelado
    end

    def to_s
      puts "Nombre: #{@nombre} "
    end

#----------------------------------------------

    protected

    # Constructor de copia
    #   Parametro: otro = objeto que va a ser copiado
    def self.copia(otro)
      nuevo = new (otro.nombre)
      nuevo.encarcelado = otro.encarcelado
      nuevo.num_casilla_actual = otro.num_casilla_actual
      nuevo.puede_comprar = otro.puede_comprar
      nuevo.saldo = otro.saldo
      nuevo.salvoconducto = otro.salvoconducto
      nuevo.propiedadades = otro.propiedadades
      return nuevo
    end

    # Metodo debeSerEncarcelado Devuelve: true si el jugador no esta encarcelado y
    #   no tiene salvoconducto false en caso contrario
    def debe_ser_encarcelado
      
      if self.is_encarcelado
        va_a_carcel = false
      elsif ( !tiene_salvoconducto() )
        va_a_carcel = true
      else 
        va_a_carcel = false
        perder_salvoconducto()
        Diario.instance.ocurre_evento("El jugador #{@nombre} se libra de la Cárcel usando la carta Salir Cárcel.")
      end

      return va_a_carcel

    end

    # Consultor del atributo propiedades
    def get_propiedades
      return @propiedadades
    end

    # Consultor del saldo del jugador
    def get_saldo
      return @saldo
    end





  end
end