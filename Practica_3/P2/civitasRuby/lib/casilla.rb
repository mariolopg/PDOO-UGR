# encoding:utf-8
 
module Civitas
  class Casilla

    attr_reader :nombre, :titulo_propiedad

    @@carcel = 1

    # Constructor de casillas de tipo DESCANSO
    def initialize (nombre, tipo, importe = 0.0, titulo_propiedad=nil, num_casilla_carcel=1, mazo=nil)
      @nombre = nombre
      @tipo = tipo
      @importe = importe
      @mazo = mazo
      @titulo_propiedad = titulo_propiedad
      if num_casilla_carcel != 1
        @@carcel = num_casilla_carcel
      end
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
      new(nombre, Tipo_casilla::SORPRESA , 0.0, nil, 1, mazo)
    end

    # Metodo informe
    #   Efecto:
    #     Informa al diario de que el jugador ha caido en la casilla que llama al metodo
    def informe(iactual, todos)
      evento = "El jugador #{todos[iactual].nombre} ha caido en la casilla #{@nombre}"
      Diario.instance.ocurre_evento(evento)
    end

    #  Metodo recibe_jugador
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla recibe al jugador actual al que se le aplica el efecto de ésta 
    def recibe_jugador(iactual, todos)
      case @tipo 
      when Tipo_casilla::CALLE
        recibe_jugador_calle(iactual,todos)
      when Tipo_casilla::IMPUESTO
        recibe_jugador_impuesto(iactual,todos)
      when Tipo_casilla::JUEZ
        recibe_jugador_juez(iactual,todos)
      when Tipo_casilla::SORPRESA
        recibe_jugador_sorpresa(iactual,todos)
      else 
        informe(iactual, todos)
      end
    end
 
    #  Metodo recibe_jugador_calle
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo calle recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador_calle(iactual, todos)
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

    #  Metodo recibe_jugador_impuesto
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo impuesto recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador_impuesto(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        todos[iactual].paga_impuesto(@importe)
        Diario.instance.ocurre_evento("El jugador #{todos[iactual].nombre} paga un impuesto de #{@importe}€. ")
      end
    end

    #  Metodo recibe_jugador_juez
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo juez recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador_juez(iactual, todos)
      if jugador_correcto(iactual, todos)
        informe(iactual, todos)
        todos[iactual].encarcelar(@@carcel)
      end
    end

    #  Metodo recibe_jugador_sorpresa
    #   Parámetros:
    #     iactual = indice del jugador actual
    #     todos = Lista con todos los jugadores
    #   Efecto: 
    #     La casilla de tipo sorpresa recibe al jugador actual al que se le aplica el efecto de ésta 
    #     y se informa al diario.
    def recibe_jugador_sorpresa(iactual, todos)
      if jugador_correcto(iactual, todos)
        @sorpresa = @mazo.siguiente
        informe(iactual, todos)
        @sorpresa.aplicar_a_jugador(iactual, todos)
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
    
    #----------------------------------------------
    private :informe, :recibe_jugador_calle,  :recibe_jugador_impuesto,  :recibe_jugador_juez,  :recibe_jugador_sorpresa

  end

end
