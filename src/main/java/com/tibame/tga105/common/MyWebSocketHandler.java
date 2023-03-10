package com.tibame.tga105.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.ServerEndpoint;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {

	private static final List<WebSocketSession> sessions = new ArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 接收到客戶端消息
		String payload = message.getPayload();
		
		Map<String, String>map = new HashMap<String, String>();
		
		
		// 將消息發送給所有已連接的客戶端
		for (WebSocketSession s : sessions) {
			System.out.println(s);
			if (s.isOpen() && !s.getId().equals(session.getId())) {
				s.sendMessage(new TextMessage(payload));
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}
