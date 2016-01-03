package br.com.multi.web.login;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import javax.servlet.http.HttpSession;

public class SessionJson {
	/*
	 * Convert a session var in a json var to put it in client side
	 * 
	 * @param request HttpServletRequest to get the session data
	 * @return String with json data
	 */
	public String getJsonfromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		JSONObject jo = new JSONObject();

		Enumeration<String> parameterNames = session.getAttributeNames();
		while (parameterNames.hasMoreElements()) {

			String paramName = parameterNames.nextElement();
			jo.put(paramName, session.getAttribute(paramName).toString());
		}
		if (!jo.isEmpty())
			return jo.toJSONString();

		return "{}";
	}
	/*
	 * Check if the current session is active
	 * 
	 * @param request HttpServletRequest with the session var to check 
	 * @return boolean true if session is active or false else.
	 */
	public boolean isSessionActive(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null)
			return (session.getAttribute("username") != null);
		return false;

	}

}
