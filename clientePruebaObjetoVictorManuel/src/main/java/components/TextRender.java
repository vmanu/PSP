/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import controller.ControlCreator;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * @author oscar
 */
public class TextRender extends DefaultTableCellRenderer{
    private CloseableHttpClient httpclient;
    private ControlCreator control;
    
    public TextRender(CloseableHttpClient httpclient) {
        super();
        control=new ControlCreator();
        this.httpclient=httpclient;
    }
   
 
    public void setValue(Object value) {
        String muestra=(value == null) ? "" : control.getAllCreators(httpclient).get(value);
        setText(muestra);
    }
    
    
}
