/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.*;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author mariolopezgonzalez
 */
public class CivitasView extends javax.swing.JFrame {

    private CivitasJuego juego;
    private JugadorPanel jugadorPanel;
    private ArrayList<JPanel> casillasPanel;
    private ArrayList<Border> bordesCasilla;
    private GestionarDialog gestionarD;
    /**
     * Creates new form CivitasView
     */
    public CivitasView() {
        
        initComponents();
        inicializarCasillas();
        inicializarBordes();
        sigOperacionValue.setText("Inicio Del Juego");
        setSize(1000, 1000);
    
        jugadorPanel = new JugadorPanel();
        gestionarD = new GestionarDialog(this);
        contenedorVistaJugador.add(jugadorPanel);
        //contenedorVistaCasilla.add(casillaPanel);
        
        repaint();
        revalidate();
    }

    public void setCivitasJuego(CivitasJuego juego) {
        this.juego = juego;
        setVisible(true);
        nombreJ1.setText(juego.getJugadores().get(0).getNombre());
        nombreJ2.setText(juego.getJugadores().get(1).getNombre());
        nombreJ3.setText(juego.getJugadores().get(2).getNombre());
        nombreJ4.setText(juego.getJugadores().get(3).getNombre());
        pack();
        repaint();
        revalidate();
       
    }
    
    void mostrarEventos(){
        DiarioDialog diarioD = new DiarioDialog(this);
        diarioD.repaint();
        diarioD.revalidate();
    }
    
    private void setCasilla( int indiceCasilla, JPanel casillaPanel, ArrayList<Jugador> jugadores){
        boolean coloreado = false;
            
            for (int i=0; i<jugadores.size() && !coloreado; i++)
                if (indiceCasilla == jugadores.get(i).getNumCasillaActual()){
                    casillaPanel.setBorder(bordesCasilla.get(i));
                    coloreado=true;
                } 

            if (!coloreado)
                casillaPanel.setBorder(bordesCasilla.get(4));        
        
    }
    
    private void inicializarBordes(){
        bordesCasilla = new ArrayList<>();
        bordesCasilla.add(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(253, 121, 207), 2)); //Jugador 1 Rosa
        bordesCasilla.add(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(120,176,219), 2)); //Jugador 2 Azul
        bordesCasilla.add(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(253,188,79), 2)); //Jugador 3 Naranja
        bordesCasilla.add(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102,218,70), 2)); //Jugador 4 Verde
        bordesCasilla.add(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0), 1)); //Sin jugador
        
    }
    
    private void inicializarCasillas(){
        casillasPanel = new ArrayList<>();
        casillasPanel.add(c0);
        casillasPanel.add(c1);
        casillasPanel.add(c2);
        casillasPanel.add(c3);
        casillasPanel.add(c4);
        casillasPanel.add(c5);
        casillasPanel.add(c6);
        casillasPanel.add(c7);
        casillasPanel.add(c8);
        casillasPanel.add(c9);
        casillasPanel.add(c10);
        casillasPanel.add(c11);
        casillasPanel.add(c12);
        casillasPanel.add(c13);
        casillasPanel.add(c14);
        casillasPanel.add(c15);
        casillasPanel.add(c16);
        casillasPanel.add(c17);
        casillasPanel.add(c18);
        casillasPanel.add(c19);
    }

    
//    public int coordenadaX(int index){
//        int x;
//        if (index>0 && index<=4)
//           x=500-index*100;
//        else if ( index>=5 && index<=10 )
//            x=0;
//        else if( index >10 && index <=15)
//            x=(index%5)*100;
//        else
//            x=500;
//        return x;
//    }
//    
//    public int coordenadaY(int index){
//        int y;
//        if (index>=0 && index<=5)
//            y=500;
//        else if ( index>5 && index<10 )
//            y=500-(index%5)*100;
//        else if( index >=10 && index <=15)
//            y=0;
//        else
//            y=(index%5)*100;
//            
//        return y;
//    }
//    
    public void actualizarVista(){
        jugadorPanel.setJugador(juego.getJugadorActual());
        ArrayList<Casilla> casillas = juego.getTablero().getCasillas();
        jugadorPanel.setBorder(bordesCasilla.get(juego.getIndiceJugadorActual()));
        for (int i=0; i<casillas.size(); i++){
            setCasilla( i, casillasPanel.get(i), juego.getJugadores());
        }
        ranking.setVisible(false);
        rankingValue.setVisible(false);
        if (juego.finalDelJuego()){
            ranking.setVisible(true);
            rankingValue.setVisible(true);
            ArrayList<Jugador> jugadores = juego.ranking();
            String ranking = "";
            for (int i=0; i<jugadores.size(); i++){
                ranking += String.valueOf(i+1)+ "º " + jugadores.get(i).getNombre() + "\n";
            }
            rankingValue.setText(ranking);
        }
        repaint();
        revalidate();
    }
    
    public void mostrarSiguienteOperacion(OperacionesJuego operacion){
        sigOperacionValue.setText(String.valueOf(operacion));
        actualizarVista();
    }
    
    
    
    Respuestas comprar(){
        int opcion = JOptionPane.showConfirmDialog(null, "¿Quieres comprar la calle actual?", "Compra", JOptionPane.YES_NO_OPTION);
        if (opcion==0)
            return Respuestas.SI;
        else
            return Respuestas.NO;
    }
    
     SalidasCarcel salirCarcel() {
         String[] opciones = {"Pagando","Tirando el dado"};
         int respuesta= JOptionPane.showOptionDialog(null, "¿Cómo quieres salir de la cárcel?", 
                 "Salir de la cárcel", JOptionPane.DEFAULT_OPTION, 
                 JOptionPane.QUESTION_MESSAGE,null, opciones, opciones[0] );
         return (SalidasCarcel.values()[respuesta]);
     }
     
    
    public int getGestion() {
        return gestionarD.getGestion();
    }

    public int getPropiedad() {
        return gestionarD.getPropiedad();
    }
    
    public void gestionar(){
        gestionarD.gestionar(juego.getJugadorActual());
        gestionarD.pack();
        gestionarD.repaint();
        gestionarD.revalidate();
        gestionarD.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sigOperacion = new javax.swing.JLabel();
        sigOperacionValue = new javax.swing.JTextField();
        ranking = new javax.swing.JLabel();
        c0 = new javax.swing.JPanel();
        c1 = new javax.swing.JPanel();
        c2 = new javax.swing.JPanel();
        c3 = new javax.swing.JPanel();
        c4 = new javax.swing.JPanel();
        c5 = new javax.swing.JPanel();
        c6 = new javax.swing.JPanel();
        c7 = new javax.swing.JPanel();
        c8 = new javax.swing.JPanel();
        c9 = new javax.swing.JPanel();
        c10 = new javax.swing.JPanel();
        c11 = new javax.swing.JPanel();
        c12 = new javax.swing.JPanel();
        c13 = new javax.swing.JPanel();
        c14 = new javax.swing.JPanel();
        c15 = new javax.swing.JPanel();
        c16 = new javax.swing.JPanel();
        c17 = new javax.swing.JPanel();
        c18 = new javax.swing.JPanel();
        c19 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rankingValue = new javax.swing.JTextArea();
        contenedorVistaJugador = new javax.swing.JPanel();
        infoJugadorLabel = new javax.swing.JLabel();
        tableroFondo = new javax.swing.JLabel();
        nombreJ1 = new javax.swing.JTextField();
        nombreJ2 = new javax.swing.JTextField();
        nombreJ3 = new javax.swing.JTextField();
        nombreJ4 = new javax.swing.JTextField();
        colorJ4 = new javax.swing.JLabel();
        colorJ1 = new javax.swing.JLabel();
        colorJ2 = new javax.swing.JLabel();
        colorJ3 = new javax.swing.JLabel();
        bgColor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(254, 193, 199));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(1000, 1000));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sigOperacion.setForeground(new java.awt.Color(255, 255, 255));
        sigOperacion.setText("Siguiente Operacion: ");
        sigOperacion.setEnabled(false);
        getContentPane().add(sigOperacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 550, -1, -1));

        sigOperacionValue.setText("jTextField1");
        getContentPane().add(sigOperacionValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 550, -1, -1));

        ranking.setForeground(new java.awt.Color(254, 254, 254));
        ranking.setText("Ranking");
        getContentPane().add(ranking, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 580, -1, -1));

        c0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c0.setOpaque(false);

        javax.swing.GroupLayout c0Layout = new javax.swing.GroupLayout(c0);
        c0.setLayout(c0Layout);
        c0Layout.setHorizontalGroup(
            c0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c0Layout.setVerticalGroup(
            c0Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c0, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 500, -1, -1));

        c1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c1.setOpaque(false);

        javax.swing.GroupLayout c1Layout = new javax.swing.GroupLayout(c1);
        c1.setLayout(c1Layout);
        c1Layout.setHorizontalGroup(
            c1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c1Layout.setVerticalGroup(
            c1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 500, -1, -1));

        c2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c2.setOpaque(false);

        javax.swing.GroupLayout c2Layout = new javax.swing.GroupLayout(c2);
        c2.setLayout(c2Layout);
        c2Layout.setHorizontalGroup(
            c2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c2Layout.setVerticalGroup(
            c2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 500, -1, 100));

        c3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c3.setOpaque(false);

        javax.swing.GroupLayout c3Layout = new javax.swing.GroupLayout(c3);
        c3.setLayout(c3Layout);
        c3Layout.setHorizontalGroup(
            c3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c3Layout.setVerticalGroup(
            c3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, -1, -1));

        c4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c4.setOpaque(false);

        javax.swing.GroupLayout c4Layout = new javax.swing.GroupLayout(c4);
        c4.setLayout(c4Layout);
        c4Layout.setHorizontalGroup(
            c4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c4Layout.setVerticalGroup(
            c4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c4, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 500, -1, -1));

        c5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c5.setOpaque(false);

        javax.swing.GroupLayout c5Layout = new javax.swing.GroupLayout(c5);
        c5.setLayout(c5Layout);
        c5Layout.setHorizontalGroup(
            c5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c5Layout.setVerticalGroup(
            c5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, -1, -1));

        c6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c6.setOpaque(false);

        javax.swing.GroupLayout c6Layout = new javax.swing.GroupLayout(c6);
        c6.setLayout(c6Layout);
        c6Layout.setHorizontalGroup(
            c6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c6Layout.setVerticalGroup(
            c6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, -1, -1));

        c7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c7.setOpaque(false);

        javax.swing.GroupLayout c7Layout = new javax.swing.GroupLayout(c7);
        c7.setLayout(c7Layout);
        c7Layout.setHorizontalGroup(
            c7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c7Layout.setVerticalGroup(
            c7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, -1, -1));

        c8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c8.setOpaque(false);

        javax.swing.GroupLayout c8Layout = new javax.swing.GroupLayout(c8);
        c8.setLayout(c8Layout);
        c8Layout.setHorizontalGroup(
            c8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c8Layout.setVerticalGroup(
            c8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, -1, -1));

        c9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c9.setOpaque(false);

        javax.swing.GroupLayout c9Layout = new javax.swing.GroupLayout(c9);
        c9.setLayout(c9Layout);
        c9Layout.setHorizontalGroup(
            c9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c9Layout.setVerticalGroup(
            c9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, -1, -1));

        c10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c10.setOpaque(false);

        javax.swing.GroupLayout c10Layout = new javax.swing.GroupLayout(c10);
        c10.setLayout(c10Layout);
        c10Layout.setHorizontalGroup(
            c10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c10Layout.setVerticalGroup(
            c10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 100, 100));

        c11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c11.setOpaque(false);

        javax.swing.GroupLayout c11Layout = new javax.swing.GroupLayout(c11);
        c11.setLayout(c11Layout);
        c11Layout.setHorizontalGroup(
            c11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c11Layout.setVerticalGroup(
            c11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c11, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 100, 100));

        c12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c12.setOpaque(false);

        javax.swing.GroupLayout c12Layout = new javax.swing.GroupLayout(c12);
        c12.setLayout(c12Layout);
        c12Layout.setHorizontalGroup(
            c12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c12Layout.setVerticalGroup(
            c12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c12, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 100, 100));

        c13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c13.setOpaque(false);

        javax.swing.GroupLayout c13Layout = new javax.swing.GroupLayout(c13);
        c13.setLayout(c13Layout);
        c13Layout.setHorizontalGroup(
            c13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c13Layout.setVerticalGroup(
            c13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c13, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 0, 100, 100));

        c14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c14.setOpaque(false);

        javax.swing.GroupLayout c14Layout = new javax.swing.GroupLayout(c14);
        c14.setLayout(c14Layout);
        c14Layout.setHorizontalGroup(
            c14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c14Layout.setVerticalGroup(
            c14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 100, 100));

        c15.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c15.setOpaque(false);

        javax.swing.GroupLayout c15Layout = new javax.swing.GroupLayout(c15);
        c15.setLayout(c15Layout);
        c15Layout.setHorizontalGroup(
            c15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c15Layout.setVerticalGroup(
            c15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c15, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 0, 100, 100));

        c16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c16.setOpaque(false);

        javax.swing.GroupLayout c16Layout = new javax.swing.GroupLayout(c16);
        c16.setLayout(c16Layout);
        c16Layout.setHorizontalGroup(
            c16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c16Layout.setVerticalGroup(
            c16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c16, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 100, 100, 100));

        c17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c17.setOpaque(false);

        javax.swing.GroupLayout c17Layout = new javax.swing.GroupLayout(c17);
        c17.setLayout(c17Layout);
        c17Layout.setHorizontalGroup(
            c17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c17Layout.setVerticalGroup(
            c17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c17, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 200, -1, -1));

        c18.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c18.setOpaque(false);

        javax.swing.GroupLayout c18Layout = new javax.swing.GroupLayout(c18);
        c18.setLayout(c18Layout);
        c18Layout.setHorizontalGroup(
            c18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c18Layout.setVerticalGroup(
            c18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c18, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 300, 100, 100));

        c19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        c19.setOpaque(false);

        javax.swing.GroupLayout c19Layout = new javax.swing.GroupLayout(c19);
        c19.setLayout(c19Layout);
        c19Layout.setHorizontalGroup(
            c19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );
        c19Layout.setVerticalGroup(
            c19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 98, Short.MAX_VALUE)
        );

        getContentPane().add(c19, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 400, -1, -1));

        rankingValue.setBackground(new java.awt.Color(254, 254, 254));
        rankingValue.setColumns(20);
        rankingValue.setRows(5);
        jScrollPane1.setViewportView(rankingValue);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 600, 260, 80));
        getContentPane().add(contenedorVistaJugador, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 40, -1, -1));

        infoJugadorLabel.setForeground(new java.awt.Color(254, 254, 254));
        infoJugadorLabel.setText("Info Jugador");
        getContentPane().add(infoJugadorLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, -1, -1));

        tableroFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/tablero_civitas.png"))); // NOI18N
        getContentPane().add(tableroFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        nombreJ1.setText("jTextField1");
        nombreJ1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreJ1ActionPerformed(evt);
            }
        });
        getContentPane().add(nombreJ1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 620, -1, -1));

        nombreJ2.setText("jTextField2");
        getContentPane().add(nombreJ2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 660, -1, -1));

        nombreJ3.setText("jTextField3");
        getContentPane().add(nombreJ3, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 620, -1, -1));

        nombreJ4.setText("jTextField4");
        getContentPane().add(nombreJ4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 660, -1, -1));

        colorJ4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/verde.png"))); // NOI18N
        getContentPane().add(colorJ4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 650, -1, 50));

        colorJ1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/rosa.png"))); // NOI18N
        getContentPane().add(colorJ1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 610, -1, 50));

        colorJ2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/azul.png"))); // NOI18N
        getContentPane().add(colorJ2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 650, -1, 50));

        colorJ3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/naranja.png"))); // NOI18N
        getContentPane().add(colorJ3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 610, -1, 50));

        bgColor.setBackground(new java.awt.Color(91, 1, 15));
        bgColor.setOpaque(true);
        getContentPane().add(bgColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1010, 790));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreJ1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreJ1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreJ1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CivitasView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CivitasView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bgColor;
    private javax.swing.JPanel c0;
    private javax.swing.JPanel c1;
    private javax.swing.JPanel c10;
    private javax.swing.JPanel c11;
    private javax.swing.JPanel c12;
    private javax.swing.JPanel c13;
    private javax.swing.JPanel c14;
    private javax.swing.JPanel c15;
    private javax.swing.JPanel c16;
    private javax.swing.JPanel c17;
    private javax.swing.JPanel c18;
    private javax.swing.JPanel c19;
    private javax.swing.JPanel c2;
    private javax.swing.JPanel c3;
    private javax.swing.JPanel c4;
    private javax.swing.JPanel c5;
    private javax.swing.JPanel c6;
    private javax.swing.JPanel c7;
    private javax.swing.JPanel c8;
    private javax.swing.JPanel c9;
    private javax.swing.JLabel colorJ1;
    private javax.swing.JLabel colorJ2;
    private javax.swing.JLabel colorJ3;
    private javax.swing.JLabel colorJ4;
    private javax.swing.JPanel contenedorVistaJugador;
    private javax.swing.JLabel infoJugadorLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nombreJ1;
    private javax.swing.JTextField nombreJ2;
    private javax.swing.JTextField nombreJ3;
    private javax.swing.JTextField nombreJ4;
    private javax.swing.JLabel ranking;
    private javax.swing.JTextArea rankingValue;
    private javax.swing.JLabel sigOperacion;
    private javax.swing.JTextField sigOperacionValue;
    private javax.swing.JLabel tableroFondo;
    // End of variables declaration//GEN-END:variables
}
