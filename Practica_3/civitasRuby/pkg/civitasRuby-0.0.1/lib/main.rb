# encoding:utf-8



require_relative "dado"
require_relative "casilla"
require_relative "diario"
require_relative "estados_juego"
require_relative "mazo_sorpresas"
require_relative "sorpresa"
require_relative "tablero"
require_relative "tipo_casilla"
require_relative "tipo_sorpresa"

module Civitas

  cont1= 0
  cont2= 0
  cont3= 0
  cont4 = 0

  for i in (0..99)

    aux = Dado.instance.quien_empieza(4)

    if(aux == 0)
      cont1 += 1
    end

    if(aux == 1)
      cont2 += 1
    end

    if(aux == 2)
      cont3 += 1
    end

    if(aux == 3)
      cont4 += 1
    end
  end

  puts "Prueba probabilidades método quien_empieza: "
  puts " "

  puts "La probabilidad de que salga el jugador numero 1: #{cont1}%"
  puts "La probabilidad de que salga el jugador numero 2: #{cont2}%"
  puts "La probabilidad de que salga el jugador numero 3: #{cont3}%"
  puts "La probabilidad de que salga el jugador numero 4: #{cont4}%"

  puts " "
  puts "------------------------------------------------------------------"
  puts " "

  puts "Prueba en modo debug dado:"
  puts " "
  puts "DADO EN MODO DEBUG:"

  #Prueba modo debug dado
  Dado.instance.set_debug(true)

  for i in (0..9)
    Dado.instance.tirar
    print "#{Dado.instance.ultimo_resultado} "
  end

  puts " "
  puts "DADO EN MODO NO DEBUG:"

  Dado.instance.set_debug(false)

  for i in (0..9)
    Dado.instance.tirar
    print "#{Dado.instance.ultimo_resultado} "
  end

  puts " "
  puts "------------------------------------------------------------------"
  puts " "

  puts "Prueba ultimo_resultado y salgo_de_la_carcel:"
  puts " "

  for i in (0..9)
    if(Dado.instance.salgo_de_la_carcel)
      puts "Tirada: #{Dado.instance.ultimo_resultado} --> Sales de la Cárcel"
    else
      puts "Tirada: #{Dado.instance.ultimo_resultado} --> Te quedas en la Cárcel"
    end
  end

  puts " "
  puts "------------------------------------------------------------------"
  puts " "

  puts "Prueba Enumerados:"
  puts " "

  puts "Tipos Casilla: #{TipoCasilla::CALLE}, #{TipoCasilla::SORPRESA}"
  puts "Tipos Sorpresa: #{TipoSorpresa::PAGARCOBRAR}, #{TipoSorpresa::IRCARCEL}"
  puts "Estados Juego: #{Estados_juego::INICIO_TURNO}, #{Estados_juego::DESPUES_CARCEL}"

  puts " "
  puts "------------------------------------------------------------------"
  puts " "

  puts "Prueba mazo_sorpresas:"
  puts " "

  mazo = MazoSorpresas.new

  s1 = Sorpresa.new
  s2 = Sorpresa.new

  mazo.al_mazo(s1)
  mazo.al_mazo(s2)

  s3 = mazo.siguiente

  mazo.inhabilitar_carta_especial(s2)
  mazo.habilitar_carta_especial(s2)

  puts "Contenido de Diario: "

  while Diario.instance.eventos_pendientes
    puts Diario.instance.leer_evento
  end

  puts " "
  puts "------------------------------------------------------------------"
  puts " "

  puts "Prueba Tablero: "
  puts " "
  puts "TABLERO INCORRECTO"
  puts " "

  puts "Casilla carcel en posicion incorrecta: "

  tablero_incorrecto = Tablero.new(50)

  puts "Añadimos casillas al tablero"

  for i in (1..8)
    if(i == tablero_incorrecto.num_casilla_carcel)
      i+=1
    end
    casilla = Casilla.new("Casilla #{i}")
    tablero_incorrecto.aniade_casilla(casilla)
  end

  tablero_incorrecto.aniade_juez

  puts "Consultamos una casilla, nos debe devolver nil, ya que no hay carcel"

  if (tablero_incorrecto.get_casilla(0)==nil)
    puts "El Tablero es incorrecto"
  end

  puts "······································"
  puts "No añadimos juez: "

  tablero_incorrecto = Tablero.new(5)

  puts "Añadimos casillas al tablero"

  for i in (1..8)
    if(i == tablero_incorrecto.num_casilla_carcel)
      i+=1
    end
    casilla = Casilla.new("Casilla #{i}")
    tablero_incorrecto.aniade_casilla(casilla)
  end

  
  puts "Consultamos una casilla, nos debe devolver nil, ya que no hay juez"

  if (tablero_incorrecto.get_casilla(0)==nil)
    puts "El Tablero es incorrecto"
  end

  
    puts "······································"
    puts "Consultamos una casilla inexistente: "

    tablero_incorrecto = Tablero.new(5)

    puts "Añadimos casillas al tablero"

    for i in (1..8)
      if(i == tablero_incorrecto.num_casilla_carcel)
        i+=1
      end
      casilla = Casilla.new("Casilla #{i}")
      tablero_incorrecto.aniade_casilla(casilla)
    end
    tablero_incorrecto.aniade_juez

    puts "Consultamos una casilla, nos debe devolver nil, ya que no existe"

    if (tablero_incorrecto.get_casilla(390)==nil)
      puts "La casilla no existe"
    end

  puts ""

  puts "TABLERO CORRECTO: "

  tablero = Tablero.new(5)

  for i in (1..8)
    if(i == tablero.num_casilla_carcel)
      i+=1
    end
    casilla = Casilla.new("Casilla #{i}")
    tablero.aniade_casilla(casilla)
  end

  tablero.aniade_juez

  player_pos = 0
  new_player_pos = 0
  num_vueltas = 0
  preso = false

  puts "Comienza el juego en posición: #{player_pos}"
  puts "Casilla Actual: #{tablero.get_casilla(player_pos).nombre} "

  for i in (0..15)

    puts "······································"

    if(player_pos == tablero.num_casilla_carcel && preso)
      puts "Estás en la cárcel"
      if (Dado.instance.salgo_de_la_carcel)
        puts "Has sacado #{Dado.instance.ultimo_resultado}"
        puts ". Sales de la cárcel"
        new_player_pos = tablero.nueva_posicion(player_pos, Dado.instance.tirar)
        preso = false
        puts "Tirada: #{tablero.calcular_tirada(player_pos, new_player_pos)}"
        puts "Nueva Posición: #{new_player_pos}"
        puts "Casilla Actual: #{tablero.get_casilla(new_player_pos).nombre}"

      else
        puts "Has sacado #{Dado.instance.ultimo_resultado}"
        puts ". Sigues en la cárcel"
        new_player_pos = player_pos

      end

    else
      new_player_pos = tablero.nueva_posicion(player_pos, Dado.instance.tirar)



      puts "Tirada: #{tablero.calcular_tirada(player_pos, new_player_pos)}"
      puts "Nueva Posición: #{new_player_pos}"
      puts "Casilla Actual: #{tablero.get_casilla(player_pos).nombre}"

    end

    if(tablero.get_casilla(new_player_pos).nombre == "Juez")
      new_player_pos = tablero.num_casilla_carcel
      puts "Vas a la cárcel"
      preso = true
    end

    player_pos = new_player_pos

    if(tablero.get_por_salida > 0)
      num_vueltas += 1
    end

    puts "Vuelta: #{num_vueltas}"


  end


end
