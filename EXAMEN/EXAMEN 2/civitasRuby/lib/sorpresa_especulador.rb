# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
 
module Civitas
  class Sorpresa_especulador < Sorpresa
    def initialize (fianza, texto)
      super(texto, fianza)
    end

    # Método aplicarAJugador (sorpresa especulador)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Envia al jugador actual a la nueva casilla
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual] = Jugador_especulador.new(todos[actual], valor)
      end
    end

    def to_s
      return "Sorpresa Especulador ->  { Texto: #{texto}, Fianza #{valor} }"
    end

    public_class_method :new

  end
end
