# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Casilla_calle < Casilla 
    attr_reader :titulo_propiedad
    def initialize (titulo)
      super(titulo.nombre)
      @titulo_propiedad = titulo
      @importe = titulo_propiedad.precio_compra
    end 

    #  Metodo recibe_jugador_calle
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo calle recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        jugador = todos[iactual]
        if !@titulo_propiedad.tiene_propietario
          jugador.puede_comprar_casilla
        else
          @titulo_propiedad.tramitar_alquiler(jugador)
        end
      end
    end

    def to_s
      return "Casilla Calle -> Nombre: #{nombre}, Titulo: #{@titulo}, Importe: #{@importe}"
    end

  end
end
