module Civitas
    class Operacion_inmobiliaria

        attr_reader :num_propiedad, :gestion
        
        def initialize ( gest, ip)
            @num_propiedad = ip
            @gestion = gest
        end

    end
end