package net.wenzuo.themedevtools.jf;

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
		String[] attrs = getAttr("attrs");

		// 设置 site
		setAttr(Const.SITE_KEY, App.site);

		// 设置 owner
		setAttr(Const.OWNER_KEY, App.owner);

		// 设置 isOwner
		setAttr(Const.IS_OWNER_KEY, App.isOwner);

		// 设置 assets 路径
		setAttr(Const.ASSETS_KEY, "");

		String view = attrs.length == 1 ? "index" : attrs[1];
		view = view + ".html";
		render(view);
	}

}
