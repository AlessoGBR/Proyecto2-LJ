/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Frontend;

import Backend.AnalizadorDDL;
import Backend.Archivo;
import Backend.NumeroLinea;
import Backend.SyntaxException;
import Flex.AnalizadorLexico;
import Flex.Token;
import Flex.TokenColor;
import Flex.TokenType;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author alesso
 */
public class MenuPrincipal extends javax.swing.JFrame {

    private NumeroLinea numero;
    private ArrayList<Token> ErrorSintactico;
    private ArrayList<Token> ErrorLexico;
    private ArrayList<Token> Tablas;
    private ArrayList<Token> TablasModificadas;
    private int create = 0;
    private int delete = 0;
    private int update = 0;
    private int select = 0;
    private int alter = 0;

    /**
     * Creates new form MenuPrincipal
     */
    public MenuPrincipal() {
        initComponents();
        numero = new NumeroLinea(txtCodigo);
        jScrollPane2.setRowHeaderView(numero);
        btnReportes.setEnabled(false);
        btnTablas.setEnabled(false);
        btnModificadas.setEnabled(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTexto = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtCodigo = new javax.swing.JTextPane();
        btnAnalizar = new javax.swing.JButton();
        bntSubir = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnTablas = new javax.swing.JButton();
        btnModificadas = new javax.swing.JButton();
        btnReportes3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MENU PRINCIPAL");
        setResizable(false);

        panelTexto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jScrollPane2.setViewportView(txtCodigo);

        javax.swing.GroupLayout panelTextoLayout = new javax.swing.GroupLayout(panelTexto);
        panelTexto.setLayout(panelTextoLayout);
        panelTextoLayout.setHorizontalGroup(
            panelTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        panelTextoLayout.setVerticalGroup(
            panelTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 753, Short.MAX_VALUE)
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

        btnReportes.setText("REPORTE LEXICO");
        btnReportes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });

        btnGuardar.setText("GUARDAR");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnTablas.setText("REPORTE TABLAS");
        btnTablas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTablas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTablasActionPerformed(evt);
            }
        });

        btnModificadas.setText("REPORTE MODIFICADAS");
        btnModificadas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnModificadas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificadasActionPerformed(evt);
            }
        });

        btnReportes3.setText("REPORTE OPERACIONES");
        btnReportes3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReportes3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportes3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(bntSubir)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnAnalizar)
                        .addGap(154, 154, 154)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReportes)
                            .addComponent(btnTablas))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnReportes3)
                            .addComponent(btnModificadas))
                        .addContainerGap(141, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bntSubir)
                            .addComponent(btnAnalizar)
                            .addComponent(btnGuardar))
                        .addGap(29, 29, 29))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnReportes)
                            .addComponent(btnModificadas))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTablas)
                            .addComponent(btnReportes3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(panelTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAnalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarActionPerformed
        ArrayList<Token> tokens = new ArrayList<>();
        this.ErrorLexico = new ArrayList<>();
        this.ErrorSintactico = new ArrayList<>();
        this.Tablas = new ArrayList<>();
        this.TablasModificadas = new ArrayList<>();
        if (txtCodigo.getText().length() != 0) {
            AnalizadorLexico lexer = new AnalizadorLexico(new StringReader(txtCodigo.getText()));
            TokenColor color = new TokenColor(txtCodigo);
            txtCodigo.setText("");
            Token token;
            try {
                while ((token = lexer.yylex()) != null && token.getType() != TokenType.EOF) {
                    color.applyStyleToToken(token.getLexema(), token.getType());
                    if (token.getType() == TokenType.ERROR) {
                        this.ErrorLexico.add(token);
                    } else {
                        tokens.add(token);
                    }

                }
            } catch (IOException ex) {
                System.out.println("error: " + ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "DEBES DE INGRESAR UN CODIGO PARA VERIFICAR");
            return;
        }

        AnalizadorDDL analizarDDL = new AnalizadorDDL(tokens);
        try {
            analizarDDL.parse();
            this.create = analizarDDL.getCreate();
        this.delete = analizarDDL.getDelete();
        this.update = analizarDDL.getUpdate();
        this.select = analizarDDL.getSelect();
        this.alter = analizarDDL.getAlter();
            JOptionPane.showMessageDialog(null, "CODIGO ANALIZADO, NINGUN ERROR ENCONTRADO");
        } catch (SyntaxException ex) {
            ErrorSintactico = analizarDDL.getError();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        this.Tablas = analizarDDL.getTablas();
        this.TablasModificadas = analizarDDL.getTablasModificadas();
        
        btnReportes.setEnabled(true);
        btnTablas.setEnabled(true);
        btnModificadas.setEnabled(true);

    }//GEN-LAST:event_btnAnalizarActionPerformed

    private void bntSubirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntSubirActionPerformed
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            Archivo leer = new Archivo();
            txtCodigo.setText(leer.leerArchivoTxt(selectedFile.getAbsolutePath()));

        }

    }//GEN-LAST:event_bntSubirActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        ReporteLexico reportes = new ReporteLexico(this.ErrorLexico);
        reportes.setVisible(true);
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar archivo de texto");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos de texto (*.txt)", "txt"));

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (!fileToSave.getName().endsWith(".txt")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".txt");
            }

            Archivo archivo = new Archivo();
            if (archivo.guardar(fileToSave, txtCodigo.getText())) {
                JOptionPane.showMessageDialog(null, "Archivo guardado con éxito");
            } else {
                JOptionPane.showMessageDialog(null, "Error a la hora de guardar el archivo");
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnTablasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTablasActionPerformed
        ReporteTablas reporte = new ReporteTablas(Tablas);
        reporte.setVisible(true);
    }//GEN-LAST:event_btnTablasActionPerformed

    private void btnModificadasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificadasActionPerformed
        ReporteModificadas reporte = new ReporteModificadas(TablasModificadas);
        reporte.setVisible(true);
    }//GEN-LAST:event_btnModificadasActionPerformed

    private void btnReportes3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportes3ActionPerformed
        ReporteOperaciones operacion = new ReporteOperaciones(this.create,this.delete,this.update,this.select,this.alter);
        operacion.setVisible(true);
    }//GEN-LAST:event_btnReportes3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntSubir;
    private javax.swing.JButton btnAnalizar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificadas;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnReportes3;
    private javax.swing.JButton btnTablas;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel panelTexto;
    private javax.swing.JTextPane txtCodigo;
    // End of variables declaration//GEN-END:variables
}
