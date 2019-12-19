package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import net.wenzuo.themedevtools.App;

import java.util.List;

/**
 * @author Catch
 * @date 2019-10-12 16:44
 */
public class TagsDirective extends BaseDirective {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			List tags = App.get("/tags", "tags");
			scope.setLocal("tags", tags);
		} else {
			error(writer, "#tags() 无需指定参数");
			return;
		}
		stat.exec(env, scope, writer);
		scope.removeLocal("tags");
	}
}
