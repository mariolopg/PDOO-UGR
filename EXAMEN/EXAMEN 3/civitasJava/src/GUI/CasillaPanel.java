/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import civitas.*;

/**
 *
 * @author marina
 */
public class CasillaPanel extends javax.swing.JPanel {

    private Casilla casilla;
    /**
     * Creates new form CasillaPanel
     */
    public CasillaPanel() {
        initComponents();
    }
    
    public void setCasilla(Casilla casilla){
        this.casilla=casilla;
        nombreValue.setText(casilla.getNombre());
//        if(casilla instanceof CasillaCalle){
//            tipo.setText("Calle");
//            CasillaCallePanel callePanel = new CasillaCallePanel();
//            callePanel.setCasillaCalle((CasillaCalle) casilla);
//            contenedorVistaCasilla.add(callePanel);
//            callePanel.setVisible(true);
//            
//        } else if (casilla instanceof CasillaImpuesto){
//            tipo.setText("Impuesto");
//            CasillaImpuestoPanel impuestoPanel = new CasillaImpuestoPanel();
//            impuestoPanel.setCasillaImpuesto((CasillaImpuesto) casilla);
//            contenedorVistaCasilla.add(impuestoPanel);
//            impuestoPanel.setVisible(true);
//            
//        } else if (casilla instanceof CasillaJuez){
//            tipo.setText("Juez");
//            CasillaJuezPanel juezPanel = new CasillaJuezPanel();
//            juezPanel.setCasillaJuez((CasillaJuez) casilla);
//            contenedorVistaCasilla.add(juezPanel);
//             juezPanel.setVisible(true);
//            
//        } else if (casilla instanceof CasillaSorpresa){
//            tipo.setText("Sorpresa");
//            CasillaSorpresaPanel sorpresaPanel = new CasillaSorpresaPanel();
//            sorpresaPanel.setCasillaSorpresa((CasillaSorpresa) casilla);
//            contenedorVistaCasilla.add(sorpresaPanel);
//            sorpresaPanel.setVisible(true);
//        }
        setVisible(true);
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

        tipo = new javax.swing.JLabel();
        nombre = new javax.swing.JLabel();
        tipoValue = new javax.swing.JTextField();
        nombreValue = new javax.swing.JTextField();
        contenedorVistaCasilla = new javax.swing.JPanel();

        tipo.setText("Tipo:");

        nombre.setText("Nombre:");

        tipoValue.setText("jTextField1");

        nombreValue.setText("jTextField2");

        javax.swing.GroupLayout contenedorVistaCasillaLayout = new javax.swing.GroupLayout(contenedorVistaCasilla);
        contenedorVistaCasilla.setLayout(contenedorVistaCasillaLayout);
        contenedorVistaCasillaLayout.setHorizontalGroup(
            contenedorVistaCasillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
        );
        contenedorVistaCasillaLayout.setVerticalGroup(
            contenedorVistaCasillaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 175, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contenedorVistaCasilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tipo)
                        .addGap(18, 18, 18)
                        .addComponent(tipoValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nombre)
                        .addGap(18, 18, 18)
                        .addComponent(nombreValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tipo)
                    .addComponent(tipoValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nombre)
                    .addComponent(nombreValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(contenedorVistaCasilla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contenedorVistaCasilla;
    private javax.swing.JLabel nombre;
    private javax.swing.JTextField nombreValue;
    private javax.swing.JLabel tipo;
    private javax.swing.JTextField tipoValue;
    // End of variables declaration//GEN-END:variables
}
