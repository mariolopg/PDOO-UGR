#encoding:utf-8

module Civitas
  class Casilla_juez < Casilla
    @@carcel = 1

    def initialize (num_casilla_carcel, nombre)
       super(nombre)
       @@carcel = num_casilla_carcel
    end

    #  Metodo recibe_jugador
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo juez recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        todos[iactual].encarcelar(@@carcel)
      end
    end

    # Sobrecarga del metodo to_s de Ruby
    def to_s
      return "Casilla Juez -> Nombre: #{nombre}, Tipo: #{@tipo}, Número Cárcel: #{@@carcel}"
    end
  end
end
