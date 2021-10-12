# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Mazo_sorpresas

    def initialize(d = false)
      @debug = d
      @ultima_sorpresa
      init
      if(d)
        Diario.instance.ocurre_evento("Mazo sorpresas en modo debug")
      else
        Diario.instance.ocurre_evento("Mazo sorpresas en modo no debug")
      end
    end

    def to_s
      "Mazo Sorpresas: sorpresas = #{@sorpresas}, usadas = #{@usadas}, cartas especiales #{@cartas_especiales}, ultima sorpresa #{@ultima_sorpresa}, debug = #{@debug},  "
    end

  # Método init
  #   Efecto:
  #    Inicializa los atributos sorpresas, cartasEspeciales, barajadas y usadas.
    def init
      @sorpresas = Array.new
      @barajada = false
      @usadas = 0
      @cartas_especiales = Array.new
    end

#   Metodo al_mazo
#     Parametros:
#       s: nueva carta sorpresa.
#     Efecto:
#       Si el mazo no ha sido barajado se añade la carta s al mazo.
    def al_mazo(s)
      if(!@barajada)
        @sorpresas << s
      end
    end

  #   Metodo siguiente
  #      Devuelve:
  #        La siguiente carta de sorpresa del mazo
  #      Efecto:
  #        Baraja el mazo si no se ha hecho previamente, o si se ha llegado al final del mismo.
  #        Guarda la carta de sorpresa siguiente en la variable ultimaSorpresa y la pone al final del mazo.
  #        Devuelve la carta que se ha guardado en ultimaSorpresa

    def siguiente
      if(!@barajada || @usadas == @sorpresas.length)
        if (!@debug)
          @sorpresas = @sorpresas.shuffle
        end
        @usadas = 0
        @baradada = true
      end
      @usadas += 1
      @ultima_sorpresa = @sorpresas[0]
      @sorpresas.delete_at(0)
      @sorpresas << @ultima_sorpresa

      return @ultima_sorpresa

    end

#    Metodo inhabilitar_carta_especial
#      Parametros:
#        sorpresa: carta sorpresa a inhabilitar
#      Efecto:
#        Si la carta sorpresa se encuentra en el mazo de sorpresas la quita y la
#        añade a cartasEspeciales, dejando constancia en el diario.
#        En caso contrario no hace nada.

    def inhabilitar_carta_especial(sorpresa)
        pos = @sorpresas.index(sorpresa)
        if(pos != nil)
          @sorpresas.delete_at(pos)
          @cartas_especiales << sorpresa
          Diario.instance.ocurre_evento("Carta Sorpresa retirada del mazo")
        end
    end

#    Metodo habilitar_carta_especial
#      Parametros:
#        sorpresa: carta sorpresa que se va a habilitar
#      Efecto:
#        Si la carta sorpresa se encuentra en el mazo cartasEspeciales la quita y la
#        añade a sorpresas, dejando constancia en el diario.
#        En caso contrario no hace nada.

    def habilitar_carta_especial(sorpresa)
      pos = @cartas_especiales.index(sorpresa)
      if(pos != nil)
        @cartas_especiales.delete_at(pos)
        @sorpresas << sorpresa
        Diario.instance.ocurre_evento("Carta Sorpresa introducida en el mazo")
      end

    end
    
    #----------------------------------------------
    private :init

  end
end
