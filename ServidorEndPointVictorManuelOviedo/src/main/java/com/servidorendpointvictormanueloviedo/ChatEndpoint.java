package com.servidorendpointvictormanueloviedo;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{room}")
public class ChatEndpoint {
	private final Logger log = Logger.getLogger(getClass().getName());

	@OnOpen
	public void open(final Session session, @PathParam("room") final String room) {
		log.info("session openend and bound to room: " + room);
		session.getUserProperties().put("room", room);
	}

	@OnMessage
	public void onMessage(final Session session, final String chatMessage) {
		String room = (String) session.getUserProperties().get("room");
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
					) {
					s.getBasicRemote().sendText(chatMessage);
				}
			}
		} catch (IOException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
}
