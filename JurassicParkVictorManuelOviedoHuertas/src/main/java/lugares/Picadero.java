/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lugares;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static lugares.Constantes.*;
import static lugares.Lugares.*;

/**
 *
 * @author Victor Manuel Oviedo Huertas
 */
public class Picadero {
    private List <QuiquiRoom> salas;
    
    public Picadero(){
        salas=Collections.synchronizedList(new ArrayList());
        for(int i=0;i<NUMERO_DE_SALAS_PICANTES;i++){
            //CONSTRUIR Y AÑADIR LAS SALAS
        }
    }
}
