/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuracion;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Victor
 */
public class Configuracion {

    private static Configuracion config;
    private String urlbase;
    
    private Configuracion() {
    }

    public static Configuracion getInstance() {
        if (config == null) {
            try {
                Yaml yaml = new Yaml();
                config = (Configuracion) yaml.loadAs(new FileInputStream("configuration.yml"), Configuracion.class);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return config;
    }

    public String getUrlbase() {
        return urlbase;
    }

    public void setUrlbase(String urlbase) {
        this.urlbase = urlbase;
    }
}
