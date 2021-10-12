#encoding:utf-8
require_relative 'operaciones_juego'
require_relative 'salidas_carcel'
require_relative 'respuestas'
require_relative 'gestiones_inmobiliarias'
require 'io/console'

module Juego_texto

  class Vista_textual
  include Civitas
    
    attr_reader :ipropiedad, :igestion

    def mostrar_estado(estado)
      puts estado
    end

    def pausa
      print "Pulsa una tecla "
      STDIN.getch
      print "\n"
    end

    def lee_entero(max,msg1,msg2)
      ok = false
      begin
        print msg1
        cadena = gets.chomp
        begin
          if (cadena =~ /\A\d+\Z/)
            numero = cadena.to_i
            ok = true
          else
            raise IOError
          end
        rescue IOError
          puts msg2
        end
        if (ok)
          if (numero >= max)
            ok = false
          end
        end
      end while (!ok)

      return numero
    end


    def menu(titulo,lista)
      tab = "  "
      puts titulo
      index = 0
      lista.each { |l|
        puts tab+index.to_s+"-"+l
        index += 1
      }

      opcion = lee_entero(lista.length,
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo")
      return opcion
    end

    def salir_carcel
      opcion = menu("Elige la forma para intentar salir de la carcel", 
                    Array(["Pagando","Tirando el dado"]) )
      return (Lista_salidas_carcel[opcion]) 
    end

    def comprar
      opcion = menu("¿Deseas comprar #{@juego_model.get_casilla_actual.nombre}?",
                    Array(["Si", "No"]))
      return(Lista_respuestas[opcion])
    end

    def gestionar
      @igestion = menu("¿Qué gestión desea realizar?",
        Array(["Vender", "Hipotecar", "Cancelar hipoteca", "Construir casa", "Construir Hotel", "Hacer propiedad sostenible" , "Terminar"]))
      if @igestion != 6
        nombres_propiedades = Array.new
        for i in 0..(@juego_model.get_jugador_actual.propiedades.size - 1)
          nombres_propiedades << @juego_model.get_jugador_actual.propiedades[i].to_s
        end
        @ipropiedad =  menu("¿Sobre qué propiedad?", nombres_propiedades)
       end
    end

    def mostrar_siguiente_operacion(operacion)
      puts "La siguiente operacion que se va a realizar es #{operacion}"
    end

    def mostrar_eventos
      while (Diario.instance.eventos_pendientes)
          puts Diario.instance.leer_evento
      end

    end

    def set_civitas_juego(civitas)
         @juego_model=civitas
         self.actualizar_vista
    end

    def actualizar_vista
      puts "Jugador actual: #{@juego_model.info_jugador_texto}"
      puts "Casilla actual: #{@juego_model.get_casilla_actual.to_s}"
    end

    
  end

end
