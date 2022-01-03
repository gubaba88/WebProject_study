package hky.member.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.MemberDAO;
import hky.project.dto.MemberDTO;

public class MemDelete implements Action {
	private static final Log log = LogFactory.getLog(MemDelete.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		String memId = request.getParameter("memId");
		log.info(memId);
		MemberDTO memberDTO = new MemberDTO();
		memberDTO.setMemId(memId);
		MemberDAO memberDAO = new MemberDAO();
		memberDTO = memberDAO.delete(memberDTO);
		log.info(memberDTO);
		request.setAttribute("memberDTO", memberDTO);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/member/mem_delete.jsp");
		return actionCommand;
	}
}
