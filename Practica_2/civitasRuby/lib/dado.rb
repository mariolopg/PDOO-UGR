# encoding:utf-8

require 'singleton'

module Civitas
  class Dado
    attr_reader :ultimo_resultado # Consultor del Último resultado

    include Singleton


    def initialize
      @random = Random.new
      @ultimo_resultado = 0
      @debug = false
      @salida_carcel = 5
    end
    
    def to_s
      "Dado: random = #{@random}, ultimo resultado = #{@ultimo_resultado}, debug = #{@salida_carcel}"
    end

#  Método Tirar
#    Devuelve:
#      Un numero entre 1 y 6 correspondiente a la tirada del dado
#    Efecto:
#      Si modo debug esta activado devuelve 1.
#      Si no debuelve un numero aleatorio entre 1 y 6

    def tirar
      @ultimo_resultado=1   # inicializamos a uno (modo debug)

      if (!@debug)          # si no está en modo debug cambiamos el resultado por uno aleatorio entre 1 y 6
        @ultimo_resultado=@random.rand(1..6)
      end

      return @ultimo_resultado
    end

#  Método Salgo de la carcel
#    Devuelve:
#      Si se puede o no salir de la cárcel.
#    Efecto:
#      Tira el dado, si la tirada es 5 o 6 devuelve true.
#      En caso contrario devuelve false.

    def salgo_de_la_carcel
      salgo=false           # inicializamos a false (no sale de la carcel)

      if ( tirar() >= 5 )   # si la tirda es 5 o 6 sale de la carcel
        salgo=true
      end

      return salgo
    end


#  Método Quien empieza
#    Parámetros:
#      n: número de jugadores
#    Devuelve:
#      El índice del jugador que empieza.
#    Efecto:
#      Devuelve un número aleatorio entre 0 y n-1.

    def quien_empieza(n)
      return @random.rand(n)     # devuelve un numpero aleatorio entre 0 y n-1
    end

#  Modificador de Debug
#    Parámetros:
#      d: nuevo valor de debug (true o false)
#    Efecto:
#      Pone en debug el valor de d y deja constancia del cambio en el diario

    def set_debug(d)
      @debug = d
      if (d)
        Diario.instance.ocurre_evento("Dado en modo debug")
      else
        Diario.instance.ocurre_evento("Dado en modo no debug")
      end
    end


  end
end
