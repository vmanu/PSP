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
import modelo.Dinosaurio;

/**
 *
 * @author Victor Manuel Oviedo Huertas
 */
public class Bosque {
    private List <Arbol> arboles;
    
    public Bosque(){
        arboles=Collections.synchronizedList(new ArrayList());
        for(int i=0;i<TAMANO_BOSQUE;i++){
            arboles.add(new Arbol());
        }
    }
    
    public void entrar(Dinosaurio dino){
        dino.setLugarActual(BOSQUE);
        for(Arbol arb:arboles){
            if(arb.entrar(dino)){
                break;
            }
        }
    }

    void para() {
        for(Arbol arb:arboles){
            arb=null;
        }
    }
}
