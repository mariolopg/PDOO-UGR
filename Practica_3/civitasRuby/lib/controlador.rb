# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Juego_texto
  class Controlador
    include Civitas

    def initialize( juego, vista)
      @juego = juego
      @vista = vista
    end

    def juega
      @vista.set_civitas_juego(@juego) 

        while !@juego.final_del_juego
          @vista.actualizar_vista
          @vista.pausa
          operacion = @juego.siguiente_paso
          @vista.mostrar_siguiente_operacion(operacion)
          if operacion != Operaciones_juego::PASAR_TURNO
            @vista.mostrar_eventos
          end
          if !@juego.final_del_juego

            if operacion == Operaciones_juego::COMPRAR
              accion = @vista.comprar
              if accion == Respuestas::SI
                @juego.comprar
              end
              @juego.siguiente_paso_completado(operacion)
            end

            if operacion == Operaciones_juego::GESTIONAR
              @vista.gestionar
              i_gestion = @vista.igestion
              i_propiedad = @vista.ipropiedad
              gestion_elegida = Lista_gestiones_inmobiliarias[i_gestion]
              operacion_inmmob = Operacion_inmobiliaria.new(gestion_elegida, i_propiedad)

              if gestion_elegida == Gestiones_inmobiliarias::VENDER
                @juego.vender(i_propiedad)

              elsif gestion_elegida == Gestiones_inmobiliarias::HIPOTECAR
               @juego.hipotecar(i_propiedad)

              elsif gestion_elegida == Gestiones_inmobiliarias::CANCELAR_HIPOTECA
                @juego.cancelar_hipoteca(i_propiedad)

              elsif gestion_elegida == Gestiones_inmobiliarias::CONSTRUIR_CASA
                @juego.construir_casa(i_propiedad)

              elsif gestion_elegida == Gestiones_inmobiliarias::CONSTRUIR_HOTEL
                @juego.construir_hotel(i_propiedad)

              else
                @juego.siguiente_paso_completado(operacion)

              end
            end

            if operacion == Operaciones_juego::SALIR_CARCEL
              salir = @vista.salir_carcel
              if salir == Salidas_carcel::TIRANDO
                @juego.salir_carcel_tirando
              else
                @juego.salir_carcel_pagando
              end
              @juego.siguiente_paso_completado(operacion)
            end

          end

        end

        puts "***RANKING***"
        puts @juego.ranking

    end

  end
end
