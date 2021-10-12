# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

require_relative 'casilla'
require_relative 'casilla_calle'
require_relative 'casilla_impuesto'
require_relative 'casilla_juez'
require_relative 'casilla_sorpresa'
require_relative 'civitas_juego'
require_relative 'controlador'
require_relative 'dado'
require_relative 'diario'
require_relative 'estados_juego'
require_relative 'gestiones_inmobiliarias'
require_relative 'gestor_estados'
require_relative 'jugador'
require_relative 'jugador_especulador'
require_relative 'mazo_sorpresas'
require_relative 'operacion_inmobiliaria'
require_relative 'operaciones_juego'
require_relative 'respuestas'
require_relative 'salidas_carcel'
require_relative 'sorpresa'
require_relative 'sorpresa_especulador'
require_relative 'sorpresa_ir_carcel'
require_relative 'sorpresa_ir_casilla'
require_relative 'sorpresa_pagar_cobrar'
require_relative 'sorpresa_por_casa_hotel'
require_relative 'sorpresa_por_jugador'
require_relative 'sorpresa_salir_carcel'
require_relative 'tablero'
require_relative 'titulo_propiedad_sostenible'

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
