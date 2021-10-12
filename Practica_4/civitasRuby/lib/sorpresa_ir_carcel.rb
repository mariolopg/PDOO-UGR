#encoding:utf-8

module Civitas
  class Sorpresa_ir_carcel < Sorpresa
    def initialize(tablero)
      super("Vas a la carcel. Muevéte a la casilla Carcel", -1, tablero)
    end 

    # Método aplicarAJugador (ir Carcel)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Envia al jugador actual a la cárcel
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(tablero.num_casilla_carcel())
      end
    end

    def to_s
      return "Sorpresa Ir Carcel: Texto: #{texto}, Tablero: #{tablero}"
    end

    public_class_method :new
    
  end
end
