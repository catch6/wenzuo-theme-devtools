package net.wenzuo.themedevtools.jf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.handler.Handler;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Console;
import java.util.Arrays;

/**
 * @author Catch
 * @date 2019-08-28 20:38
 */
public class GlobalHandler extends Handler {
	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		String[] targets = target.split(StrUtil.SLASH);
		if (targets.length > 1) {
			String actionKey = targets[1];
			if (actionKey.equals(Const.KEY)) {
				request.setAttribute(Const.ATTRS, Arrays.copyOfRange(targets, 1, targets.length));
				target = Const.TARGET;
			}
		}
		next.handle(target, request, response, isHandled);
	}
}

