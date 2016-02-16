/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clienteendpointvictormanueloviedo;

import java.io.IOException;
import java.net.URI;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author oscar
 */
public class WebsocketClient {


    public static void main(String[] args) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            String uri = "ws://localhost:8080/websocket?usuario=juan";
            System.out.println("Connecting to " + uri);
            Session session = container.connectToServer(MyClient.class, URI.create(uri));
            session.getBasicRemote().sendText(" me voy");
            session.close();
            
            Scanner sc = new Scanner(System.in);
            
            sc.nextLine();
        } catch (DeploymentException ex) {
            Logger.getLogger(WebsocketClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WebsocketClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
