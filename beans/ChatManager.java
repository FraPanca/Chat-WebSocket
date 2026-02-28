package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.Session;

public class ChatManager {

	// group, session
	private static Map<String, List<Session>> onlineUsers = new ConcurrentHashMap<>();
	private static Map<String, List<ResponseWs>> chats = new ConcurrentHashMap<>();
	
	public static synchronized void loginUser(String group, Session id) {
		onlineUsers.computeIfAbsent(group, k -> new ArrayList<>()).add(id);
	    chats.computeIfAbsent(group, k -> new ArrayList<>());
	}
	
	public static synchronized void logoutUser(Session id) {
		String group = getGroup(id);
		if(group == null) return;
		
		List<Session> users = onlineUsers.get(group);
		users.removeIf(user -> user.equals(id));
		
		if(users.isEmpty()) onlineUsers.remove(group);
	}
	
	private static synchronized String getGroup(Session id) {

		for(Entry<String, List<Session>> entry : onlineUsers.entrySet()) {
			for(Session user : entry.getValue()) {
				if(user.equals(id)) return entry.getKey();
			}
		}
		
		return null;
	}
	
	public static synchronized List<Session> getUserByGroup(String group) {
		for(Entry<String, List<Session>> entry : onlineUsers.entrySet()) {
			if(entry.getKey().equals(group)) return entry.getValue();
		}
		
		return new ArrayList<>();
	}
	
	public static synchronized List<ResponseWs> getChat(String group) {
		return chats.get(group);
	}
	
	public static synchronized void addMsg(String group, ResponseWs msg) {
		List<ResponseWs> msgs = chats.get(group);
		msgs.add(msg);
		
		chats.replace(group, msgs);
	}
}
