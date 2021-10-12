# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Sorpresa_pagar_cobrar < Sorpresa
    def initialize (valor, texto)
      super(texto, valor)
    end

    
    # Método aplicarAJugado (pagar cobrar)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   El jugador paga o cobra el valor indicado en la carta
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual].modificar_saldo(valor)
      end
    end

    def to_s
      return "Sorpresa Pagar Cobrar -> Texto: #{texto}, Valor: #{valor}"
    end

    public_class_method :new

  end
end
