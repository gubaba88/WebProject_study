package hky.post.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.GameDAO;
import hky.project.dao.PostDAO;
import hky.project.dto.GameDTO;
import hky.project.dto.PostDTO;

public class PostSelectAll implements Action{
	private static Log log = LogFactory.getLog(PostSelectAll.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		int gameNumber = Integer.parseInt(request.getParameter("gameNumber"));
		int page = 1;
		if (request.getParameter("page") != null && !request.getParameter("page").equals("")) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		String topic = "";
		if (request.getParameter("topic") != null && !request.getParameter("topic").equals("")) {
			topic = request.getParameter("topic");
		}
		GameDTO gameDTO = new GameDTO();
		GameDAO gameDAO = new GameDAO();
		gameDTO.setGameNumber(gameNumber);
		log.info("검색을 위한 게임 번호 : "+gameDTO.getGameNumber());
		gameDTO = gameDAO.select(gameDTO);
		log.info("검색한 게임 정보 : "+gameDTO);
		
		ArrayList<PostDTO> arrayList = new ArrayList<PostDTO>();
		PostDAO postDAO = new PostDAO();
		arrayList = postDAO.selectAll(gameDTO, page, topic);
		log.info("받아서 보낼 게시글 목록 : " + arrayList);
		
		SimpleDateFormat format = new SimpleDateFormat ("yyyy-MM-dd");
		Date date = new Date();
		String day = format.format(date);
		log.info("현재 날짜 : "+day);
		
		int listCount = postDAO.listCount(gameDTO,topic);
		int maxpage = (int) ((double) listCount/10 + 0.9);
		int startpage = (((int) ((double) page / 10 + 0.9)) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if (endpage > maxpage) {
			endpage = maxpage;
		}
		request.setAttribute("gameDTO", gameDTO);
		request.setAttribute("arrayList", arrayList);
		request.setAttribute("topic", topic);
		request.setAttribute("day", day);
		request.setAttribute("page", page);
		request.setAttribute("maxpage", maxpage);
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("listCount", listCount);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/post/post_select_all_view.jsp");
		return actionCommand;
	}
}
