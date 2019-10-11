package net.wenzuo.themedevtools.jf.websocket;

import com.jfinal.kit.LogKit;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Catch
 * @date 2019-10-11 18:26
 */
@ServerEndpoint("/autoReload.ws")
public class MyWebSocket {
	public Session session;

	@OnMessage
	public void message(String message, Session session) {
		for (Session s : session.getOpenSessions()) {
			s.getAsyncRemote().sendText(message);
		}
	}

	/**
	 * 连接建立后触发的方法
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		LogKit.info("====== onOpen:" + session.getId() + " ======");
		WebSocketMapUtil.put(session.getId(), this);
	}

	/**
	 * 连接关闭后触发的方法
	 */
	@OnClose
	public void onClose() {
		WebSocketMapUtil.remove(session.getId());
		LogKit.info("====== onClose:" + session.getId() + " ======");
	}

	/**
	 * 发生错误时触发的方法
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		LogKit.info(session.getId() + "连接发生错误" + error.getMessage());
		error.printStackTrace();
	}
}
