/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import components.TextEditor;
import components.TextRender;
import constants.Constantes;
import static constants.Constantes.*;
import controller.ControlJuegos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import static javafx.beans.binding.Bindings.or;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.*;
import javax.swing.RowFilter;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import com.objetopruebavictormanuel.Juego;
import view.model.TablaModeloJuego;
import org.jdesktop.swingx.table.DatePickerCellEditor;

/**
 *
 * @author dam2
 */
public class view extends javax.swing.JFrame {

    private ControlJuegos control;
    private LinkedHashMap<Integer,String> tipos;

    /**
     * Creates new form view
     */
    public view() {
        initComponents();
        control = new ControlJuegos();
        TablaModeloJuego model = new TablaModeloJuego();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        System.out.println("I" + e.getColumn() + " " + e.getFirstRow());
                        break;
                    case TableModelEvent.UPDATE:
                        if (!((TablaModeloJuego) jTable1.getModel()).isInsertando()) {
                            control.UpdateJuego(new Juego((int) jTable1.getModel().getValueAt(e.getFirstRow(), 0), (String) jTable1.getModel().getValueAt(e.getFirstRow(), 1), (Date) jTable1.getModel().getValueAt(e.getFirstRow(), 2), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 3), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 4), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 5)));
                        } else {
                            ((TablaModeloJuego) jTable1.getModel()).insertRow((String) jTable1.getModel().getValueAt(e.getFirstRow(), 1), (Date) jTable1.getModel().getValueAt(e.getFirstRow(), 2), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 3), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 4), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 5));
                            if (!((TablaModeloJuego) jTable1.getModel()).isInsertando()) {
                                Juego j = new Juego((int) jTable1.getModel().getValueAt(e.getFirstRow(), 0), (String) jTable1.getModel().getValueAt(e.getFirstRow(), 1), (Date) jTable1.getModel().getValueAt(e.getFirstRow(), 2), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 3), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 4), (int)jTable1.getModel().getValueAt(e.getFirstRow(), 5));
                                control.InsertJuego(j);
                                ((TablaModeloJuego) jTable1.getModel()).setJuegoWithRightId(j);
                                jButtonInsert.setEnabled(true);
                            }
                        }
                        break;
                    case TableModelEvent.DELETE:
                        System.out.println("entra aqui");
                        break;
                }
            }
        });
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if (((TablaModeloJuego) jTable1.getModel()).isInsertando()&&jTable1.getSelectedRow()!=(((TablaModeloJuego)jTable1.getModel()).getRowCount()-1)) {
                    int val=JOptionPane.showConfirmDialog(view.this, "Debe completar todos los campos, ¿Que desea salir? (SE BORRARÁ LA FILA)","CAMBIO DE FILA DURANTE AGREGACION",YES_NO_OPTION,WARNING_MESSAGE);
                    switch(val){
                        case 0:case -1:
                            //ENTRA SI O CERRAR RESPECTIVAMENTE
                            ((TablaModeloJuego)jTable1.getModel()).deleteLastRow();
                            ((TablaModeloJuego)jTable1.getModel()).fireTableDataChanged();
                            jButtonInsert.setEnabled(true);
                            break;
                        case 1:
                            //ENTRA NO
                            jTable1.setRowSelectionInterval((((TablaModeloJuego)jTable1.getModel()).getRowCount()-1), (((TablaModeloJuego)jTable1.getModel()).getRowCount()-1));
                            break;
                    }
                }
            }
        });
        jTable1.setModel(model);
        //hacer visible la columna como tipo date y mostrar calendario (IMPORTANTE QUE ESTE DESPUES DEL setModel)
        TableColumn dateColumn = jTable1.getColumnModel().getColumn(2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        dateColumn.setCellEditor(new DatePickerCellEditor(sdf));
        dateColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                if (value != null) {
                    setText(sdf.format(value));
                } else {
                    setText("");
                }
            }
        });
        //<editor-fold defaultstate="collapsed" desc="PARA REFRESCAR">        
//        int numeroColumnas=model.getColumnCount();
//        for(int i=0;i<numeroColumnas;i++){
//            model.removeRow(0);
//        }
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="OCULTAR COLUMNA">
//        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
//        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
//        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
//        jTable1.getColumnModel().getColumn(0).setResizable(false);
        //</editor-fold>
        
        tipos=control.getAllTipos();
        JComboBox combo=new JComboBox();
        for(int k:tipos.keySet()){
            combo.addItem(tipos.get(k));
        }
        TableColumn col=jTable1.getColumnModel().getColumn(NUMERO_COLUMNA_TIPO);
        col.setCellEditor(new DefaultCellEditor(combo));
        col.setCellRenderer(new DefaultTableCellRenderer(){
            
            @Override
            protected void setValue(Object o) {
                setText(String.valueOf(tipos.get((int)o)));
            }
        });
        
        col=jTable1.getColumnModel().getColumn(NUMERO_COLUMNA_CREADOR);
        col.setCellEditor(new TextEditor());
        col.setCellRenderer(new TextRender());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButtonInsert = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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
        jScrollPane2.setViewportView(jTable1);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButtonInsert.setText("ADD");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonInsert);

        jButtonDelete.setText("DELETE");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonDelete);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        ((TablaModeloJuego) jTable1.getModel()).insertRow();
        jTable1.setRowSelectionInterval(jTable1.getModel().getRowCount() - 1, jTable1.getModel().getRowCount() - 1);
        jButtonInsert.setEnabled(false);
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        if(control.removeJuego((int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0))){
            ((TablaModeloJuego)jTable1.getModel()).deleteRow(new Juego((int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0), (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1), (Date) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2),(int)jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 3), (int)jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 4), (int)jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 5)));
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(view.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new view().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
