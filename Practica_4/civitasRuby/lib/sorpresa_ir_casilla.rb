# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Sorpresa_ir_casilla < Sorpresa
    def initialize(valor, texto, tablero)
       super(texto, valor, tablero)
    end

    # Método aplicarAJugador (ir casilla)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Envia al jugador actual a la nueva casilla
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        casilla_actual = todos[actual].num_casilla_actual
        tirada = tablero.calcular_tirada(casilla_actual, valor)
        nueva_pos = tablero.nueva_posicion(casilla_actual, tirada)
        todos[actual].mover_a_casilla(nueva_pos)
        tablero.get_casilla(nueva_pos).recibe_jugador(actual, todos)
      end
    end

    def to_s
      return "Sorpresa Ir Carcel -> Texto: #{texto}, Valor: #{valor}, Tablero: #{tablero}"   
    end

    public_class_method :new

  end
end
