package net.wenzuo.themedevtools;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jfinal.kit.Kv;
import com.jfinal.kit.LogKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import net.wenzuo.themedevtools.jf.JFConfig;
import net.wenzuo.themedevtools.jf.WenzuoServer;
import net.wenzuo.themedevtools.watcher.ThemeWatcher;

import javax.websocket.Session;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Catch
 * @date 2019-09-22 10:30
 */
@SuppressWarnings("unchecked")
public class App {
    public static boolean cache;
    public static Map<String, Object> cacheMap = new HashMap<>();
    public static Map<String, Object> site = new HashMap<>();
    public static Map<String, Object> master = new HashMap<>();
    public static Map<String, Object> visitor = new HashMap<>();
    public static boolean isMaster;
    public static Map<String, Object> paraMap = new HashMap<>();
    public static Set<Session> sessionSet = new HashSet<>();

    public static final Prop prop=PropKit.use(new File("config.txt"))
            .appendIfExists(new File("config.dev.txt"));

    public static void main(String[] args) {
        System.setProperty("org.jboss.logging.provider", "jdk");
        java.util.logging.Logger.getLogger("io.undertow").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.jboss").setLevel(java.util.logging.Level.OFF);
        java.util.logging.Logger.getLogger("org.xnio").setLevel(java.util.logging.Level.OFF);
		int port=PropKit.getInt("port",8080);
        WenzuoServer.start(port);
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
        url=URLUtil.decode(url);
        if (cache && cacheMap.containsKey(url)) {
            return (T) cacheMap.get(url);
        }
        url=PropKit.get("serverUrl", "http://t.wenzuo.net:8000/api/devtools") + url;
        String body = HttpUtil.post(url, App.paraMap);
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

