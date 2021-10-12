/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author mariolopezgonzalez
 */
public class Barrio {
    
    private String nombre;
    private TipoBarrio tipo;
    private float porcentajePlusvalia;

    @Override
    public String toString() {
        return "Barrio{" + "nombre=" + nombre + ", tipo=" + tipo + ", porcentajePlusvalia=" + porcentajePlusvalia + '}';
    }
    
    
    
    Barrio(String nombre, TipoBarrio tipo, float porcentajePlusvalia){
        this.nombre = nombre;
        this.tipo = tipo;
        this.porcentajePlusvalia = porcentajePlusvalia;
    }
    
    public String getEstadoInmuebles(){
        ArrayList <TituloPropiedad> propiedadesEnBarrio = Tablero.getInstance().getInmueblesEnBarrio(this);
        String estados = "";
        for(int i = 0; i < propiedadesEnBarrio.size(); i++){
            boolean tienePropietario = propiedadesEnBarrio.get(i).tienePropietario();
            String info = "";
            if(tienePropietario){
                String nombre = propiedadesEnBarrio.get(i).getPropietario().getNombre();
                info = "El propietario es: " + nombre + " | ";
            }
            else
                info = "Inmueble vacio | ";
            
            estados += info; 
            
        }
        
        return estados;
        
    }

    float getPorcentajePlusvalia() {
        return porcentajePlusvalia;
    }
    
    
    
}
