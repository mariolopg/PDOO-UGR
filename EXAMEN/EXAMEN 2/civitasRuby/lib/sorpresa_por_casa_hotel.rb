# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Sorpresa_por_casa_hotel < Sorpresa
    def initialize(valor, texto)
       super(texto, valor)
    end

    # Método aplicarAJugador (PorCasaHotel)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Modifica el saldo del jugador con el valor de la sorpresa multiplicado por el numero de casas y hoteles del jugador
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        saldo = valor*(todos[actual].cantidad_casas_hoteles)
        todos[actual].modificar_saldo(saldo)
      end
    end

    def to_s
      return "Sorpresa Por Casa Hotel -> Texto: #{texto}, Valor: #{valor}"
    end

    public_class_method :new

  end
end
