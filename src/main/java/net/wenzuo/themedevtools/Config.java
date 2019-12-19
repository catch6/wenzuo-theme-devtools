package net.wenzuo.themedevtools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.log.dialect.console.ConsoleLog;
import cn.hutool.log.level.Level;
import com.jfinal.kit.Kv;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.template.source.ClassPathSource;

import java.io.File;
import java.util.Date;

/**
 * @author Catch
 * @date 2019-09-22 13:44
 */
@SuppressWarnings("unchecked")
public class Config {
	public static void init() {
		ConsoleLog.setLevel(Level.ERROR);
		initConfig();
		verifyConfig();
		initStaticObjects();
	}

	private static void initConfig() {
		File configFile = new File("config.txt");
		if (!configFile.exists()) {
			FileUtil.touch(configFile);
			String configTemplate = new ClassPathSource("configTemplate.txt").getContent().toString();
			FileUtil.writeUtf8String(configTemplate, configFile);
		}
	}

	private static void verifyConfig() {
		if (StrKit.isBlank(PropKit.get("mobile"))) {
			System.out.println("手机号不能为空！请检查您的配置文件。");
			System.exit(-1);
		}
		if (StrKit.isBlank(PropKit.get("password"))) {
			System.out.println("密码不能为空！请检查您的配置文件。");
			System.exit(-1);
		}
		if (StrKit.isBlank(PropKit.get("key"))) {
			System.out.println("站点key不能为空！请检查您的配置文件。");
			System.exit(-1);
		}
	}

	private static void initStaticObjects() {
		App.cache = PropKit.getBoolean("cache", true);
		String key=PropKit.get("key");
		boolean forceVisitor=PropKit.getBoolean("forceVisitor",false);

		String token = App.get("/login",
			Kv.by("mobile", PropKit.get("mobile"))
				.set("password", PropKit.get("password"))
				.set("key", PropKit.get("key")),
			"token"
		);

		// App.paraMap 在后续请求中将一直携带该参数
		App.paraMap.put("token", token);
		App.paraMap.put("key", key);
		App.paraMap.put("forceVisitor", forceVisitor);

		App.site = App.get("/site", "site");

		App.visitor= App.get("/visitor", "visitor");

		App.master= App.get("/master", "master");

		App.isMaster = App.master.get("id").equals(App.visitor.get("id"));
		if (forceVisitor){
			App.isMaster=false;
		}
	}

}

