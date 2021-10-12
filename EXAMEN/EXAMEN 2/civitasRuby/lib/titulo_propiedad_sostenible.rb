# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
require_relative 'titulo_propiedad'

module Civitas
  class Titulo_propiedad_sostenible < Titulo_propiedad
    def initialize(nom, ab, fr, hb, pc, pe, pi)
      super(nom, ab, fr, hb, pc, pe)
      @procentaje_inversion = pi
    end
    
    def hacerme_sostenible(jugador)
      if self == nil
        super
      end
    end
    
    def get_precio_venta
      return super + get_importe_hacerme_sostenible
    end
    
    def to_s
      return "Titulo propiedad sostenible: nombre = #{nombre}, porcentaje inversion = #{@porcentaje_inversion}, alquiler base = #{get_precio_alquiler}, factor revalorizacion = #{factor_revalorizacion}, hipoteca base = #{hipoteca_base}, hipotecado = #{hipotecado}, numero casas = #{num_casas}, numero hoteles = #{num_hoteles}, precio compra = #{precio_compra}, precio edificar = #{precio_edificar}"
    end
    
    # -----------------------------------------
     protected :get_precio_venta
     
  end
end
