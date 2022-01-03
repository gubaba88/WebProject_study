package hky.project.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.MemberDAO;
import hky.project.dto.MemberDTO;

public class Login implements Action{
	private static Log log = LogFactory.getLog(Login.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		String memId = request.getParameter("memId");
		String memPasswd = request.getParameter("memPasswd");
		String save = request.getParameter("save");
		log.info(save);
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemId(memId);
		memberDTO.setMemPasswd(memPasswd);
		log.info(memberDTO);
		
		MemberDAO memberDAO = new MemberDAO();
		memberDTO = memberDAO.login(memberDTO);
		log.info(memberDTO);
		request.setAttribute("memberDTO", memberDTO);
		if (!memberDTO.getMemId().equals("") & !memberDTO.getMemPasswd().equals("")) {
			HttpSession httpSession = request.getSession();
			httpSession.setAttribute("memId", memberDTO.getMemId());
			Cookie cookie = new Cookie("memId",memberDTO.getMemId());
			cookie.setMaxAge(60*60*24);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/login/login_check.jsp");
		return actionCommand;
	}

}
