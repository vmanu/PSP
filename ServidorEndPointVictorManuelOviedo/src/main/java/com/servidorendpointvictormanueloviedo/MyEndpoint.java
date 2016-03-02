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
package com.servidorendpointvictormanueloviedo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mycompany.objetoendpointvictormanueloviedo.MetaMensajeWS;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.objetoendpointvictormanueloviedo.Mensaje;
import com.mycompany.objetoendpointvictormanueloviedo.TipoMensaje;
import static constants.Constantes.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import model.UserWS;

/**
 * @author Victor Manuel
 */
@ServerEndpoint(value = "/websocket")
public class MyEndpoint {

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        UserWS user = new UserWS();
        user.setNombre(session.getRequestParameterMap().get("usuario").get(0));
        session.getUserProperties().put("user", user);
    }

    @OnClose
    public void onClose(Session session) {
        UserWS user = (UserWS) session.getUserProperties().get("user");
        ObjectMapper mapper = new ObjectMapper();
        Mensaje msg = new Mensaje();
        msg.setMensaje(MENSAJE_HA_SALIDO + user.getNombre());
        msg.setFormateado(false);
        String envia = "";
        for (String i : user.getRooms()) {
            msg.setRoom(i);
            try {
                envia = mapper.writeValueAsString(msg);
            } catch (JsonProcessingException ex) {
                Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (Session s : session.getOpenSessions()) {
                try {
                    s.getBasicRemote().sendText(envia);
                } catch (IOException ex) {
                    Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @OnMessage
    public void echoText(String mensaje, Session session) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            MetaMensajeWS meta = mapper.readValue(mensaje,
                    new TypeReference<MetaMensajeWS>() {
                    });
            String room = ((LinkedHashMap) meta.getContenido()).get("room").toString();
            UserWS u = (UserWS) session.getUserProperties().get("user");
            if (!u.getRooms().contains(room)) {
                u.getRooms().add(room);
                session.getUserProperties().put("user", u);
            }
            switch (meta.getTipo()) {
                case MENSAJE:
                    for (Session s : session.getOpenSessions()) {
                        try {
                            String men = mapper.writeValueAsString(meta.getContenido());
                            s.getBasicRemote().sendText(men);
                        } catch (IOException ex) {
                            Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case PRIVADO:
                    String men = mapper.writeValueAsString(meta.getContenido());
                    Mensaje msg = mapper.readValue(men, new TypeReference<Mensaje>() {
                    });
                    String[] usuarios = msg.getRoom().split("-");
                    boolean encontrado = false;
                    ArrayList<Session> openSesion = new ArrayList(session.getOpenSessions());
                    int indexMuestra = ((UserWS) session.getUserProperties().get("user")).getNombre().equals(usuarios[0]) ? 1 : 0;
                    for (int i = 0; i < openSesion.size() && !encontrado; i++) {
                        if (usuarios[indexMuestra].equals(((UserWS) openSesion.get(i).getUserProperties().get("user")).getNombre())) {
                            try {
                                openSesion.get(i).getBasicRemote().sendText(men);
                                encontrado = true;
                            } catch (IOException ex) {
                                Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    if (msg.isEnforceCreation()) {
                        msg.setFrom(usuarios[1]);
                        men = mapper.writeValueAsString(msg);
                    }
                    if (encontrado) {
                        session.getBasicRemote().sendText(men);
                    }
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(MyEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @OnMessage
    public void echoBinary(byte[] data, Session session) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            builder.append(b);
        }

        for (Session s : session.getOpenSessions()) {
            s.getBasicRemote().sendBinary(ByteBuffer.wrap(data));
        }
    }
}
