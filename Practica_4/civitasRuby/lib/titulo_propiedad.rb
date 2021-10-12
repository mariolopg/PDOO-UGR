# encoding:utf-8
 

module Civitas

  class Titulo_propiedad

    @@factor_intereses_hipoteca = 1.1

    attr_reader :hipotecado, :nombre, :num_casas,
                :num_casas, :num_hoteles, :precio_compra, :precio_edificar,
                :propietario

    # Constructor con parámetros
    #  Parametros:
    #    Recibe como parámetros los valores de los atributos: nombre, alquilerBase, factorRevalorizacion, hipotecaBase, precioCompra, precioEdificar
        
    def initialize(nom, ab, fr, hb, pc, pe)
      @alquiler_base = ab
      @factor_revalorizacion = fr
      @hipoteca_base = hb
      @hipotecado = false
      @nombre = nom
      @num_casas = 0
      @num_hoteles = 0
      @precio_compra = pc
      @precio_edificar = pe
      @propietario = nil
    end

    # Método actualizaPropietarioPorConversion
    #  Parametros:
    #    Recibe un jugador
    #  Efecto:
    #   El propietario de la propiedad se convierte en el jugador pasado como parámetro

    def actualiza_propietario_por_conversion(jugador)
      @propietario = jugador
    end

    # Método cancelarhipoteca
    #  Efecto:
    #   Cancea la hipoteca  del título que llama al metodo
    #  Devuelve:
    #   Si se ha podido cancelar o no
    def cancelar_hipoteca(jugador)
      result = false
      if (@hipotecado)
        if (es_este_el_propietario(jugador))
          jugador.paga(get_importe_cancelar_hipoteca)
          @hipotecado = false
          result = true
        end
      end
      return result
    end

    # Método cantidadCasasHoteles
    # Devuelve:
    #   El número de casas más hoteles
    def cantidad_casas_hoteles
      return @num_casas + @num_hoteles
    end

    # Metodo comprar
    #   Parámetro
    #     jugador = jugador que va a comprar este titulo
    #   Devuelve:
    #     Si el jugador ha podido o no comprar el título
    def comprar(jugador)
      result = false
      if !tiene_propietario
          @propietario = jugador
          result = true
          @propietario.paga(@precio_compra)
      end
      return result
    end

    # Parámetro
    #      jugador = jugador que va a construir una casa en esta propiedad
    #    Devuelve:
    #      Si el jugador ha podido o no construir la casa
    def construir_casa(jugador)
      result = false
      
      if es_este_el_propietario(jugador)
        @propietario.paga(@precio_edificar)
        @num_casas += 1
        result = true
      end
      return result
    end
 
    # Parámetro
    #      jugador = jugador que va a construir un hotel en esta propiedad
    #    Devuelve:
    #      Si el jugador ha podido o no construir el hotel
    def construir_hotel(jugador)
      result = false
      if es_este_el_propietario(jugador)
        @propietario.paga(@precio_edificar)
        @num_hoteles += 1
        result = true
      end
      return result
    end


    # Método derruir Casas
    # Parametros:
    #  n: numero de casas a derruir
    #  jugador: que va a derruir las casas
    # Efecto:
    #   Si el jugador es el propietario de la propiedad, se decrementa el numero de casas en n unidades
    # Devuelve:
    #   true si se derruyen casas, false en caso contrario
    def derruir_casas(n, jugador)
      derruidas = false
      if es_este_el_propietario(jugador) && @num_casas >= n
        @num_casas -= n
        derruidas = true
      end
      return derruidas
    end

    # Método esEsteElPropietario
    # Parametros:
    #  jugador: jugador del que queremos saber si es el propietario
    # Devuelve:
    #   true si el jugador es el propietario de la propiedad, false en caso contrario
    def es_este_el_propietario(jugador)
      es_propietario = false
      if jugador == @propietario
        es_propietario = true
      end
      return es_propietario
    end

    # Método getImporteCancelarHipoteca
    # Devuelve:
    #   El valor de cancelar una hipoteca
    def get_importe_cancelar_hipoteca
      return @hipoteca_base * @@factor_intereses_hipoteca
    end

    # Método getImporteHipotecar
    # Devuelve:
    #   El valor de realizar una hipoteca
    def get_importe_hipotecar
      return @alquiler_base * (1 + @num_casas*0.5 + @num_hoteles*2.5)
    end

    # Método getPrecioAlquiler
    # Devuelve:
    #   el valor asociado al alquiler de una propiedad
    def get_precio_alquiler
      return @alquiler_base * (1 + @num_casas*0.5 + @num_hoteles*2.25)
    end 

    # Método getPrecioVenta
    # Devuelve:
    #   el precio por vender la propiedad
    def get_precio_venta
      return @precio_compra + @precio_edificar*(@num_casas + 5*@num_hoteles)*@factor_revalorizacion
    end
 
    # Método hipotecar
    # Parámetro:
    #   jugador: jugador que va a hipotecar la propiedad
    # Devuelve:
    #   true si se puede hipotecar la propiedad por el jugador, false en caso contrario
    def hipotecar (jugador)
      salida = false
      if (!@hipotecado && es_este_el_propietario(jugador))
        jugador.recibe(@hipoteca_base)
        @hipotecado = true
        salida = true
      end
      return salida
    end

    # Método propietarioEncarcelado
    # Devuelve:
    #   true si el propietario de la propiedad está encarcelado, false en caso contrario
    def propietario_encarcelado
      encarcelado = false
      if tiene_propietario
        encarcelado = @propietario.encarcelado
      end
      return encarcelado
    end

    # Método tienePropietario
    # Devuelve:
    #   true si la propiedad tiene propietario, false en caso contrario
    def tiene_propietario
      tiene = false
      if @propietario != nil
        tiene = true
      end
      return tiene
    end

    # Método tramitarAlquiler
    # Parámetros:
    #   jugador: jugador que va a tramitar el alquiler
    # Efecto:
    #   Si el jugador pasado como argumento no es el propietario de la propiedad paga el alquiler y el propietario recibe el dinero
    def tramitar_alquiler(jugador)
      if tiene_propietario && !(es_este_el_propietario(jugador))
        jugador.paga_alquiler(get_precio_alquiler)
        @propietario.recibe(get_precio_alquiler)
        Diario.instance.ocurre_evento("El Jugador #{jugador.nombre} paga #{get_precio_alquiler}€ a #{@propietario.nombre} por el alquiler de la propiedad #{@nombre}")
      end
    end

    # Método vender
    # Parámetros:
    #   jugador: jugador que va a vender la propiedad
    # Efecto:
    #   Si el jugador es el propietario y no está hipotecado, recibe el precio de la venta, se desvincula de la propiedad y se eliminan las casas y hoteles
    # Devuelve:
    #   true si se efectua la venta, false en caso contrario
    def vender(jugador)
      vendido = false
      if es_este_el_propietario(jugador) && !@hipotecado
        vendido = true
        @propietario.recibe(get_precio_venta)
        @propietario = nil
        @num_casas = 0
        @num_hoteles = 0
      end
      return vendido
    end

    def to_s
      return "Titulo propiedad: nombre = #{@nombre}, alquiler base = #{@alquiler_base}, factor revalorizacion = #{@factor_revalorizacion}, hipoteca base = #{@hipoteca_base}, hipotecado = #{@hipotecado}, numero casas = #{@num_casas}, numero hoteles = #{@num_hoteles}, precio compra = #{@precio_compra}, precio edificar = #{@precio_edificar}"
    end

     # -----------------------------------------
     private :es_este_el_propietario, :get_importe_hipotecar, :get_precio_alquiler, :get_precio_venta, :propietario_encarcelado

  end
end
