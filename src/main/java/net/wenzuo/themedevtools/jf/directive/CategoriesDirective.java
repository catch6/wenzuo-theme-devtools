package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import net.wenzuo.themedevtools.App;

import java.util.List;

/**
 * @author Catch
 * @date 2019-10-12 15:20
 */
public class CategoriesDirective extends BaseDirective {

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			List categories = App.get("/categories", "categories", List.class);
			scope.setLocal("categories", categories);
		} else {
			error(writer, "#categories() 无需指定参数");
			return;
		}
		stat.exec(env, scope, writer);
		scope.removeLocal("categories");
	}
}
