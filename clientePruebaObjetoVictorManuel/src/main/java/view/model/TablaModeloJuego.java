/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.model;

import static constants.Constantes.*;
import controller.ControlCreator;
import controller.ControlJuegos;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import com.objetopruebavictormanuel.Juego;

/**
 *
 * @author dam2
 */
public class TablaModeloJuego extends AbstractTableModel {

    private ArrayList<Juego> juegos;
    private boolean insertando;

    public TablaModeloJuego() {
        super();
        insertando = false;
        ControlJuegos control = new ControlJuegos();
        juegos = control.getAllJuegos();
    }

    @Override
    public String getColumnName(int i) {
        String columnName = "";
        switch (i) {
            case 0:
                columnName = "ID";
                break;
            case 1:
                columnName = "NOMBRE";
                break;
            case 2:
                columnName = "FECHA";
                break;
            case 3:
                columnName = "VENTAS";
                break;
            case 4:
                columnName = "TIPO";
                break;
            case 5:
                columnName = "CREADOR";
                break;
        }
        return columnName;
    }

    @Override
    public Object getValueAt(int row, int col) {
        Juego game = juegos.get(row);
        Object o = null;
        switch (col) {
            case 0:
                o = game.getId();
                break;
            case 1:
                o = game.getNombre();
                break;
            case 2:
                o = game.getFecha_creacion();
                break;
            case 3:
                o = game.getVentas();
                break;
            case 4:
                o = game.getTipo();
                break;
            case 5:
                o = game.getCreador();
                break;
        }
        return o;
    }

    @Override
    public void setValueAt(Object o, int row, int col) {
        if(row>=0&&col>0){
            Juego game = juegos.get(row);
            switch (col) {
                /*case 0:
                    game.setId((int)o);
                    break;*/
                case 1:
                    game.setNombre((String) o);
                    break;
                case 2:
                    game.setFecha_creacion((Date) o);
                    break;
                case 3:
                    game.setVentas(Integer.parseInt((String)o));
                    break;
                case 4:
                    LinkedHashMap<Integer,String> map=new ControlJuegos().getAllTipos();
                    for(Integer k:map.keySet()){
                        if(map.get(k).equals((String)o)){
                            game.setTipo(k);
                        }
                    }
                    break;
                case 5:
                    LinkedHashMap<Integer,String> creadores=new ControlCreator().getAllCreators();
                    for(Integer k:creadores.keySet()){
                        if(creadores.get(k).equals((String)o)){
                            game.setCreador(k);
                        }
                    }
                    break;
            }
            fireTableCellUpdated(row, col);
        }
    }

    public void deleteRow(Juego j) {
        int p=0;
        for(int i=0;i<juegos.size();i++){
            if(juegos.get(i).getId()==j.getId()){
                p=i;
            }
        }
        juegos.remove(p);
        fireTableDataChanged();
    }

    public void insertRow(String nombre, Date fecha,int ventas, int tipo, int creador) {
        if (insertando) {
            if (!nombre.isEmpty() && fecha != null&&ventas!=-1&&tipo!=-1&&creador!=-1) {
                insertando=false;
            }
        }
        fireTableRowsInserted(juegos.size() - 1, juegos.size() - 1);
    }

    public void insertRow() {
        insertando = true;
        juegos.add(new Juego(-1, "", null,-1,-1,-1));
        fireTableRowsInserted(juegos.size() - 1, juegos.size() - 1);
    }

    @Override
    public int getRowCount() {
        return juegos.size();
    }

    @Override
    public int getColumnCount() {
        return NUMERO_COLUMNAS;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return col != 0; //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isInsertando() {
        return insertando;
    }

    public void setJuegoWithRightId(Juego j) {
        juegos.remove(juegos.size()-1);
        juegos.add(j);
    }

    public void deleteLastRow() {
        juegos.remove(juegos.size()-1);
        insertando=false;
    }
}
