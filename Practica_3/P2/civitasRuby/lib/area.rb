# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.

module Civitas
  class Area
    attr_reader :numero_areas
    @@numero_areas = 0
    def initialize(tipo)
      @@numero_areas = @@numero_areas + 1
      @tipo_area = tipo
      @titulos_propiedad = Array.new
    end
    
    def aniadir_titulo_propiedad(titulo)
      @titulos_propiedad << titulo
    end
    
    def to_s
      return "El area es de tipo #{@tipo_area} y tiene #{@titulos_propiedad.size} propiedades"
    end
    
  end
end
