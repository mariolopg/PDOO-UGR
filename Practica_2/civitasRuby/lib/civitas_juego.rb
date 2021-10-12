# encoding:utf-8

require_relative 'dado'
require_relative 'casilla'
require_relative 'diario'
require_relative 'estados_juego'
require_relative 'mazo_sorpresas'
require_relative 'sorpresa'
require_relative 'tablero'
require_relative 'tipo_casilla'
require_relative 'tipo_sorpresa'
require_relative 'jugador'
require_relative 'operaciones_juego'
require_relative 'titulo_propiedad'
require_relative 'gestor_estados'

module Civitas
  class Civitas_juego
    
    attr_reader :tablero, :mazo

    
    # Constructor con parametros
    #   Recibe como parametros los nombres de los jugadores
    def initialize(nombres)
      @jugadores = Array.new
      for i in 0..(nombres.size - 1)
        @jugadores << Jugador.new(nombres[i])
      end
      @gestor_estados = Gestor_estados.new
      @estado = @gestor_estados.estado_inicial
      @indice_jugador_actual = Dado.instance.quien_empieza(@jugadores.size)
      @mazo = Mazo_sorpresas.new
      @tablero = Tablero.new(5)
      inicializar_tablero(@mazo)
      inicializar_mazo_sorpresas(@tablero)
  end

  private

    # def avanza_jugador

    # end
#public # para la prueba en civitas juego
    # Contabiliza pasos por salida
    #   Contabiliza las veces que ha pasado el jugador actual pro la salida
    def contabilizar_pasos_por_salida(jugador_actual)
      while @tablero.get_por_salida > 0
        jugador_actual.pasa_por_salida
      end
    end

    
    # inicializar Mazo Sorpresas
    #   Inicializa el mazo sorpresas a partir del tablero. Crea todas las cartas y las añade al mazo
    def inicializar_mazo_sorpresas(tablero)

      #Creamos la Carta sorpresa pagar
      valor = rand(100..1000)
      @mazo.al_mazo(Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PAGARCOBRAR, -valor, "Debes pagar #{valor} euros"))

      #Creamos la Carta sorpresa cobrar
      valor = rand(100..1000)
      @mazo.al_mazo(Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PAGARCOBRAR, valor , "Ganas #{valor} euros"))

      #Creamos las Cartas ir casilla  (dos normales y una para la carcel)
      valor = rand(0..(@tablero.casillas.size - 1))
      while(valor == @tablero.num_casilla_carcel)
        valor = rand(0..@tablero.casillas.size)
      end
      @mazo.al_mazo(Sorpresa.crear_ir_casilla(Tipo_sorpresa::IRCASILLA, @tablero, valor, "Te mueves a la casilla #{@tablero.get_casilla(valor).nombre}"))

      valor = rand(0..(@tablero.casillas.size - 1))
      while(valor == @tablero.num_casilla_carcel)
        valor = rand(0..(@tablero.casillas.size - 1))
      end
      @mazo.al_mazo(Sorpresa.crear_ir_casilla(Tipo_sorpresa::IRCASILLA, @tablero, valor, "Te mueves a la casilla #{@tablero.get_casilla(valor).nombre}"))
      
      # Carta carcel
      @mazo.al_mazo(Sorpresa.crear_ir_casilla(Tipo_sorpresa::IRCASILLA, @tablero, @tablero.num_casilla_carcel, "Vas de visita a la carcel"))

      #Creamos las Cartas por casa hotel
      valor = rand(50..250)
      @mazo.al_mazo(Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PORCASAHOTEL, valor, "Ganas #{valor} euros por tus propiedades"))
      
      valor = rand(50..250)
      @mazo.al_mazo(Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PORCASAHOTEL, -valor, "Pagas #{valor} euros por tus propiedades"))

      #Creamos las Cartas por jugador
      valor = rand(50..300)
      @mazo.al_mazo(Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PORJUGADOR, valor, "Recibes #{valor} euros de cada jugador"))

      valor = rand(50..300)
      @mazo.al_mazo(Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PORJUGADOR, -valor, "Pagas #{valor} euros a cada jugador"))

      #Creamos la Carta Salir Carcel
      @mazo.al_mazo(Sorpresa.crear_salir_carcel(Tipo_sorpresa::SALIRCARCEL, @tablero))

      #Creamos la Carta ir Carcel
      @mazo.al_mazo(Sorpresa.crear_ir_carcel(Tipo_sorpresa::IRCARCEL, @tablero))

    end

    
    # inicializar tablero
    #   Crea el tablero y lo inicializa añadiendole todas las Casillas
    def inicializar_tablero(mazo)
        #Creamos las Casillas  
      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Moscu", 20 , 1.05, 100 , 200, 125)))

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Oslo", 60, 0.95, 150, 300, 125)))
      
      #Sorpresa
      @tablero.aniade_casilla(Casilla.casilla_sorpresa(@mazo, "Sorpresa"))

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Manila", 50, 0.5, 250, 500, 250)))
    
      #carcel se añade automaticamente

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Marsella", 70, 1.5, 300, 700, 250)))
      
      #sorpresa
      @tablero.aniade_casilla(Casilla.casilla_sorpresa(@mazo, "Sorpresa"))

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Palermo", 80, 0.85, 400, 960, 500)))
      
      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Berlin", 100, 1.15, 500, 1000, 500)))
      
      #Parking
      @tablero.aniade_casilla(Casilla.casilla_descanso("Descanso"))

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Helsinki", 120, 1.25, 600, 1200, 625)))
      
      
      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Lisboa", 130, 0.75, 650, 1300, 625)))
    
      #impuesto
      cantidad = rand(50..500)
      @tablero.aniade_casilla(Casilla.casilla_impuesto(cantidad, "Impuesto"))

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Estocolmo", 150, 0.8, 750, 1500, 750)))
      
      #Juez
      @tablero.aniade_juez()

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Denver", 170, 1.2, 850, 1700, 875)))
      

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Rio", 180, 1.1, 900, 1800, 875)))
      
      # sorpresa
      @tablero.aniade_casilla(Casilla.casilla_sorpresa(@mazo, "Sorpresa"))

      @tablero.aniade_casilla(Casilla.casilla_calle (Titulo_propiedad.new("Tokio", 200, 0.9, 1000, 20000, 1000)))
      
    end

    # ranking
    #   ordena a los jugadores en funcion de su saldo de mayor a menor
    def ranking
      @jugadores.sort{|b,a| a <=> b}
    end


  #----------------------------------------------

  public
  
    # pasar turno
    #   pasa el turno al siguiente jugador
    def pasar_turno
      @indice_jugador_actual = (@indice_jugador_actual + 1) % @jugadores.size
    end

    # Cancelar hipoteca
    #   Cancela la hipoteca de la propiedad ip del jugador actual
    def cancelar_hipoteca(ip)
      get_jugador_actual.cancelar_hipoteca(ip)
    end

    # def comprar

    # end

    # Construir casa
    #   Construye una casa en la propiedad ip del jugador actual
    def construir_casa
      get_jugador_actual.construir_casa
    end

    # Construir hotel
    #   Construye un hotel en la propiedad ip del jugador actual
    def construir_hotel
      get_jugador_actual.construir_hotel
    end

    # Final del juego
    #   Devuelve true si hay algun jugador en bancarrota, false en caso contrario
    def final_del_juego
      fin = false
      for i in 0...@jugadores.size
        if @jugadores[i].en_bancarrota
          fin = true
        end
      end
      return fin
    end

    # get casilla actual
    #   devuelve la casilla en la que se encuentra el jugador actual
    def get_casilla_actual
      indice_casilla = get_jugador_actual.num_casilla_actual
      return @tablero.get_casilla(indice_casilla)
    end

    # get jugador actual
    #   Devuelve el jugador que esta jugando en el momento
    def get_jugador_actual
      return @jugadores[@indice_jugador_actual]
    end

    # hipotecar
    #   hipoteca la propieddad ip del jugador actual
    def hipotecar
      get_jugador_actual.hipotecar;
    end

    # info jugador texto
    #   devuelve un string con la informacion del jugador actual
    def info_jugador_texto
      return get_jugador_actual.to_s
    end

    # salir carcel pagando
    #   Devuelve true si el jugador actual puede salir de la carcel pagando, false en caso contrario. 
    #   En caso afirmativo sale de la carcel
    def salir_carcel_pagando
      return get_jugador_actual.salir_carcel_pagando
    end

    # salir carcel tirando
    #   Devuelve true si el jugador actual puede salir de la carcel tirando, false en caso contrario.  
    #   En caso afirmativo sale de la carcel
    def salir_carcel_tirando
      return get_jugador_actual.salir_carcel_tirando
    end

    # def siguiente_paso

    # end

    # siguiente paso completado
    #   Se actualiza el estado del juego obteniendo el siguiente del gestor de estados
    def siguiente_paso_completado(operacion)
      gestorEstados.siguiente_estado(get_jugador_actual, @estado, operacion)
    end

    # vender
    #   Vende la propiedad ip del jugador actual
    def vender(ip)
      return get_jugador_actual.vender(ip)
    end

    #------------------------------ MAIN ------------------------------#

    jugadores = Array.new

    jugadores << "Evaristo"
    jugadores << "Eufrasio"

    juego = Civitas_juego.new(jugadores)

    puts "Tablero creado: #{juego.tablero}"
    puts "Mazo creado: #{juego.mazo}"

    puts "Info jugador: #{juego.info_jugador_texto}"
    juego.pasar_turno
    puts "Info jugador: #{juego.info_jugador_texto}"

    puts "Saldo: #{juego.get_jugador_actual.saldo}"
    juego.get_jugador_actual.encarcelar(juego.tablero.num_casilla_carcel)
    puts "Puede salir pagando: #{juego.salir_carcel_pagando}"
    puts "Saldo: #{juego.get_jugador_actual.saldo}"

    for i in 0..4
      puts "Puede salir pagando: #{juego.salir_carcel_tirando}"
    end
    puts "Saldo: #{juego.get_jugador_actual.saldo}"

    juego.get_jugador_actual.modificar_saldo(-7200)
    puts "Saldo: #{juego.get_jugador_actual.saldo}"
    puts "Puede salir pagando: #{juego.salir_carcel_pagando}"

    puts "Fin juego: #{juego.final_del_juego}"
    juego.get_jugador_actual.modificar_saldo(-101)
    puts "Fin juego: #{juego.final_del_juego}"

    puts juego.ranking.to_s

    puts "Jugador: #{juego.get_jugador_actual.nombre}"
    puts "Casilla actual: #{juego.get_casilla_actual}"
    juego.get_jugador_actual.mover_a_casilla(19)
    puts "Casilla actual: #{juego.get_casilla_actual}"
    tirada = Dado.instance.tirar
    juego.get_jugador_actual().mover_a_casilla(juego.tablero.nueva_posicion(19, tirada))
    puts "Casilla actual: #{juego.get_casilla_actual}"
    puts "Saldo: #{juego.get_jugador_actual.saldo}"
    juego.contabilizar_pasos_por_salida(juego.get_jugador_actual)
    puts "Jugador: #{juego.get_jugador_actual.nombre}"

  end
end
