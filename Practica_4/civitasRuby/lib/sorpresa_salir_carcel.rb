# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas 
  class Sorpresa_salir_carcel < Sorpresa
    def initialize(mazo)
      super("Te libras de la carcel. Si caes en la carcel y tienes esta carta puedes salir de ella.")
      @mazo = mazo
    end

    # Método aplicarAJugador (salir carcel)
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Si nadie tiene la carta para salir de la carcel, la obtiene el jugador actual
    def aplicar_a_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)

        alguien_tiene = false
        i=0
        while (i<todos.size && !alguien_tiene)
          if (todos[i].tiene_salvoconducto() )
            alguien_tiene = true 
          end
          i+=1        
        end

        if (!alguien_tiene)
          todos[actual].obtener_salvoconducto(self)
          salir_del_mazo
        end
      end
    end

    # Método salirMazo
    # Efecto:
    #   Si la sorpresa es la que inhabilita la cárcel, inhabilita la carta en el mazo
    def salir_del_mazo
        @mazo.inhabilitar_carta_especial(self)
    end

    # Método usada()
    # Efecto:
    #   Si la sorpresa es la que inhabilita la cárcel, habilita la carta en el mazo
    def usada
        @mazo.habilitar_carta_especial(self)
    end

    def to_s 
      return "Sorpesa Salir Carcel -> Texto: #{texto}, Mazo: #{@mazo} "
    end

    public_class_method :new

  end
end
