# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'casilla'
require_relative 'civitas_juego'
require_relative 'controlador'
require_relative 'dado'
require_relative 'diario'
require_relative 'estados_juego'
require_relative 'gestiones_inmobiliarias'
require_relative 'gestor_estados'
require_relative 'jugador'
require_relative 'mazo_sorpresas'
require_relative 'operacion_inmobiliaria'
require_relative 'operaciones_juego'
require_relative 'respuestas'
require_relative 'salidas_carcel'
require_relative 'sorpresa'
require_relative 'tablero'
require_relative 'tipo_casilla'
require_relative 'tipo_sorpresa'
require_relative 'titulo_propiedad'
require_relative 'vista_textual'


module Juego_texto
  include Civitas
    vista = Vista_textual.new
    Dado.instance.set_debug(true)
    juego = Civitas_juego.new(["Marina", "Mario"])
    controlador = Controlador.new(juego, vista)
    controlador.juega
end
