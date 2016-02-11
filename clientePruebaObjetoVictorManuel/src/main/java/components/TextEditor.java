/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

//import static components.ColorEditor.EDIT;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author oscar
 */
public class TextEditor extends AbstractCellEditor
                         implements TableCellEditor{

    Integer text;
    JButton button;
    NewJDialog dialog;
    protected static final String EDIT = "edit";
    
    
     public TextEditor() {
        //Set up the editor (from the table's point of view),
        //which is a button.
        //This button brings up the color chooser dialog,
        //which is the editor from the user's point of view.
        button = new JButton();
        button.setActionCommand(EDIT);

        button.setBorderPainted(false);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (EDIT.equals(e.getActionCommand())) {
            //The user has clicked the cell, so
            //bring up the dialog.
            //button.setBackground(currentColor);
            //colorChooser.setColor(currentColor);
            dialog.setVisible(true);

            //Make the renderer reappear.
            fireEditingStopped();

        } 
            }
        });
        //button.setBorderPainted(false);

        //Set up the dialog that the button brings up.
        
        dialog = new NewJDialog(null, true);
    }
    
    @Override
    public Object getCellEditorValue() {
        if (dialog.getValor()==null||dialog.getValor().equals(""))
           fireEditingCanceled();
        return dialog.getValor();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        text = (Integer)value;
        return button;
    }
    
}
