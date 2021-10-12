# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Casilla_sorpresa < Casilla
    attr_reader :mazo, :sorpresa
    def initialize (mazo, nombre)
       super(nombre)
       @mazo = mazo
       @sorpresa = nil
    end

    #  Metodo recibe_jugador (sorpresa)
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo sorpresa recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual, todos)
        @sorpresa = @mazo.siguiente
        informe(iactual, todos)
        @sorpresa.aplicar_a_jugador(iactual, todos)
      end
    end

    def to_s
      return "Casilla Sorpresa -> Nombre: #{nombre}, Mazo: #{@mazo}, Sorpresa:  #{@sorpresa}"
    end

  end
end
