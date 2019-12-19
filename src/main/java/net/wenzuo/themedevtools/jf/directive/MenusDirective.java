package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import net.wenzuo.themedevtools.App;
import net.wenzuo.themedevtools.jf.Const;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Catch
 * @date 2019-10-12 14:51
 */
public class MenusDirective extends BaseDirective {
	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			List menus = App.get("/menus", "menus");
			scope.setLocal("menus", menus);
		} else {
			error(writer, "#menus() 无需指定参数");
			return;
		}
		stat.exec(env, scope, writer);
		scope.removeLocal("menus");
	}
}
