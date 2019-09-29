package net.wenzuo.themedevtools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.dialect.console.ConsoleLog;
import cn.hutool.log.level.Level;
import com.jfinal.kit.*;
import com.jfinal.template.source.ClassPathSource;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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


    public static void main(String[] args) {
        Config.init();
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
        String body = HttpUtil.get(PropKit.get("baseUrl", "https://wenzuo.net/api/devtools") + url);
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

