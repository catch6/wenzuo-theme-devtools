package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.kit.Kv;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;
import net.wenzuo.themedevtools.App;

import java.util.List;
import java.util.Map;

public class PrevNextDirective extends BaseDirective {
	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			error(writer, "#PrevNext(articleId,[length][prevLength,nextLength]) 至少需要指定一个参数");
			return;
		} else if (len == 1) {
			String articleId = (String) exprList.getExpr(0).eval(scope);
			setPrevNext(scope,articleId,1,1);
		} else if (len==2){
			String articleId = (String) exprList.getExpr(0).eval(scope);
			int length= (int) exprList.getExpr(1).eval(scope);
			setPrevNext(scope,articleId,length,length);
		}else if (len==3){
			String articleId = (String) exprList.getExpr(0).eval(scope);
			int preLength= (int) exprList.getExpr(1).eval(scope);
			int nextLength= (int) exprList.getExpr(2).eval(scope);
			setPrevNext(scope,articleId,preLength,nextLength);
		}else {
			error(writer, "#PrevNext(articleId,[length][prevLength,nextLength]) 至多有三个参数");
			return;
		}
		stat.exec(env, scope, writer);
		scope.removeLocal("prev");
		scope.removeLocal("next");
	}

	private void setPrevNext(Scope scope,String articleId,int prevLength,int nextLength){
		Kv kv=Kv.by("articleId",articleId).set("prevLength",prevLength).set("nextLength",nextLength);

		Map data= App.get("/prevNext", kv,"data");

		List prev= (List) data.get("prev");
		List next= (List) data.get("next");

		scope.setLocal("prev", prev);
		scope.setLocal("next", next);
	}

}
