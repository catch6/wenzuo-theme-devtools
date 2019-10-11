package net.wenzuo.themedevtools.jf.directive;

import cn.hutool.core.util.StrUtil;
import com.jfinal.template.Directive;
import com.jfinal.template.TemplateException;
import com.jfinal.template.io.Writer;

import java.io.IOException;

/**
 * @author Catch
 * @date 2019-09-23 下午5:42
 */
public abstract class BaseDirective extends Directive {
    private static final String PREFIX = "<font color=red>";
    private static final String SUFFIX = "</font>";

    protected void error(Writer writer, String errorMsg) {
        errorMsg = PREFIX + location + StrUtil.SPACE + errorMsg + SUFFIX;
        try {
            writer.write(errorMsg, 0, errorMsg.length());
        } catch (IOException e) {
            throw new TemplateException(e.getMessage(), location, e);
        }
    }

    @Override
    public boolean hasEnd() {
        return true;
    }
}
