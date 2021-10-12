# encoding:utf-8

module Civitas
  class Casilla

    attr_reader :nombre, :titulo_propiedad

    @@carcel = 1

    private

    # Constructor de casillas de tipo DESCANSO
    def initialize (nombre, tipo, importe = 0.0, titulo_propiedad=nil, num_casilla_carcel=1, mazo=nil)
      @nombre = nombre
      @tipo = tipo
      @importe = importe
      @mazo = mazo
      @titulo_propiedad = titulo_propiedad
      @@carcel = num_casilla_carcel
      @sorpresa = nil
    end

    def self.casilla_descanso(nombre)
      new(nombre, Tipo_casilla::DESCANSO)
    end
    
    # Constructor de casillas de tipo CALLE
    def self.casilla_calle(titulo)
      new(titulo.nombre, Tipo_casilla::CALLE, titulo.precio_compra, titulo)
    end

    # Constructor de casillas de tipo IMPUESTO
    def self.casilla_impuesto(cantidad, nombre)
      new(nombre, Tipo_casilla::IMPUESTO, cantidad)
    end

    # Constructor de casillas de tipo JUEZ
    def self.casilla_juez(num_casilla_carcel, nombre)
      new(nombre, Tipo_casilla::JUEZ , 0.0, nil, num_casilla_carcel) 
    end

    # Constructor de casillas de tipo SORPRESA
    def self.casilla_sorpresa(mazo, nombre)
      new(nombre, Tipo_casilla::SORPRESA , 0.0, nil, nil, mazo)
    end

    # Metodo informe
    #   Efecto:
    #     Informa al diario de que el jugador ha caido en la casilla que llama al metodo
    def informe(iactual, todos)
      evento = "El jugador #{todos.get(iactual).nombre} ha caido en la casilla #{this}"
      Diario.instance.ocurre_evento(evento)
    end

    # def recibe_jugador(iactual, todos)

    # end

    # def recibe_jugador_calle(iactual, todos)

    # end

    #  Metodo recibe_jugador_impuesto
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     El jugador paga el importe del impuesto y se informa de ello al diario
    def recibe_jugador_impuesto(iactual, todos)
      if correcto(iactual, todos)
        informe(iactual, todos)
        todos.get(iactual).paga_impuesto(@importe)
      end
    end

    # Metodo recibe_jugador_juez
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     El jugador es encarcelado y se informa de ello al diario
    def recibe_jugador_juez(iactual, todos)
      if correcto(iactual, todos)
        informe(iactual, todos)
        todos.get(iactual).encarcelar(@@carcel)
      end
    end

    # def recibe_jugador_sorpresa(iactual, todos)

    # end



    public

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
      case @tipo 
      when Tipo_casilla::SORPRESA
        mensaje = "Nombre: #{@nombre}, Tipo: #{@tipo}, Mazo: #{@mazo}"
      when Tipo_casilla::CALLE
        mensaje = "Nombre: #{@nombre}, Tipo: #{@tipo}, Importe: #{@importe}"
      when Tipo_casilla::DESCANSO
        mensaje = "Nombre: #{@nombre}, Tipo: #{@tipo}"
      when Tipo_casilla::JUEZ
        mensaje = "Nombre: #{@nombre}, Tipo: #{@tipo}, Número Cárcel: #{@@carcel}"
      when Tipo_casilla::IMPUESTO
        mensaje = "Nombre: #{@nombre}, Tipo: #{@tipo}, Número Cárcel: #{@importe}"
      end
    end

  end

end
