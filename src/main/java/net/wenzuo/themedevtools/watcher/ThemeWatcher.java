package net.wenzuo.themedevtools.watcher;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.watch.WatchMonitor;
import cn.hutool.core.io.watch.Watcher;
import net.wenzuo.themedevtools.App;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Map;

/**
 * @author Catch
 * @date 2019-10-12 10:00
 */
public class ThemeWatcher {
	public static void watch() {
		File file = new File("theme");
		WatchMonitor watchMonitor = WatchMonitor.create(file);

		watchMonitor.setWatcher(new Watcher() {
			@Override
			public void onCreate(WatchEvent<?> event, Path currentPath) {
				update();
			}

			@Override
			public void onModify(WatchEvent<?> event, Path currentPath) {
				update();
			}

			@Override
			public void onDelete(WatchEvent<?> event, Path currentPath) {
				update();
			}

			@Override
			public void onOverflow(WatchEvent<?> event, Path currentPath) {
				update();
			}
		});

		//设置监听目录的最大深入，目录层级大于制定层级的变更将不被监听，默认只监听当前层级目录
		watchMonitor.setMaxDepth(5);
		//启动监听
		watchMonitor.start();
	}

	private static void update() {
		App.sessionSet.forEach(session -> session.getAsyncRemote().sendText("reload"));
	}
}
