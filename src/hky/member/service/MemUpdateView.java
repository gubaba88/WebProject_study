package hky.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.MemberDAO;
import hky.project.dto.MemberDTO;

public class MemUpdateView implements Action {
	private static final Log log = LogFactory.getLog(MemUpdateView.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO memberDAO = new MemberDAO();
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemId(request.getParameter("memId"));
		memberDTO.setMemPasswd(request.getParameter("memPasswd"));
		memberDTO.setMemEmail(request.getParameter("memEmail"));
		memberDTO.setMemPhone(request.getParameter("memPhone"));
		String memAddress = "입력 없음";
		if (!request.getParameter("memAddress").equals("")) {
			memAddress = request.getParameter("memAddress");
		}
		String memComment = "입력 없음";
		if (!request.getParameter("memComment").equals("")) {
			memComment = request.getParameter("memComment");
		}
		memberDTO.setMemAddress(memAddress);
		memberDTO.setMemComment(memComment);
		log.info(memberDTO);
		memberDTO = memberDAO.update(memberDTO);
		request.setAttribute("memberDTO", memberDTO);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/member/mem_update_view.jsp");
		return actionCommand;
	}
}
