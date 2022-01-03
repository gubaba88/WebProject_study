package hky.project.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;

public class Logout implements Action{
	private static Log log = LogFactory.getLog(Logout.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession httpSession = request.getSession();
		String memId = (String)httpSession.getAttribute("memId");
		request.setAttribute("memId", memId);
		
		httpSession.invalidate();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("memId")) {
					log.info(cookie.getName().equals("memId"));
					cookie.setMaxAge(0);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
		}
		
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/login/logout.jsp");
		return actionCommand;
	}
	
}
