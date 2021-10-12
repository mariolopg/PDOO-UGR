# encoding:utf-8
 
module Civitas
  class Sorpresa
    
    attr_reader :texto, :valor, :tablero
    
    def initialize(texto, valor=-1, tablero=nil)
      @texto = texto
      @valor = valor
      @tablero = tablero
    end
    
    def aplicar_a_jugador(iactual, todos)
    end

    # Método informe
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Informa al diario que se está aplicando una sorpresa al jugador actual
    def informe(actual, todos)
      Diario.instance.ocurre_evento("Se esta aplicando una sorpresa al jugador #{todos[actual].nombre}")
    end

    # Método jugador_correcto
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Return:
    #   Devuelve true si el jugador es correcto
    def jugador_correcto(actual, todos)
      return (actual>=0 && actual<todos.size)
    end

    #----------------------------------------------
     protected :informe, :texto, :valor, :tablero
     private_class_method :new
    
  end
end
