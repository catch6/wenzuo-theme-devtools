package net.wenzuo.themedevtools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.dialect.console.ConsoleLog;
import com.jfinal.kit.*;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.source.ClassPathSource;
import net.wenzuo.themedevtools.jf.JFConfig;
import net.wenzuo.themedevtools.jf.WenzuoServer;
import net.wenzuo.themedevtools.watcher.ThemeWatcher;
import org.jboss.logging.BasicLogger;
import org.xnio._private.Messages_$logger;

import javax.websocket.Session;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Catch
 * @date 2019-09-22 10:30
 */
@SuppressWarnings("unchecked")
public class App {
	public static boolean cache;
	public static String token;
	public static Map<String, Object> cacheMap = new HashMap<>();
	public static Map<String, Object> site = new HashMap<>();
	public static Map<String, Object> owner = new HashMap<>();
	public static boolean isOwner;
	public static String ownerId;
	public static String siteId;
	public static Map<String, Object> paraMap = new HashMap<>();
	public static Set<Session> sessionSet = new HashSet<>();

	public static void main(String[] args) {
		Config.init();
		WenzuoServer.start(JFConfig.class.getName(), PropKit.getInt("port"), false);
		ThemeWatcher.watch();
	}

	/**
	 * 抛出错误
	 *
	 * @param obj api 返回的 body 直接转 JSONObject 的值
	 */
	public static void error(JSONObject obj) {
		String message = StrUtil.format("错误：code:{},msg:{}", obj.getInt("code"), obj.getStr("msg"));
		LogKit.error(message);
	}

	/**
	 * 获取请求对象结果,负责与服务器通信
	 *
	 * @param url
	 * @param key
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T get(String url, String key, Class<T> type) {
		if (cache && cacheMap.containsKey(url)) {
			return (T) cacheMap.get(url);
		}
		String body = HttpUtil.get(PropKit.get("serverUrl", "https://wenzuo.net/api/devtools") + url, App.paraMap);
//		String body = HttpUtil.get(PropKit.get("baseUrl", "http://localhost:9090/api/devtools") + url, App.paraMap);
		JSONObject obj = JSONUtil.parseObj(body);
		if (obj.getInt("code") != 200) {
			App.error(obj);
			System.exit(-1);
		}
		T value = obj.get(key, type);
		if (cache) {
			cacheMap.put(url, value);
		}
		return value;
	}

	public static <T> T get(String url, Kv kv, String key, Class<T> type) {
		if (!url.contains("?") && kv != null) {
			url += "?" + HttpUtil.toParams(kv);
		}
		return get(url, key, type);
	}

}

