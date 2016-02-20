/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package constants;

/**
 *
 * @author dam2
 */
public class Constantes {

    public static final String SQL_SENTENCIA_DAME_TODO = "SELECT * FROM GAME";
    public static final String SQL_SENTENCIA_DAME_TODO_LOGIN = "SELECT Log FROM LOGIN";
    public static final String SQL_SENTENCIA_UPDATE = "update GAME set NOMBRE = ?,FECHA_CREACION=?, VENTAS=?,GAME_TYPE=?,GAME_CREATOR=? where ID = ?";
    public static final String SQL_SENTENCIA_INSERT = "INSERT INTO GAME (nombre,fecha_creacion,ventas,game_type,game_creator) values (?,?,?,?,?)";
    public static final String SQL_SENTENCIA_REGISTRA_LOGIN = "INSERT INTO LOGIN (Log,Pass) values (?,?)";
    public static final String SQL_SENTENCIA_DAME_LOGIN = "SELECT Pass FROM LOGIN WHERE Log=? AND Activo='true'";
    public static final String SQL_SENTENCIA_ULTIMO_ID="SELECT LAST_INSERT_ROWID()";
    public static final String SQL_SENTENCIA_DELETE = "DELETE FROM GAME WHERE id=?";
    public static final String SQL_SENTENCIA_DAME_TODO_TIPOS = "SELECT * FROM GAMETYPE";
    public static final String SQL_SENTENCIA_DAME_TODO_CREADORES = "SELECT * FROM GAMECREATOR";
    public static final String SQL_SENTENCIA_ACTIVA_LOGIN="update LOGIN set ACTIVO = 'true' where Log=?";
    public static final String SQL_SENTENCIA_DELETE_LOGIN="DELETE FROM LOGIN WHERE Log=?";
    
    
    public static final String EMAIL_FROM="vmanuoveuh@hotmail.com";
    public static final String EMAIL_LOCALHOST="smtp01.educa.madrid.org";
    public static final int EMAIL_PORT=25;
    public static final String EMAIL_SUBJECT="Registro TABLA VICTOR";
    public static final String EMAIL_MSG="Este email es para la confirmación de un registro.\nSi usted no se ha registrado, por favor, ignore este mensaje.\n\nEn caso de ser el propietario de este registro, haga click en el siguiente link:\n";
    public static final String PARTE_ESTANDAR_ENLACE="http://localhost:8080/ControllerConfirmRegister?user=";
}
