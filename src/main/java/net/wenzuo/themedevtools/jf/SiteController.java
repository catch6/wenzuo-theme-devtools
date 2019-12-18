package net.wenzuo.themedevtools.jf;

import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;
import net.wenzuo.themedevtools.App;

/**
 * @author Catch
 * @date 2018-06-11 14:06
 */
public class SiteController extends Controller {

	/**
	 * 站点处理
	 * <p>
	 * attribute 中 attrs 数组内容 ["站点key","view",...]
	 */
	public void index() {
		String[] attrs = getAttr(Const.ATTRS);

		// 设置 site
		setAttr(Const.SITE, App.site);

		// 设置 master
		setAttr(Const.MASTER, App.master);

		// 设置 visitor
		setAttr(Const.VISITOR, App.visitor);

		// 设置 isMaster
		setAttr(Const.IS_MASTER, App.isMaster);

		// 设置 assets 路径
		setAttr(Const.ASSETS, StrUtil.EMPTY);

		String view = attrs.length == 1 ? Const.INDEX : attrs[1];
		view = view + Const.VIEW_SUFFIX;
		render(view);
	}

}
