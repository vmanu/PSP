/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CreatorsDAO;
import java.util.LinkedHashMap;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 *
 * @author Victor
 */
public class ControlCreator {

    public LinkedHashMap<Integer, String> getAllCreators(CloseableHttpClient httpclient) {
        return new CreatorsDAO().getAllCreators(httpclient);
    }
    
}
