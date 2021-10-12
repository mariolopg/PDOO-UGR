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
public class MainP4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Jugador j1 = new Jugador ("jugador1");
        System.out.println("Jugador: " + j1);

        TituloPropiedad titulo = new TituloPropiedad("Moscu", 20 , 1.05f, 100 , 200, 125);
        j1.comprar(titulo);
        System.out.println("Titulo: " + titulo);
        System.out.println("Jugador: " + j1);
        
        System.out.println("**************************");
        
        j1 = new JugadorEspeculador ( j1, 1000);
        System.out.println("Jugador: " + j1);
        System.out.println("Titulo: " + titulo);

        System.out.println("Debe ser encarcelado" + j1.debeSerEncarcelado());
        System.out.println("Jugador: " + j1);
    }
    
}
 