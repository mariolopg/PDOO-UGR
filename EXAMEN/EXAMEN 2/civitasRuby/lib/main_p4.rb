# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'jugador'
require_relative 'jugador_especulador'
require_relative 'diario'
require_relative 'titulo_propiedad'

module Civitas
  class Main_p4
    jugador = Jugador.new("Jugador1")
    propiedad = Titulo_propiedad.new("Moscu", 20 , 1.05, 100 , 200, 125)
    jugador.comprar(propiedad)

    puts jugador.inspect
    puts "Compra la propiedad"
    puts propiedad.inspect
    puts "**********************"

    jugador = Jugador_especulador.new(jugador, 1000)
    puts "Jugador especulador"
    puts jugador.inspect

    puts "Debe ser encarcelado"
    puts  jugador.debe_ser_encarcelado

  end
end
