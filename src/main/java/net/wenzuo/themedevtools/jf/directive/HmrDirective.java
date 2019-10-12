package net.wenzuo.themedevtools.jf.directive;

import com.jfinal.kit.Kv;
import com.jfinal.kit.PropKit;
import com.jfinal.template.Directive;
import com.jfinal.template.Engine;
import com.jfinal.template.Env;
import com.jfinal.template.TemplateException;
import com.jfinal.template.io.Writer;
import com.jfinal.template.source.ClassPathSource;
import com.jfinal.template.stat.Scope;

import java.io.IOException;

/**
 * @author Catch
 * @date 2019-10-12 10:18
 */
public class HmrDirective extends Directive {
	private static final String HMR = new ClassPathSource("hmr.html").getContent().toString().replace("PORT", PropKit.get("port"));

	@Override
	public void exec(Env env, Scope scope, Writer writer) {
		try {
			writer.write(HMR);
		} catch (IOException e) {
			throw new TemplateException(e.getMessage(), location, e);
		}
	}
}
