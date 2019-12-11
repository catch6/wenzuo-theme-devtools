/**
 * Copyright (c) 2011-2019, James Zhan 詹波 (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.render;

import java.io.IOException;

import com.jfinal.core.Const;

/**
 * ErrorRender.
 */
public class ErrorRender extends Render {

	protected static final String contentType = "text/html; charset=" + getEncoding();

	private static final String hmr = "<script> var socket; var origin = location.origin;origin = origin.replace(location.protocol, \"\").replace(\"//\", \"\"); if (window.WebSocket) { socket = new WebSocket(`ws://${origin}/hmr.ws`); socket.onmessage = function (event) { if (event.data === \"reload\") { location.reload(true); } }; } else { alert(\"Your browser does not support Websockets. (Use Chrome)\"); } function send(message) { if (window.WebSocket) { if (socket.readyState == WebSocket.OPEN) { socket.send(message); } else { alert(\"The socket is not open.\"); } } }</script>";

	protected static final byte[] html404 = ("<html><meta charset=\"UTF-8\"><head><title>404 Not Found</title>" + hmr + "</head><body bgcolor='white'><center><h1>404 Not Found</h1></center></body></html>").getBytes();
	protected static final byte[] html500 = ("<html><meta charset=\"UTF-8\"><head><title>500 Internal Server Error</title>" + hmr + "</head><body bgcolor='white'><center><h1>主题编译异常，请检查语法</h1></center></body></html>").getBytes();

	protected static final byte[] html400 = ("<html><meta charset=\"UTF-8\"><head><title>400 Bad Request</title>" + hmr + "</head><body bgcolor='white'><center><h1>400 Bad Request</h1></center></body></html>").getBytes();
	protected static final byte[] html401 = ("<html><meta charset=\"UTF-8\"><head><title>401 Unauthorized</title>" + hmr + "</head><body bgcolor='white'><center><h1>401 Unauthorized</h1></center></body></html>").getBytes();
	protected static final byte[] html403 = ("<html><meta charset=\"UTF-8\"><head><title>403 Forbidden</title>" + hmr + "</head><body bgcolor='white'><center><h1>403 Forbidden</h1></center></body></html>").getBytes();

	protected int errorCode;

	public ErrorRender(int errorCode, String view) {
		this.errorCode = errorCode;
		this.view = view;
	}

	@Override
	public void render() {
		response.setStatus(getErrorCode());    // HttpServletResponse.SC_XXX_XXX

		// render with view
		String view = getView();
		if (view != null) {
			RenderManager.me().getRenderFactory().getRender(view).setContext(request, response).render();
			return;
		}

		// render with html content
		try {
			response.setContentType(contentType);
			response.getOutputStream().write(getErrorHtml());
		} catch (IOException e) {
			throw new RenderException(e);
		}
	}

	public byte[] getErrorHtml() {
		int errorCode = getErrorCode();
		if (errorCode == 404) {
			return html404;
		}
		if (errorCode == 500) {
			return html500;
		}
		if (errorCode == 400) {
			return html400;
		}
		if (errorCode == 401) {
			return html401;
		}
		if (errorCode == 403) {
			return html403;
		}
		return ("<html><head><meta charset=\"UTF-8\"><title>" + errorCode + " Error</title>" + hmr + "</head><body bgcolor='white'><center><h1>" + errorCode + " Error</h1></center></body></html>").getBytes();
	}

	public int getErrorCode() {
		return errorCode;
	}
}





