/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.Jugador;
import civitas.JugadorEspeculador;
import civitas.Regalo;
import civitas.TituloPropiedad;
import java.util.ArrayList;
import javax.swing.JLabel;

/**
 *
 * @author mariolopezgonzalez
 */
public class JugadorPanel extends javax.swing.JPanel {

     private Jugador jugador;
     private ArrayList<Regalo> regalos;
     
    
    /**
     * Creates new form JugadorPanel
     */
    public JugadorPanel() {
        initComponents();
    }
    
    private void rellenaPropiedades (ArrayList<TituloPropiedad> lista) {
    // Se elimina la información antigua
    propiedades.removeAll();
    // Se recorre la lista de propiedades para ir creando sus vistas individuales y añadirlas al panel
    for (TituloPropiedad t : lista) {
        PropiedadPanel vistaPropiedad = new PropiedadPanel();
        vistaPropiedad.setPropiedad(t);
        propiedades.add(vistaPropiedad);
        vistaPropiedad.setVisible(true);
    }
    // Se fuerza la actualización visual del panel propiedades y del panel del jugador
    repaint();
    revalidate();
    }
    
       public void setJugador(Jugador otro) {
        jugador = otro;
        nombreVal.setText(jugador.getNombre());
        saldoVal.setText(String.valueOf(jugador.getSaldo()));
        encarceladoVal.setText(String.valueOf(jugador.isEncarcelado()));
        especuladorVal.setText(String.valueOf(jugador instanceof JugadorEspeculador));
        repaint();
        rellenaPropiedades(jugador.getPropiedades());
        rellenaRegalos(jugador.getRegalos());
    }
       
       public void rellenaRegalos(ArrayList<Regalo> listaR){
           panelRegalos.removeAll();
           
           for(Regalo r: listaR){
               RegaloPanel regPanel = new RegaloPanel();
               regPanel.setRegalo(r);
               panelRegalos.add(regPanel);
               regPanel.setVisible(true);
               
           }
           repaint();
           revalidate();    
       }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        nombre = new javax.swing.JLabel();
        saldo = new javax.swing.JLabel();
        encarcelado = new javax.swing.JLabel();
        especulador = new javax.swing.JLabel();
        nombreVal = new javax.swing.JTextField();
        saldoVal = new javax.swing.JTextField();
        encarceladoVal = new javax.swing.JTextField();
        especuladorVal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        propiedades = new javax.swing.JPanel();
        panelRegalos = new javax.swing.JPanel();
        regalosLabel = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setBackground(new java.awt.Color(254, 254, 254));

        nombre.setText("Nombre");
        nombre.setEnabled(false);

        saldo.setText("Saldo");
        saldo.setEnabled(false);

        encarcelado.setText("Encarcelado");
        encarcelado.setEnabled(false);

        especulador.setText("Especulador");
        especulador.setEnabled(false);

        nombreVal.setText("jTextField1");
        nombreVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreValActionPerformed(evt);
            }
        });

        saldoVal.setText("jTextField2");

        encarceladoVal.setText("jTextField3");

        especuladorVal.setText("jTextField4");

        jLabel1.setText("Propiedades");
        jLabel1.setEnabled(false);

        jScrollPane2.setViewportView(propiedades);

        regalosLabel.setText("Regalos");
        regalosLabel.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelRegalos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(especulador)
                                        .addComponent(encarcelado))
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(encarceladoVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(especuladorVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nombre)
                                .addGap(18, 18, 18)
                                .addComponent(nombreVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saldo)
                                .addGap(18, 18, 18)
                                .addComponent(saldoVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(regalosLabel))
                        .addGap(0, 128, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre)
                    .addComponent(nombreVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldo)
                    .addComponent(saldoVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(encarcelado)
                    .addComponent(encarceladoVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(especulador)
                    .addComponent(especuladorVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(regalosLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelRegalos, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void nombreValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreValActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombreValActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel encarcelado;
    private javax.swing.JTextField encarceladoVal;
    private javax.swing.JLabel especulador;
    private javax.swing.JTextField especuladorVal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel nombre;
    private javax.swing.JTextField nombreVal;
    private javax.swing.JPanel panelRegalos;
    private javax.swing.JPanel propiedades;
    private javax.swing.JLabel regalosLabel;
    private javax.swing.JLabel saldo;
    private javax.swing.JTextField saldoVal;
    // End of variables declaration//GEN-END:variables

    
}
