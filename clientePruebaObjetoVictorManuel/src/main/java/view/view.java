/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import components.TextEditor;
import components.TextRender;
import static constantes.Constantes.*;
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
import controller.ControlLogin;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import view.model.TablaModeloJuego;
import org.jdesktop.swingx.table.DatePickerCellEditor;

/**
 *
 * @author dam2
 */
public class view extends javax.swing.JFrame {

    private CloseableHttpClient httpclient;
    private ControlJuegos control;
    private LinkedHashMap<Integer, String> tipos;

    /**
     * Creates new form view
     */
    public view() {
        initComponents();
        httpclient = HttpClients.createDefault();
        defaultState();
    }

    public void defaultState() {
        getContentPane().add(jPanelSignUpContainer);
        jPanelSignUpContainer.setVisible(false);
        getContentPane().add(jPanelLoginContainer);
        jPanelLoginContainer.setVisible(false);
        getContentPane().add(jPanelTabla);
        jPanelTabla.setVisible(false);
        getContentPane().add(jPanelChooseOption);
    }

    public void ponTablaEnMarcha() {
        control = new ControlJuegos();
        TablaModeloJuego model = new TablaModeloJuego(httpclient);
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                switch (e.getType()) {
                    case TableModelEvent.INSERT:
                        System.out.println("I" + e.getColumn() + " " + e.getFirstRow());
                        break;
                    case TableModelEvent.UPDATE:
                        if (!((TablaModeloJuego) jTable1.getModel()).isInsertando()) {
                            control.UpdateJuego(new Juego((int) jTable1.getModel().getValueAt(e.getFirstRow(), 0), (String) jTable1.getModel().getValueAt(e.getFirstRow(), 1), (Date) jTable1.getModel().getValueAt(e.getFirstRow(), 2), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 3), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 4), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 5)), httpclient);
                        } else {
                            ((TablaModeloJuego) jTable1.getModel()).insertRow((String) jTable1.getModel().getValueAt(e.getFirstRow(), 1), (Date) jTable1.getModel().getValueAt(e.getFirstRow(), 2), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 3), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 4), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 5));
                            if (!((TablaModeloJuego) jTable1.getModel()).isInsertando()) {
                                Juego j = new Juego((int) jTable1.getModel().getValueAt(e.getFirstRow(), 0), (String) jTable1.getModel().getValueAt(e.getFirstRow(), 1), (Date) jTable1.getModel().getValueAt(e.getFirstRow(), 2), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 3), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 4), (int) jTable1.getModel().getValueAt(e.getFirstRow(), 5));
                                control.InsertJuego(j, httpclient);
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
                if (((TablaModeloJuego) jTable1.getModel()).isInsertando() && jTable1.getSelectedRow() != (((TablaModeloJuego) jTable1.getModel()).getRowCount() - 1)) {
                    int val = JOptionPane.showConfirmDialog(view.this, "Debe completar todos los campos, ¿Que desea salir? (SE BORRARÁ LA FILA)", "CAMBIO DE FILA DURANTE AGREGACION", YES_NO_OPTION, WARNING_MESSAGE);
                    switch (val) {
                        case 0:
                        case -1:
                            //ENTRA SI O CERRAR RESPECTIVAMENTE
                            ((TablaModeloJuego) jTable1.getModel()).deleteLastRow();
                            ((TablaModeloJuego) jTable1.getModel()).fireTableDataChanged();
                            jButtonInsert.setEnabled(true);
                            break;
                        case 1:
                            //ENTRA NO
                            jTable1.setRowSelectionInterval((((TablaModeloJuego) jTable1.getModel()).getRowCount() - 1), (((TablaModeloJuego) jTable1.getModel()).getRowCount() - 1));
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

        //<editor-fold defaultstate="collapsed" desc="OCULTAR COLUMNA">
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setResizable(false);
        //</editor-fold>

        tipos = control.getAllTipos(httpclient);
        JComboBox combo = new JComboBox();
        for (int k : tipos.keySet()) {
            combo.addItem(tipos.get(k));
        }
        TableColumn col = jTable1.getColumnModel().getColumn(NUMERO_COLUMNA_TIPO);
        col.setCellEditor(new DefaultCellEditor(combo));
        col.setCellRenderer(new DefaultTableCellRenderer() {

            @Override
            protected void setValue(Object o) {
                setText(String.valueOf(tipos.get((int) o)));
            }
        });

        col = jTable1.getColumnModel().getColumn(NUMERO_COLUMNA_CREADOR);
        col.setCellEditor(new TextEditor(httpclient));
        col.setCellRenderer(new TextRender(httpclient));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelLoginContainer = new javax.swing.JPanel();
        jPanelLoginData = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabelLogUser = new javax.swing.JLabel();
        jTextFieldLoginUser = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabelLogPass = new javax.swing.JLabel();
        jTextFieldLoginPass = new javax.swing.JTextField();
        jLabelLoginProblem = new javax.swing.JLabel();
        jPanelLoginButtons = new javax.swing.JPanel();
        jButtonLogIn = new javax.swing.JButton();
        jButtonClearFieldLog = new javax.swing.JButton();
        jButtonAtrasLog = new javax.swing.JButton();
        jPanelChooseOption = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelTittleAppMenu = new javax.swing.JLabel();
        jLabelWhatYouHaveToDo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonChooseSign = new javax.swing.JButton();
        jButtonChooseLog = new javax.swing.JButton();
        jPanelTabla = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanelBotonesTabla = new javax.swing.JPanel();
        jButtonInsert = new javax.swing.JButton();
        jButtonDelete = new javax.swing.JButton();
        jPanelSignUpContainer = new javax.swing.JPanel();
        jPanelSignUpData = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabelSignUser = new javax.swing.JLabel();
        jTextFieldSignUser = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabelSignPass1 = new javax.swing.JLabel();
        jTextFieldSignPass1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabelSignPass2 = new javax.swing.JLabel();
        jTextFieldSignPass2 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabelSignMail = new javax.swing.JLabel();
        jTextFieldSignMail = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jLabelSignProblem = new javax.swing.JLabel();
        jPanelSignUpButtons = new javax.swing.JPanel();
        jButtonSignIn = new javax.swing.JButton();
        jButtonClearFieldSign = new javax.swing.JButton();
        jButtonAtrasSign = new javax.swing.JButton();

        jPanelLoginContainer.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelLoginContainer.setLayout(new java.awt.BorderLayout());

        jPanelLoginData.setLayout(new java.awt.BorderLayout());

        jLabelLogUser.setText("User");
        jPanel9.add(jLabelLogUser);

        jTextFieldLoginUser.setMinimumSize(new java.awt.Dimension(150, 30));
        jTextFieldLoginUser.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel9.add(jTextFieldLoginUser);

        jPanelLoginData.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jLabelLogPass.setText("Pass");
        jPanel8.add(jLabelLogPass);

        jTextFieldLoginPass.setMinimumSize(new java.awt.Dimension(150, 30));
        jTextFieldLoginPass.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel8.add(jTextFieldLoginPass);

        jPanelLoginData.add(jPanel8, java.awt.BorderLayout.CENTER);

        jLabelLoginProblem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLoginProblem.setMaximumSize(new java.awt.Dimension(400, 20));
        jLabelLoginProblem.setMinimumSize(new java.awt.Dimension(400, 20));
        jLabelLoginProblem.setPreferredSize(new java.awt.Dimension(400, 20));
        jPanelLoginData.add(jLabelLoginProblem, java.awt.BorderLayout.PAGE_END);

        jPanelLoginContainer.add(jPanelLoginData, java.awt.BorderLayout.CENTER);

        jButtonLogIn.setText("Log In");
        jButtonLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogInActionPerformed(evt);
            }
        });
        jPanelLoginButtons.add(jButtonLogIn);

        jButtonClearFieldLog.setText("Clear Fields");
        jButtonClearFieldLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearFieldLogActionPerformed(evt);
            }
        });
        jPanelLoginButtons.add(jButtonClearFieldLog);

        jButtonAtrasLog.setText("Back");
        jButtonAtrasLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasLogActionPerformed(evt);
            }
        });
        jPanelLoginButtons.add(jButtonAtrasLog);

        jPanelLoginContainer.add(jPanelLoginButtons, java.awt.BorderLayout.SOUTH);

        jPanelChooseOption.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelChooseOption.setLayout(new javax.swing.BoxLayout(jPanelChooseOption, javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new java.awt.BorderLayout(0, 20));

        jLabelTittleAppMenu.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTittleAppMenu.setText("VICTOR MANUEL OVIEDO APP");
        jLabelTittleAppMenu.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelTittleAppMenu.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelTittleAppMenu.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabelTittleAppMenu, java.awt.BorderLayout.PAGE_START);

        jLabelWhatYouHaveToDo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelWhatYouHaveToDo.setText("Choose one option: Log in if You have an account or Sign In if You don't");
        jLabelWhatYouHaveToDo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabelWhatYouHaveToDo.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jPanel1.add(jLabelWhatYouHaveToDo, java.awt.BorderLayout.CENTER);

        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout();
        flowLayout1.setAlignOnBaseline(true);
        jPanel2.setLayout(flowLayout1);

        jButtonChooseSign.setText("Sign In");
        jButtonChooseSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseSignActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonChooseSign);

        jButtonChooseLog.setText("Log In");
        jButtonChooseLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseLogActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonChooseLog);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        jPanelChooseOption.add(jPanel1);

        jPanelTabla.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelTabla.setLayout(new java.awt.BorderLayout());

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

        jPanelTabla.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jButtonInsert.setText("ADD");
        jButtonInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonInsertActionPerformed(evt);
            }
        });
        jPanelBotonesTabla.add(jButtonInsert);

        jButtonDelete.setText("DELETE");
        jButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteActionPerformed(evt);
            }
        });
        jPanelBotonesTabla.add(jButtonDelete);

        jPanelTabla.add(jPanelBotonesTabla, java.awt.BorderLayout.SOUTH);

        jPanelSignUpContainer.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelSignUpContainer.setLayout(new javax.swing.BoxLayout(jPanelSignUpContainer, javax.swing.BoxLayout.Y_AXIS));

        jPanelSignUpData.setLayout(new javax.swing.BoxLayout(jPanelSignUpData, javax.swing.BoxLayout.Y_AXIS));

        jLabelSignUser.setText("User");
        jPanel3.add(jLabelSignUser);

        jTextFieldSignUser.setMinimumSize(new java.awt.Dimension(150, 30));
        jTextFieldSignUser.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel3.add(jTextFieldSignUser);

        jPanelSignUpData.add(jPanel3);

        jLabelSignPass1.setText("Pass");
        jPanel4.add(jLabelSignPass1);

        jTextFieldSignPass1.setMinimumSize(new java.awt.Dimension(150, 30));
        jTextFieldSignPass1.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel4.add(jTextFieldSignPass1);

        jPanelSignUpData.add(jPanel4);

        jLabelSignPass2.setText("Pass");
        jPanel5.add(jLabelSignPass2);

        jTextFieldSignPass2.setMinimumSize(new java.awt.Dimension(150, 30));
        jTextFieldSignPass2.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel5.add(jTextFieldSignPass2);

        jPanelSignUpData.add(jPanel5);

        jLabelSignMail.setText("E-Mail");
        jPanel6.add(jLabelSignMail);

        jTextFieldSignMail.setMinimumSize(new java.awt.Dimension(150, 30));
        jTextFieldSignMail.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel6.add(jTextFieldSignMail);

        jPanelSignUpData.add(jPanel6);

        jLabelSignProblem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelSignProblem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabelSignProblem.setMaximumSize(new java.awt.Dimension(400, 20));
        jLabelSignProblem.setMinimumSize(new java.awt.Dimension(400, 20));
        jLabelSignProblem.setPreferredSize(new java.awt.Dimension(400, 20));
        jPanel7.add(jLabelSignProblem);

        jPanelSignUpData.add(jPanel7);

        jPanelSignUpContainer.add(jPanelSignUpData);

        jButtonSignIn.setText("Sign In");
        jButtonSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSignInActionPerformed(evt);
            }
        });
        jPanelSignUpButtons.add(jButtonSignIn);

        jButtonClearFieldSign.setText("Clear Fields");
        jButtonClearFieldSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearFieldSignActionPerformed(evt);
            }
        });
        jPanelSignUpButtons.add(jButtonClearFieldSign);

        jButtonAtrasSign.setText("Back");
        jButtonAtrasSign.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAtrasSignActionPerformed(evt);
            }
        });
        jPanelSignUpButtons.add(jButtonAtrasSign);

        jPanelSignUpContainer.add(jPanelSignUpButtons);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Victor Manuel Oviedo Huertas App");
        setMinimumSize(new java.awt.Dimension(453, 438));
        setPreferredSize(new java.awt.Dimension(453, 438));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.X_AXIS));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonInsertActionPerformed
        ((TablaModeloJuego) jTable1.getModel()).insertRow();
        jTable1.setRowSelectionInterval(jTable1.getModel().getRowCount() - 1, jTable1.getModel().getRowCount() - 1);
        jButtonInsert.setEnabled(false);
    }//GEN-LAST:event_jButtonInsertActionPerformed

    private void jButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteActionPerformed
        if (control.removeJuego((int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0), httpclient)) {
            ((TablaModeloJuego) jTable1.getModel()).deleteRow(new Juego((int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0), (String) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 1), (Date) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 2), (int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 3), (int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 4), (int) jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 5)));
        }
    }//GEN-LAST:event_jButtonDeleteActionPerformed

    private void jButtonChooseSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseSignActionPerformed
        jPanelChooseOption.setVisible(false);
        jPanelSignUpContainer.setVisible(true);
    }//GEN-LAST:event_jButtonChooseSignActionPerformed

    private void jButtonChooseLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseLogActionPerformed
        jPanelChooseOption.setVisible(false);
        jPanelLoginContainer.setVisible(true);
    }//GEN-LAST:event_jButtonChooseLogActionPerformed

    private void jButtonSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSignInActionPerformed
        ControlLogin controlLog = new ControlLogin();
        jLabelSignProblem.setText("");
        String user = jTextFieldSignUser.getText();
        String pass1 = jTextFieldSignPass1.getText();
        String pass2 = jTextFieldSignPass2.getText();
        String mail = jTextFieldSignMail.getText();
        boolean bien = false;
        String mensaje;
        if (!pass1.isEmpty() && !pass2.isEmpty() && !user.isEmpty() && !mail.isEmpty() && pass1.equals(pass2)) {
            //System.out.println("TODO BIEN");
            switch (controlLog.registra(user, pass1, httpclient)) {
                case MENSAJE_TRUE:
                    //AQUI GESTION EMAIL->Si lo hay
                    mensaje = MENSAJE_REGISTRO_CORRECTO;
                    cleanFieldsSign();
                    jPanelSignUpContainer.setVisible(false);
                    jPanelChooseOption.setVisible(true);
                    bien = true;
                    break;
                case MENSAJE_FALSE_REPETICION:
                    //PROBLEMA POR LOGIN:USER COGIDO
                    mensaje = MENSAJE_REGISTRO_FALLIDO_REPETICION;
                    break;
                case MENSAJE_FALSE_REGISTRO:
                    //PROBLEMA POR REGISTRO (FALLO EN SERVER)
                    mensaje = MENSAJE_REGISTRO_FALLIDO_REGISTRO;
                    break;
                default:
                    //CASOS IMPROBABLES-> FALLO DE COMUNICACION CON SERVER
                    mensaje = MENSAJE_REGISTRO_FALLIDO_CONEXION;
                    break;
            }
        } else {
            //System.out.println("ALGO HAY VACIO O LA CONTRASEÑA NO COINCIDE");
            if (!pass1.isEmpty() && !pass2.isEmpty() && !user.isEmpty() && !mail.isEmpty()) {
                //PASS NO COINCIDEN
                mensaje = MENSAJE_PASS_NO_COINCIDEN;
            } else {
                //VACIO
                if (!pass1.isEmpty() && !pass2.isEmpty() && !pass1.equals(pass2)) {
                    //VACIO Y ADEMAS PASS NO COINCIDEN
                    mensaje = MENSAJE_PASS_NO_COINCIDEN_Y_VACIO;
                } else {
                    //SOLO ALGO VACIO
                    mensaje = MENSAJE_CAMPOS_VACIOS;
                }
            }
        }
        if (bien) {
            //SHOW A DIALOG INFORMING YOU EVERYTHING IS ALL RIGHT
            JOptionPane.showConfirmDialog(view.this, mensaje, "INFORMACION", PLAIN_MESSAGE, INFORMATION_MESSAGE);
        } else {
            jLabelSignProblem.setText(mensaje);
        }
    }//GEN-LAST:event_jButtonSignInActionPerformed

    private void jButtonClearFieldSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearFieldSignActionPerformed
        cleanFieldsSign();
    }//GEN-LAST:event_jButtonClearFieldSignActionPerformed

    private void jButtonAtrasSignActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasSignActionPerformed
        cleanFieldsSign();
        jPanelSignUpContainer.setVisible(false);
        jPanelChooseOption.setVisible(true);
    }//GEN-LAST:event_jButtonAtrasSignActionPerformed

    private void jButtonLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogInActionPerformed
        ControlLogin controlLog = new ControlLogin();
        jLabelLoginProblem.setText("");
        String user = jTextFieldLoginUser.getText();
        String pass = jTextFieldLoginPass.getText();
        if (!pass.isEmpty() && !user.isEmpty() && controlLog.login(user, pass, httpclient)) {
            //System.out.println("TODO BIEN");
            cleanFieldsLog();
            jPanelLoginContainer.setVisible(false);
            jPanelTabla.setVisible(true);
            ponTablaEnMarcha();
        } else {
            //System.out.println("ALGO HAY VACIO O LA CONTRASEÑA NO COINCIDE");
            String mensaje;
            if (!pass.isEmpty() && !user.isEmpty()) {
                //USER NO COINCIDEN CON PASS O USER NO EXISTE
                mensaje = MENSAJE_LOGIN_FALLIDO;
            } else {
                //VACIO
                    mensaje = MENSAJE_CAMPOS_VACIOS;
            }
            jLabelLoginProblem.setText(mensaje);
        }
    }//GEN-LAST:event_jButtonLogInActionPerformed

    private void jButtonClearFieldLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearFieldLogActionPerformed
        cleanFieldsLog();
    }//GEN-LAST:event_jButtonClearFieldLogActionPerformed

    private void jButtonAtrasLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAtrasLogActionPerformed
        cleanFieldsLog();
        jPanelLoginContainer.setVisible(false);
        jPanelChooseOption.setVisible(true);
    }//GEN-LAST:event_jButtonAtrasLogActionPerformed

    public void cleanFieldsSign() {
        jTextFieldSignUser.setText("");
        jTextFieldSignPass1.setText("");
        jTextFieldSignPass2.setText("");
        jTextFieldSignMail.setText("");
        jLabelSignProblem.setText("");
    }

    public void cleanFieldsLog() {
        jTextFieldLoginUser.setText("");
        jTextFieldLoginPass.setText("");
        jLabelLoginProblem.setText("");
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
    private javax.swing.JButton jButtonAtrasLog;
    private javax.swing.JButton jButtonAtrasSign;
    private javax.swing.JButton jButtonChooseLog;
    private javax.swing.JButton jButtonChooseSign;
    private javax.swing.JButton jButtonClearFieldLog;
    private javax.swing.JButton jButtonClearFieldSign;
    private javax.swing.JButton jButtonDelete;
    private javax.swing.JButton jButtonInsert;
    private javax.swing.JButton jButtonLogIn;
    private javax.swing.JButton jButtonSignIn;
    private javax.swing.JLabel jLabelLogPass;
    private javax.swing.JLabel jLabelLogUser;
    private javax.swing.JLabel jLabelLoginProblem;
    private javax.swing.JLabel jLabelSignMail;
    private javax.swing.JLabel jLabelSignPass1;
    private javax.swing.JLabel jLabelSignPass2;
    private javax.swing.JLabel jLabelSignProblem;
    private javax.swing.JLabel jLabelSignUser;
    private javax.swing.JLabel jLabelTittleAppMenu;
    private javax.swing.JLabel jLabelWhatYouHaveToDo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelBotonesTabla;
    private javax.swing.JPanel jPanelChooseOption;
    private javax.swing.JPanel jPanelLoginButtons;
    private javax.swing.JPanel jPanelLoginContainer;
    private javax.swing.JPanel jPanelLoginData;
    private javax.swing.JPanel jPanelSignUpButtons;
    private javax.swing.JPanel jPanelSignUpContainer;
    private javax.swing.JPanel jPanelSignUpData;
    private javax.swing.JPanel jPanelTabla;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldLoginPass;
    private javax.swing.JTextField jTextFieldLoginUser;
    private javax.swing.JTextField jTextFieldSignMail;
    private javax.swing.JTextField jTextFieldSignPass1;
    private javax.swing.JTextField jTextFieldSignPass2;
    private javax.swing.JTextField jTextFieldSignUser;
    // End of variables declaration//GEN-END:variables
}
