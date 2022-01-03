package hky.post.service;

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

public class PostSelect implements Action{
private static final Log log = LogFactory.getLog(PostSelect.class);
	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		int gameNumber = Integer.parseInt(request.getParameter("gameNumber"));
		int postNumber = Integer.parseInt(request.getParameter("postNumber"));
		log.info("게시판 번호 : "+gameNumber+", 게시글 번호 : "+postNumber);
		GameDTO gameDTO = new GameDTO();
		PostDTO postDTO = new PostDTO();
		gameDTO.setGameNumber(gameNumber);
		postDTO.setGameNumber(gameNumber);
		postDTO.setPostNumber(postNumber);
		GameDAO gameDAO = new GameDAO();
		gameDTO = gameDAO.select(gameDTO);
		PostDAO postDAO = new PostDAO();
		postDTO = postDAO.select(postDTO);
		log.info(gameDTO);
		log.info(postDTO);		
		request.setAttribute("gameDTO", gameDTO);
		request.setAttribute("postDTO", postDTO);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/post/post_select_view.jsp");
		return actionCommand;
	}

}
