package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleDirective extends BaseDirective{
	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			error(writer, "#shuffle([limit][min,max]) 至少需要指定一个参数");
			return;
		} else if (len == 1) {
			int max = (int) exprList.getExpr(0).eval(scope);
			List<Integer> shuffle=new ArrayList<>();
			for (int i = 1; i <= max; i++) {
				shuffle.add(i);
			}
			Collections.shuffle(shuffle);
			scope.setLocal("shuffle", shuffle);
		}else if (len == 2) {
			int min = (int) exprList.getExpr(0).eval(scope);
			int max = (int) exprList.getExpr(1).eval(scope);
			List<Integer> shuffle=new ArrayList<>();
			for (int i = min; i <= max; i++) {
				shuffle.add(i);
			}
			Collections.shuffle(shuffle);
			scope.setLocal("shuffle", shuffle);
		}else{
			error(writer, "#shuffle([limit][min,max]) 至多有两个参数");
			return;
		}
		stat.exec(env, scope, writer);
		scope.removeLocal("shuffle");
	}
}
