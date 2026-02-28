package beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.*;
import javax.websocket.server.*;

import com.google.gson.Gson;

import it.unibo.tw.web.beans.ChatManager;
import it.unibo.tw.web.beans.RequestWs;
import it.unibo.tw.web.beans.ResponseWs;


@ServerEndpoint("/actions")
public class WebSocketServer {
	
	@OnOpen
	public void open(Session session) {}
	
	@OnClose
	public void close(Session session) {
		ChatManager.logoutUser(session);
	}
	
	@OnError
	public void onError(Throwable error) {}
	
	@OnMessage
	public void handleMessage(String message, Session session) throws Exception {
		Gson gson = new Gson();
		ResponseWs response = new ResponseWs();
		
		RequestWs request = new RequestWs();
		request = gson.fromJson(message, RequestWs.class);
		
		response.setGroup(request.getGroup());
		
		if(request.getAction().equals("accedi")) {
			ChatManager.loginUser(response.getGroup(), session);
			
			List<ResponseWs> prevMsg = ChatManager.getChat(response.getGroup());
			if(prevMsg.isEmpty()) {
				ResponseWs benvenuto = new ResponseWs();
				
				benvenuto.setGroup(response.getGroup());
				benvenuto.setMsg("Chat creata!");
				
				prevMsg = new ArrayList<>();
				prevMsg.add(benvenuto);
				
				ChatManager.addMsg(response.getGroup(), benvenuto);
			}
			response.setPreviousMsg(prevMsg);
			
			session.getBasicRemote().sendText(gson.toJson(response));
		} else {
			
			response.setNome(request.getNome());
			response.setMsg(request.getMsg());
			
			ResponseWs msg = new ResponseWs();
			msg.setGroup(response.getGroup());
			msg.setNome(request.getNome());
			msg.setMsg(request.getMsg());

			ChatManager.addMsg(response.getGroup(), msg);
			
			// broadcast
			try {
				for(Session s : ChatManager.getUserByGroup(response.getGroup())) {
					if(s.isOpen()) {
						s.getBasicRemote().sendText(gson.toJson(response));
					}
				}
			} catch(IOException e) {
				System.err.println("Errore");
			}
		}
	}
	
}
