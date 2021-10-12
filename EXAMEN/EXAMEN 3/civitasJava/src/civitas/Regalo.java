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
public class Regalo {
    private String nombre;
    private TipoRegalo tipo;
    
    Regalo(String nombre, TipoRegalo tipo){
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public TipoRegalo getTipo(){
        return tipo;
    }

    @Override
    public String toString() {
        return "Regalo{" + "nombre=" + nombre + ", tipo=" + tipo + '}';
    }
    
    
}
