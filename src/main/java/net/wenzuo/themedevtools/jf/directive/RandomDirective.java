package net.wenzuo.themedevtools.jf.directive;

import cn.hutool.core.util.RandomUtil;
import com.jfinal.template.Env;
import com.jfinal.template.io.Writer;
import com.jfinal.template.stat.Scope;

import java.io.IOException;

public class RandomDirective extends BaseDirective {
	@Override
	public boolean hasEnd() {
		return false;
	}

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		int len = exprList.length();
		if (len == 0) {
			error(writer, "#random([limit][min,max]) 至少需要指定一个参数");
		} else if (len == 1) {
			int max = (int) exprList.getExpr(0).eval(scope);
			int i=RandomUtil.randomInt(max);
			try {
				writer.write(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if (len == 2) {
			int min = (int) exprList.getExpr(0).eval(scope);
			int max = (int) exprList.getExpr(1).eval(scope);
			int i=RandomUtil.randomInt(min,max);
			try {
				writer.write(i);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			error(writer, "#random([limit][min,max]) 至多有两个参数");
		}
	}


}
