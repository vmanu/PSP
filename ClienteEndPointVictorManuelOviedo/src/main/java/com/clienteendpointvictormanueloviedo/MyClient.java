/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package com.clienteendpointvictormanueloviedo;

import com.mycompany.objetoendpointvictormanueloviedo.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import static com.mycompany.objetoendpointvictormanueloviedo.TipoMensaje.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCode;
import javax.websocket.DeploymentException;

/**
 * @author Arun Gupta
 */
@ClientEndpoint
public class MyClient {

    private ClientManager client;
    private Session userSession;
    private MessageHandler messageHandler;
    private ArrayList<String> rooms;

    public MyClient(URI endpointURI) {
        try {
            rooms = new ArrayList();
            client = ClientManager.createClient();
            client.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.userSession = session;
        System.out.println("Connected to endpoint: " + session.getBasicRemote());

    }

    public void addMessageHandler(final MessageHandler msgHandler) {
        messageHandler = msgHandler;
    }

    @OnClose
    public void disconnect() {
        try {
            messageHandler = null;
            userSession.close();
        } catch (IOException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(Mensaje message) {
        try {
            MetaMensajeWS ms = new MetaMensajeWS();
            ms.setTipo(MENSAJE);
            ms.setContenido(message);
            ObjectMapper mapper = new ObjectMapper();
            String men = mapper.writeValueAsString(ms);
            userSession.getAsyncRemote().sendText(men);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendPrivateMessage(Mensaje message) {
        try {
            MetaMensajeWS ms = new MetaMensajeWS();
            ms.setTipo(PRIVADO);
            ms.setContenido(message);
            ObjectMapper mapper = new ObjectMapper();
            String men = mapper.writeValueAsString(ms);
            userSession.getAsyncRemote().sendText(men);
        } catch (JsonProcessingException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void processMessage(String message) {
        if (messageHandler != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                Mensaje mensaje = null;
                mensaje = mapper.readValue(message, new TypeReference<Mensaje>() {});
                messageHandler.handleMessage(mensaje);
            } catch (IOException ex) {
                Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @OnError
    public void processError(Throwable t) {
        t.printStackTrace();
    }

    public static interface MessageHandler {

        public void handleMessage(Mensaje message);
    }

}
