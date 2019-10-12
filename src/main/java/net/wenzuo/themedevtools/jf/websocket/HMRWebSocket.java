package net.wenzuo.themedevtools.jf.websocket;

import com.jfinal.kit.LogKit;
import net.wenzuo.themedevtools.App;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * @author Catch
 * @date 2019-10-11 18:26
 */
@ServerEndpoint("/hmr.ws")
public class HMRWebSocket {

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
		System.out.println("open==" + session.getId());
		App.sessionSet.add(session);
	}

	/**
	 * 连接关闭后触发的方法
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("close==" + session.getId());
		App.sessionSet.remove(session);
	}

	/**
	 * 发生错误时触发的方法
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		LogKit.info("热加载连接发生错误" + error.getMessage());
		error.printStackTrace();
	}
}
