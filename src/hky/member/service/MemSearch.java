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

public class MemSearch implements Action{
private static final Log log = LogFactory.getLog(MemSearch.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		String keyword = request.getParameter("keyword");
		log.info("키워드 확인 - " + keyword);
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
		arrayList = memberDAO.search(keyword, page, limit);
		log.info(arrayList);
		int listCount = memberDAO.searchMemberCount(keyword);
		log.info(listCount);
		int maxpage = (int) ((double) listCount/limit + 0.9);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if (endpage > maxpage) {
			endpage = maxpage;
		}
		request.setAttribute("arrayList", arrayList);
		request.setAttribute("limit", limit);
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listCount", listCount);
		request.setAttribute("keyword", keyword);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/member/mem_search_view.jsp");
		return actionCommand;
	}

}
