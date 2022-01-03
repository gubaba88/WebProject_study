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

public class MemSelectAll implements Action {
	private static final Log log = LogFactory.getLog(MemSelectAll.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		MemberDAO memberDAO = new MemberDAO();
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		int page = 1;
		int limit = 10;
		if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		if (request.getParameter("limit") != null && !request.getParameter("limit").equals("")) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		arrayList = memberDAO.selectAll(page, limit);
		log.info(arrayList);
		int listCount = memberDAO.memberCount();
		int maxpage = (int) ((double) listCount/limit + 0.9);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if (endpage > maxpage) {
			endpage = maxpage;
		}
		int memberCount = memberDAO.maxNumber();
		request.setAttribute("arrayList", arrayList);
		request.setAttribute("limit", limit);
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listCount", listCount);
		request.setAttribute("memberCount", memberCount);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/member/mem_select_all_view.jsp");
		return actionCommand;
	}
}
