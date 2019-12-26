package net.wenzuo.themedevtools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.*;
import net.wenzuo.themedevtools.jf.Const;
import net.wenzuo.themedevtools.jf.WenzuoServer;

import javax.websocket.Session;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * @param ret api 返回的 body 直接转 Map 的值
     */
    public static void error(Map ret) {
        int code= (int) ret.get("code");
        String msg= (String) ret.get("msg");
        String message = StrUtil.format("错误：code:{},msg:{}", code, msg);
        LogKit.error(message);
    }

    /**
     * 获取请求对象结果,负责与服务器通信
     *
     * @param url
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T get(String url, String key) {
        url=URLUtil.decode(url);
        if (cache && cacheMap.containsKey(url)) {
            return (T) cacheMap.get(url);
        }
        url=PropKit.get("serverUrl", "http://t.wenzuo.net:8000/api/devtools") + url;
        String body = HttpUtil.post(url, App.paraMap);
        Map ret=JsonKit.parse(body,Map.class);
        int code= (int) ret.get("code");
        if (code!=200){
            App.error(ret);
            System.exit(-1);
        }
        T value= (T) ret.get(key);
        if (cache) {
            cacheMap.put(url, value);
        }
        return value;
    }

    public static <T> T get(String url, Kv kv, String key) {
        if (!url.contains("?") && kv != null) {
            url += "?" + HttpUtil.toParams(kv);
        }
        return get(url, key);
    }

}

