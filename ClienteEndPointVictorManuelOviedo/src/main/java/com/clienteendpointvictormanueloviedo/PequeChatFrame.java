/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteendpointvictormanueloviedo;

import com.mycompany.objetoendpointvictormanueloviedo.Mensaje;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author oscar
 */
public class PequeChatFrame extends javax.swing.JFrame {

    private HashMap<String, javax.swing.JTextArea> mapTextArea;
    private MyClient client = null;
    private boolean conectado;

    /**
     * Creates new form PequeChatFrame
     */
    public PequeChatFrame() {
        initComponents();
        conectado = false;
        mapTextArea = new HashMap();
        jButtonAgregarPrivado.setVisible(false);
        jButtonBorraPrivado.setVisible(false);
        /*jTabbedPane1.setTitleAt(0, "room 1");
        jTabbedPane1.getComponent(0).setName("tabla1");*/
        // <editor-fold defaultstate="collapsed" desc="addTab">                          
        javax.swing.JTextArea jTextAreaChat = new javax.swing.JTextArea();
        jTextAreaChat.setColumns(20);
        jTextAreaChat.setRows(5);
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(jTextAreaChat);
        String nombreRoom = "room " + (1 + jTabbedPaneRooms.getComponentCount());
        jTabbedPaneRooms.addTab(nombreRoom, jScrollPane1);
        mapTextArea.put(jTabbedPaneRooms.getTitleAt(jTabbedPaneRooms.getComponentZOrder(jScrollPane1)), jTextAreaChat);
        jTextAreaChat = new javax.swing.JTextArea();
        jTextAreaChat.setColumns(20);
        jTextAreaChat.setRows(5);
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(jTextAreaChat);
        nombreRoom = "room " + (1 + jTabbedPaneRooms.getComponentCount());
        jTabbedPaneRooms.addTab(nombreRoom, jScrollPane1);
        //</editor-fold>
        mapTextArea.put(jTabbedPaneRooms.getTitleAt(jTabbedPaneRooms.getComponentZOrder(jScrollPane1)), jTextAreaChat);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonConectar = new javax.swing.JButton();
        jTextFieldMensaje = new javax.swing.JTextField();
        jButtonEnviar = new javax.swing.JButton();
        jTextFieldNombre = new javax.swing.JTextField();
        jTabbedPanePrincipal = new javax.swing.JTabbedPane();
        jTabbedPaneRooms = new javax.swing.JTabbedPane();
        jTabbedPanePrivados = new javax.swing.JTabbedPane();
        jButtonAgregarPrivado = new javax.swing.JButton();
        jButtonBorraPrivado = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButtonConectar.setText("Conectar");
        jButtonConectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConectarActionPerformed(evt);
            }
        });

        jButtonEnviar.setText("mandar");
        jButtonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEnviarActionPerformed(evt);
            }
        });

        jTabbedPanePrincipal.setMinimumSize(new java.awt.Dimension(430, 310));
        jTabbedPanePrincipal.setPreferredSize(new java.awt.Dimension(430, 310));
        jTabbedPanePrincipal.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPanePrincipalStateChanged(evt);
            }
        });
        jTabbedPanePrincipal.addTab("ROOMS", jTabbedPaneRooms);
        jTabbedPanePrincipal.addTab("PRIVADOS", jTabbedPanePrivados);

        jButtonAgregarPrivado.setText("Add");
        jButtonAgregarPrivado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAgregarPrivadoActionPerformed(evt);
            }
        });

        jButtonBorraPrivado.setText("Remove");
        jButtonBorraPrivado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBorraPrivadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPanePrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMensaje)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonConectar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonBorraPrivado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonAgregarPrivado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonEnviar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jTabbedPanePrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonConectar)
                    .addComponent(jButtonEnviar)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAgregarPrivado)
                    .addComponent(jButtonBorraPrivado))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConectarActionPerformed
        if (!conectado) {
            try {
                String nombre = jTextFieldNombre.getText();
                client=new MyClient(new URI("ws://localhost:8080/websocket?usuario=" + nombre));
                client.addMessageHandler(new AtiendeMensajes());
                conectado = true;
                jButtonConectar.setText("Desconectar");
                jButtonAgregarPrivado.setEnabled(true);
                jButtonBorraPrivado.setEnabled(true);
                jButtonEnviar.setEnabled(true);
            } catch (URISyntaxException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else {
            client.disconnect();
            conectado=false;
            jButtonConectar.setText("Conectar");
            jButtonAgregarPrivado.setEnabled(false);
            jButtonBorraPrivado.setEnabled(false);
            jButtonEnviar.setEnabled(false);
        }
    }//GEN-LAST:event_jButtonConectarActionPerformed

    private void jButtonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEnviarActionPerformed
        if (conectado) {
            jTabbedPaneRooms.getComponent(jTabbedPaneRooms.getSelectedIndex()).getName();
            String message = jTextFieldMensaje.getText();
            String nombre = jTextFieldNombre.getText();
            Mensaje m = new Mensaje();
            m.setMensaje(message);
            m.setFormateado(true);
            m.setFrom(nombre);
            m.setRoom(jTabbedPaneRooms.getTitleAt(jTabbedPaneRooms.getSelectedIndex()));
            client.sendMessage(m);
        }
    }//GEN-LAST:event_jButtonEnviarActionPerformed

    private void jTabbedPanePrincipalStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPanePrincipalStateChanged
        if(jTabbedPanePrincipal.getSelectedComponent().equals(jTabbedPanePrivados)){
            jButtonAgregarPrivado.setVisible(true);
            jButtonBorraPrivado.setVisible(true);
        }else{
            jButtonAgregarPrivado.setVisible(false);
            jButtonBorraPrivado.setVisible(false);
        }
    }//GEN-LAST:event_jTabbedPanePrincipalStateChanged

    private void jButtonAgregarPrivadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAgregarPrivadoActionPerformed
        addTab(JOptionPane.showInputDialog(this,"Introduce el usuario con el que desea hablar","Elige usuario",JOptionPane.INFORMATION_MESSAGE),jTabbedPanePrivados);
    }//GEN-LAST:event_jButtonAgregarPrivadoActionPerformed

    private void jButtonBorraPrivadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonBorraPrivadoActionPerformed
        jTabbedPanePrivados.remove(jTabbedPanePrivados.getSelectedComponent());
    }//GEN-LAST:event_jButtonBorraPrivadoActionPerformed

    private void addTab(String nombreRoom, javax.swing.JTabbedPane tabbedPane){
        javax.swing.JTextArea jTextAreaChat = new javax.swing.JTextArea();
        jTextAreaChat.setColumns(20);
        jTextAreaChat.setRows(5);
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(jTextAreaChat);
        tabbedPane.addTab(nombreRoom, jScrollPane1);
        mapTextArea.put(tabbedPane.getTitleAt(tabbedPane.getComponentZOrder(jScrollPane1)), jTextAreaChat);
    }
    
    private class AtiendeMensajes implements MyClient.MessageHandler {

        @Override
        public void handleMessage(Mensaje message) {
            if(message.isFormateado()){
                System.out.println("ENTRAMOS EN MENSAJE NORMAL");
                mapTextArea.get(message.getRoom()).append(message.getFrom() + "::" + message.getMensaje() + "\n");
            }else{
                mapTextArea.get(message.getRoom()).append(message.getMensaje() + "\n");
            }
            System.out.println("SALIMOS DEL HANDLE");
        }

    }

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
            java.util.logging.Logger.getLogger(PequeChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PequeChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PequeChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PequeChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PequeChatFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAgregarPrivado;
    private javax.swing.JButton jButtonBorraPrivado;
    private javax.swing.JButton jButtonConectar;
    private javax.swing.JButton jButtonEnviar;
    private javax.swing.JTabbedPane jTabbedPanePrincipal;
    private javax.swing.JTabbedPane jTabbedPanePrivados;
    private javax.swing.JTabbedPane jTabbedPaneRooms;
    private javax.swing.JTextField jTextFieldMensaje;
    private javax.swing.JTextField jTextFieldNombre;
    // End of variables declaration//GEN-END:variables
}
