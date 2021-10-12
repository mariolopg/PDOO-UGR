# encoding:utf-8

module Civitas
  class Sorpresa
    
    
    def initialize(tipo, valor, texto, tablero=nil,  mazo=nil)
      init
      @tipo=tipo
      @tablero = tablero
      @valor = valor 
      @texto = texto
      @mazo = mazo
    end

    # Constructor con parámetros
    # Crea una sorpresa de tipo IRCARCEL
    # Parametros:
    #   Recibe como parámetros los valores de los atributos: tipo y tablero
    def self.crear_ir_carcel(tipo,tablero)
      new(tipo, -1,  "Vas a la carcel. Muevéte a la casilla Carcel", tablero)
    end
    
    # Constructor con parámetros
    # Crea una sorpresa de tipo IRCASILLA
    # Parametros:
    #   Recibe como parámetros los valores de los atributos: tipo, tablero, valor y texto
    def self.crear_ir_casilla(tipo, valor, texto, tablero)
      new(tipo, valor, texto, tablero)
    end

    # Constructor con parámetros
    # Crea una sorpresa de tipo SALIRCARCEL
    # Parametros:
    #   Recibe como parámetros los valores de los atributos: tipo y tablero    
    def self.crear_salir_carcel(tipo, mazo)
      new(tipo, -1, "Te libras de la carcel. Si caes en la carcel y tienes esta carta puedes salir de ella.", nil, mazo)
    end

    # Constructor con parámetros
    # Crea una sorpresa para el resto de sorpresas
    # Parametros:
    #   Recibe como parámetros los valores de los atributos: tipo, valor y texto
    def self.crear_otras_sorpresas(tipo, valor, texto)
      new(tipo,valor,texto)
    end

    # Método aplicarAJugador()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Aplica al jugador la sorpresa correspondiente dependiendo del tipo
    def aplicar_a_jugador(actual, todos)

      case @tipo
      when Tipo_sorpresa::IRCASILLA
        aplicar_a_jugador_ir_a_casilla(actual, todos)
      when Tipo_sorpresa::IRCARCEL
        aplicar_a_jugador_ir_carcel(actual, todos)
      when Tipo_sorpresa::PAGARCOBRAR
        aplicar_a_jugador_pagar_cobrar(actual,todos)
      when Tipo_sorpresa::PORCASAHOTEL
        aplicar_a_jugador_por_casa_hotel(actual,todos)
      when Tipo_sorpresa::PORJUGADOR
        aplicar_a_jugador_por_jugador(actual,todos)
      when Tipo_sorpresa::SALIRCARCEL
        aplicar_a_jugador_salir_carcel(actual, todos)
      end

    end

    # Método aplicarAJugador_irACasilla()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Envia al jugador actual a la nueva casilla
    def aplicar_a_jugador_ir_a_casilla(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        casilla_actual = todos[actual].num_casilla_actual
        tirada = @tablero.calcular_tirada(casilla_actual, @valor)
        nueva_pos = @tablero.nueva_posicion(casilla_actual, tirada)
        todos[actual].mover_a_casilla(nueva_pos)
        @tablero.get_casilla(nueva_pos).recibe_jugador(actual, todos)
      end
    end

    # Método aplicarAJugador_irCarel()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Envia al jugador actual a la cárcel
    def aplicar_a_jugador_ir_carcel(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual].encarcelar(@tablero.num_casilla_carcel())
      end
    end

    # Método aplicarAJugador_pagarCobrar()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   El jugador paga o cobra el valor indicado en la carta
    def aplicar_a_jugador_pagar_cobrar(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        todos[actual].modificar_saldo(@valor)
      end
    end

    # Método aplicarAJugador_porCasaHotel()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Modifica el saldo del jugador con el valor de la sorpresa multiplicado por el numero de casas y hoteles del jugador
    def aplicar_a_jugador_por_casa_hotel(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        saldo = @valor*(todos[actual].cantidad_casas_hoteles)
        todos[actual].modificar_saldo(saldo)
      end
    end

    # Método aplicarAJugador_porJugador()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   El jugador actual recibe dinero de todos los jugadores
    def aplicar_a_jugador_por_jugador(actual, todos)
      if jugador_correcto(actual, todos)
        informe(actual, todos)
        pagar = Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PAGARCOBRAR, @valor*-1, "Los jugadores pagan #{@valor} al jugador #{todos[actual].nombre}")
        cobrar = Sorpresa.crear_otras_sorpresas(Tipo_sorpresa::PAGARCOBRAR, @valor*(todos.size - 1), "El jugador #{todos[actual].nombre} recibe #{@valor} del resto de jugadores")
        
        cobrar.aplicar_a_jugador_pagar_cobrar(actual,todos)
        for i in (0..todos.size)
          if (i!=actual)
            pagar.aplicar_a_jugador_pagar_cobrar(i,todos)
          end           
        end
      end
    end

    # Método aplicarAJugador_salirCarcel()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Si nadie tiene la carta para salir de la carcel, la obtiene el jugador actual
    def aplicar_a_jugador_salir_carcel(actual, todos)
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


    # Método aplicarAJugador_salirCarcel()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Efecto:
    #   Informa al diario que se está aplicando una sorpresa al jugador actual
    def informe(actual, todos)
      Diario.instance.ocurre_evento("Se esta aplicando una sorpresa al jugador #{todos[actual].nombre}")
    end

    # Método init()
    # Efecto:
    #   Punto de partida en los constructores
    def init
      @valor = -1
      @mazo = nil
      @tablero = nil
      @texto = " "
    end

    # Método salirMazo
    # Efecto:
    #   Si la sorpresa es la que inhabilita la cárcel, inhabilita la carta en el mazo
    def salir_del_mazo
      if (@tipo == Tipo_sorpresa::SALIRCARCEL)
        @mazo.inhabilitar_carta_especial(self)
      end
    end

    # Método usada()
    # Efecto:
    #   Si la sorpresa es la que inhabilita la cárcel, habilita la carta en el mazo
    def usada
      if (@tipo == Tipo_sorpresa::SALIRCARCEL)
        @mazo.habilitar_carta_especial(self)
      end
    end

    # Método aplicarAJugador_salirCarcel()
    # Parámetros:
    #   actual: indice del jugador actual
    #   todos: array que contiene los jugadores
    # Return:
    #   Devuelve true si el jugador es correcto
    def jugador_correcto(actual, todos)
      return (actual>=0 && actual<todos.size)
    end

    def to_s
      return "Sorpresa de tipo: #{@tipo}, Texto: #{@texto}"
    end

     #----------------------------------------------
     private :aplicar_a_jugador_ir_a_casilla, :aplicar_a_jugador_ir_carcel, :aplicar_a_jugador_pagar_cobrar, :aplicar_a_jugador_por_casa_hotel, :aplicar_a_jugador_por_jugador, :aplicar_a_jugador_por_jugador, :aplicar_a_jugador_salir_carcel, :informe, :init
    
  end
end
