package net.wenzuo.themedevtools.jf;

import com.jfinal.handler.Handler;
import com.jfinal.kit.PropKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author Catch
 * @date 2019-08-28 20:38
 */
public class GlobalHandler extends Handler {
	private static final String KEY = PropKit.get("key");

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		String[] targets = target.split("/");
		if (targets.length > 1) {
			String actionKey = targets[1];
			if (actionKey.equals(KEY)) {
				request.setAttribute("attrs", Arrays.copyOfRange(targets, 1, targets.length));
				target = "/site/index";
			}
		}
		next.handle(target, request, response, isHandled);
	}
}

