# encoding:utf-8
 
module Civitas
  class Casilla 

    attr_reader :nombre

    # Constructor de casillas de tipo DESCANSO
    def initialize (nombre)
      @nombre = nombre
    end

    # Metodo informe
    #   Efecto:
    #     Informa al diario de que el jugador ha caido en la casilla que llama al metodo
    def informe(iactual, todos)
      evento = "El jugador #{todos[iactual].nombre} ha caido en la casilla #{@nombre}"
      Diario.instance.ocurre_evento(evento)
    end

    #  Metodo recibe_jugador (descanso)
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla recibe al jugador actual al que se le aplica el efecto de ésta 
    def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
      end
    end

    # Metodo jugadorCorrecto
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Devuelve:
    #     si el jugador de indice iacutal existe
    def jugador_correcto(iactual, todos)
      return (iactual >= 0 && iactual < todos.size)
    end

    # Sobrecarga del metodo to_s de Ruby
    def to_s
      return "Casilla Descanso -> Nombre: #{@nombre} "
    end
    
    #----------------------------------------------
    private :informe

  end

end
