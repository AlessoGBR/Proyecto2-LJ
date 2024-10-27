/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.NumeroLinea;
import Flex.AnalizadorLexico;
import Flex.Token;
import java.io.StringReader;

/**
 *
 * @author alesso
 */
public class MenuPrincipal extends javax.swing.JFrame {

    private NumeroLinea numero;

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        numero = new NumeroLinea(txtCodigo);
        jScrollPane2.setRowHeaderView(numero);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSquare = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        prueba = new javax.swing.JTextArea();
        panelTexto = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextArea();
        btnAnalizar = new javax.swing.JButton();
        bntSubir = new javax.swing.JButton();
        btnReporteToken = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU PRINCIPAL");
        setResizable(false);

        panelSquare.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        prueba.setColumns(20);
        prueba.setRows(5);
        jScrollPane1.setViewportView(prueba);

        javax.swing.GroupLayout panelSquareLayout = new javax.swing.GroupLayout(panelSquare);
        panelSquare.setLayout(panelSquareLayout);
        panelSquareLayout.setHorizontalGroup(
            panelSquareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSquareLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelSquareLayout.setVerticalGroup(
            panelSquareLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSquareLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        panelTexto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtCodigo.setColumns(20);
        txtCodigo.setRows(5);
        jScrollPane2.setViewportView(txtCodigo);

        javax.swing.GroupLayout panelTextoLayout = new javax.swing.GroupLayout(panelTexto);
        panelTexto.setLayout(panelTextoLayout);
        panelTextoLayout.setHorizontalGroup(
            panelTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 605, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelTextoLayout.setVerticalGroup(
            panelTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnAnalizar.setText("ANALIZAR");
        btnAnalizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarActionPerformed(evt);
            }
        });

        bntSubir.setText("SUBIR");
        bntSubir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bntSubir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntSubirActionPerformed(evt);
            }
        });

        btnReporteToken.setText("REPORTE TOKENS");
        btnReporteToken.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReporteToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReporteTokenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bntSubir)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnalizar)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReporteToken)
                    .addComponent(panelSquare, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntSubir)
                    .addComponent(btnAnalizar)
                    .addComponent(btnReporteToken))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelSquare, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
        String input = "CREATE TABLE example (id INTEGER, name VARCHAR(255));";
        AnalizadorLexico lexer = new AnalizadorLexico(new StringReader(input));
        try {
            Token token;
            while ((token = lexer.yylex()) != null) {
                System.out.println(token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAnalizarActionPerformed

    private void bntSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSubirActionPerformed


    }//GEN-LAST:event_bntSubirActionPerformed

    private void btnReporteTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReporteTokenActionPerformed


    }//GEN-LAST:event_btnReporteTokenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSubir;
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JButton btnReporteToken;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelSquare;
    private javax.swing.JPanel panelTexto;
    private javax.swing.JTextArea prueba;
    private javax.swing.JTextArea txtCodigo;
    // End of variables declaration//GEN-END:variables
}
