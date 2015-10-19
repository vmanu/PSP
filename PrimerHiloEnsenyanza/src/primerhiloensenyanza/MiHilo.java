/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primerhiloensenyanza;

/**
 *
 * @author dam2
 */
public class MiHilo implements Runnable{

    private int id;
    private StringBuffer sb;
    
    public MiHilo (int id,String text,StringBuffer sb){
        this.id=id;
        this.sb=sb;
        sb.append(text);
    }
    
    @Override
    public void run(){
        System.out.println("hello world "+id+sb.toString());
    }
}
