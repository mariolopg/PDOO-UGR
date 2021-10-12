
# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas 
  class Sorpresa_por_jugador < Sorpresa
    def initialize(valor, texto)
      super(texto,valor)
    end

    # Método aplicarAJugado (por jugador)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   El jugador actual recibe dinero de todos los jugadores
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        pagar = Sorpresa_pagar_cobrar.new(valor*-1, "Los jugadores pagan #{valor} al jugador #{todos[actual].nombre}")
        cobrar = Sorpresa_pagar_cobrar.new(valor*(todos.size - 1), "El jugador #{todos[actual].nombre} recibe #{valor} del resto de jugadores")
        
        cobrar.aplicar_a_jugador(actual,todos)
        for i in (0..todos.size)
          if (i!=actual)
            pagar.aplicar_a_jugador(i,todos)
          end           
        end
      end
    end

    def to_s
      return "Sorpresa Por Jugador -> Texto: #{texto}, Valor: #{valor}"
    end

    public_class_method :new

  end
end
