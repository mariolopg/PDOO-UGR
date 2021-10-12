# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

# encoding:utf-8

module Civitas
  class Tablero

    attr_reader :num_casilla_carcel

    def initialize(n_cas_carcel)

      @num_casilla_carcel = 1
      if(n_cas_carcel >= 1)
        @num_casilla_carcel = n_cas_carcel
      end
      @casillas = Array.new
      casilla = Casilla.new("Salida")
      @casillas << casilla
      @por_salida = 0
      @tiene_juez = false

    end

    def to_s
      "Tablero: por salida #{@por_salida}, tiene juez #{@tiene_juez},  num casilla carcel #{@num_casilla_carcel}"
    end

    private

#    Metodo Correcto
#    Devuelve:
#      Si el tablero es correcto o no
#    Efecto:
#      El tablero es correcto si la posicion de la carcel está dentro del tablero y si tiene juez.

      def correcto
        ok = false
        if( @casillas.length > @num_casilla_carcel && @tiene_juez == true )
          ok = true
        end
        return ok
      end

#      Metodo correcto_cas
#        Parámetros:
#          numCasilla: indice de una Casilla
#        Devuelve:
#          Si el tablero es correcto o no
#        Efecto:
#          El tablero es correcto si la posicion de la carcel está dentro del tablero y si tiene juez.
#          Y si numCasilla es un indice válido ( 0 <= numCasilla < size ).

      def correcto_cas(num_casilla)
        ok = false
        if( correcto && num_casilla >= 0 && num_casilla < @casillas.length)
          ok  = true
        end

        return ok
      end

      public

#      Metodo get_por_salida
#        Devuelve:
#          Las veces que se ha pasado por la salida
#        Efecto:
#          Si el numero de veces es > 0 lo decrementa y devuelve el valor que tenía nates de ser decrementado.

      def get_por_salida
        psalida = @por_salida

        if(@por_salida > 0)
          @por_salida = @por_salida - 1
        end

        return psalida
      end

#      Metodo aniade_casilla
#        Parametros:
#          casilla: Casilla que se va a añadir al tablero
#        Efecto:
#          Añade la casilla al final del tablero.
#          Si la casilla se va a añadir en la posicion donde deberia ir la carcel,
#          se añade primero ésta.
#          Si al añadir la casilla la siguiente casilla es la carcel, la añade.

      def aniade_casilla(c)

        casilla = Casilla.new("Carcel")

        if(num_casilla_carcel == @casillas.length)
          @casillas << casilla
        end

          @casillas << c

        if(num_casilla_carcel == @casillas.length)
            @casillas << casilla
        end
      end

#      Metodo aniade_juez
#        Efecto:
#          Si el tablero no tiene casilla jue, la añade al final de éste.

      def aniade_juez

        casilla = Casilla.new("Juez")

        if(@tiene_juez == false)
          @casillas << casilla
          @tiene_juez = true
        end
      end

#      Metodo get_casilla
#        Parametros:
#          numCasilla: indice de la casilla que se quiere consultar
#        Devuelve:
#          La casilla de indice numCasilla
#        Efecto:
#          Si el indice es correcto devuelve la casilla de indice numCasilla
#        Excepción:
#          Si el indice no es corrrecto devuelve null.

      def get_casilla(n_casilla)
        if(correcto_cas(n_casilla))
          return @casillas[n_casilla]
        else
          return nil
        end
      end

#      Metodo nueva_posicion
#        Parametros:
#          actual: posicion actual del jugador
#          tirada: valor de la tirada del dado
#        Devuelve:
#          La nueva posicion del jugador
#        Efecto:
#          Calcula la casilla a la que tiene que moverse el jugador.
#        Excepción:
#          Su la posicion no es correcta devuelve -1.

     def nueva_posicion(actual, tirada)
        nueva_pos = -1

        if(correcto_cas(actual))
          nueva_pos = (actual+tirada) % @casillas.length
          if (nueva_pos != (actual + tirada))
            @por_salida+=1
          end
        end

        return nueva_pos

      end

#      Metodo calcular_tirada
#        Parametros:
#          origen: casilla en la que se encuentra el jugador
#          destino: casilla a la que se mueve el jugador
#        Devuelve:
#          La tirada que ha sacado el jugador para moverse a la casilla destino
#        Efecto:
#          Devuelve la tirada que se ha necesitado para ir de origen a destino.

     def calcular_tirada(origen, destino)
        tirada = destino - origen
        if (tirada < 0)
          tirada += @casillas.length
        end
        return tirada
     end

      end

  end
