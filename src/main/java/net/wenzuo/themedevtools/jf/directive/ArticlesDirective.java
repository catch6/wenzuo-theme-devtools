package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.kit.Kv;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import net.wenzuo.themedevtools.App;

import java.util.Map;

/**
 * @author Catch
 * @date 2019-10-14 15:31
 */
public class ArticlesDirective extends BaseDirective {
	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			error(writer, "#articles(type[,value],pageNumber,pageSize) 参数错误");
			return;
		} else if (len == 3) {
			String type = (String) exprList.getExpr(0).eval(scope);
			int pageNumber = (int) exprList.getExpr(1).eval(scope);
			int pageSize = (int) exprList.getExpr(2).eval(scope);
			Kv param = Kv.by("type", type)
					.set("pageNumber", pageNumber)
					.set("pageSize", pageSize);
			Map page = App.get("/articles", param, "page", Map.class);
			scope.setLocal("page", page);
		} else if (len == 4) {
			String type = (String) exprList.getExpr(0).eval(scope);
			String value = (String) exprList.getExpr(1).eval(scope);
			int pageNumber = (int) exprList.getExpr(2).eval(scope);
			int pageSize = (int) exprList.getExpr(3).eval(scope);
			Kv param = Kv.by("type", type)
					.set("value", value)
					.set("pageNumber", pageNumber)
					.set("pageSize", pageSize);
			Map page = App.get("/articles", param, "page", Map.class);
			scope.setLocal("page", page);
		} else {
			error(writer, "#articles(type[,value],pageNumber,pageSize) 参数错误");
			return;
		}
		stat.exec(env, scope, writer);
		scope.removeLocal("page");
	}
}
