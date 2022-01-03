package hky.member.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.MemberDAO;
import hky.project.dto.MemberDTO;

public class MemInsert implements Action {
	private static final Log log = LogFactory.getLog(MemInsert.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		String memId = request.getParameter("memId");
		log.info(memId);
		String memPasswd = request.getParameter("memPasswd");
		log.info(memPasswd);
		String memEmail = request.getParameter("memEmail");
		log.info(memEmail);
		String memName = request.getParameter("memName");
		log.info(memName);
		String memPhone = request.getParameter("memPhone");
		log.info(memPhone);
		String memGender = "";
		int memGenderi = Integer.parseInt(request.getParameter("memGender"));
		log.info(memGenderi);
		if (memGenderi == 1 || memGenderi == 3 ) {
			memGender = "M";
		} else {
			memGender = "F";
		}
		log.info(memGender);
		String memBirth = "";
		if (memGenderi < 3 ) {
			memBirth = "19" + request.getParameter("memBirth");
		} else {
			memBirth = "20" + request.getParameter("memBirth");
		}
		log.info(memBirth);
		String memAddress = "-";
		if (!request.getParameter("memAddress").equals("")) {
			memAddress = request.getParameter("memAddress");
		}	
		log.info(memAddress);
		String memComment = "-";
		if (!request.getParameter("memComment").equals("")) {
			memComment = request.getParameter("memComment");
		}
		log.info(memComment);
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		MemberDAO memberDAO = new MemberDAO();
//		arrayList = memberDAO.selectAll();
		log.info(arrayList);
		request.setAttribute("arrayList", arrayList);
		MemberDTO memberDTO = new MemberDTO();
		int memNumber = memberDAO.maxNumber();
		memberDTO.setMemNumber(memNumber + 1);
		memberDTO.setMemId(memId);
		memberDTO.setMemPasswd(memPasswd);
		memberDTO.setMemEmail(memEmail);
		memberDTO.setMemName(memName);
		memberDTO.setMemPhone(memPhone);
		memberDTO.setMemBirth(memBirth);
		memberDTO.setMemGender(memGender);
		memberDTO.setMemAddress(memAddress);
		memberDTO.setMemComment(memComment);
		log.info(memberDTO);
		memberDTO = memberDAO.insert(memberDTO);
		request.setAttribute("memId", memberDTO.getMemId());
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/member/mem_insert_view.jsp");
		return actionCommand;
	}

}
