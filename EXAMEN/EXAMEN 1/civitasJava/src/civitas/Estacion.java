/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitas;

/**
 *
 * @author mariolopezgonzalez
 */
public class Estacion {
    private String nombre;
    private TipoEstacion estacion;
    
    Estacion(String nombre, TipoEstacion estacion){
        this.nombre = nombre;
        this.estacion = estacion;
    }

    @Override
    public String toString() {
        return "Estacion{" + "nombre=" + nombre + ", estacion=" + estacion + '}';
    }
    
    TipoEstacion getTipo(){
        return estacion;
    }
}
