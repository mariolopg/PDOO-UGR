#encoding:utf-8

module Civitas
  class Casilla_impuesto < Casilla
    attr_reader :importe
    def initialize (cantidad, nombre)
       super(nombre)
       @importe = cantidad
    end
    
    #  Metodo recibe_jugador (impuesto)
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo impuesto recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        todos[iactual].paga_impuesto(@importe)
        Diario.instance.ocurre_evento("El jugador #{todos[iactual].nombre} paga un impuesto de #{@importe}€. ")
      end
    end

    # Sobrecarga del metodo to_s
    def to_s
      "Casilla Impuesto -> Nombre: #{nombre}, Cantidad: #{@importe}"
    end

  end
end
