package net.wenzuo.themedevtools.jf.websocket;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Catch
 * @date 2019-10-11 18:26
 */

/**
 * for (MyWebSocket mws : webSocketMap.values()) {
 * msw.session.getAsyncRemote().sendText(message);
 * }
 * <p>
 * 上面的代码是向所有客户端发消息，你也可以通过 id 获取某一个客户端，单独向它来发信息，例如：
 * webSocketMap.get(sessionId).session.getAsyncRemote().sendText(message);
 * <p>
 * 将上面的代码放在定时器中即可，注意，在定时器中你要有一个对象持有 webSocketMap 这个对象
 */
public class WebSocketMapUtil {
	public static ConcurrentMap<String, MyWebSocket> webSocketMap = new ConcurrentHashMap<>();

	public static void put(String key, MyWebSocket myWebSocket) {
		webSocketMap.put(key, myWebSocket);
	}

	public static MyWebSocket get(String key) {
		return webSocketMap.get(key);
	}

	public static void remove(String key) {
		webSocketMap.remove(key);
	}

	public static Collection<MyWebSocket> getValues() {
		return webSocketMap.values();
	}
}
