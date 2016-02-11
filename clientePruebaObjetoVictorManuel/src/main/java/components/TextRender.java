/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import controller.ControlCreator;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author oscar
 */
public class TextRender extends DefaultTableCellRenderer{

    private ControlCreator control;
    
    public TextRender() {
        super();
        control=new ControlCreator();
    }
   
 
    public void setValue(Object value) {
        String muestra=(value == null) ? "" : control.getAllCreators().get(value);
        setText(muestra);
    }
    
    
}
