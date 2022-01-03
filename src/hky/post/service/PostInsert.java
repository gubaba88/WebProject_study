package hky.post.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.GameDAO;
import hky.project.dto.GameDTO;

public class PostInsert implements Action{
private static final Log log = LogFactory.getLog(PostInsert.class);
	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		int gameNumber = Integer.parseInt(request.getParameter("gameNumber"));
		log.info(gameNumber);
		GameDTO gameDTO = new GameDTO();
		GameDAO gameDAO = new GameDAO();
		gameDTO.setGameNumber(gameNumber);
		gameDTO = gameDAO.select(gameDTO);
		log.info(gameDTO);
		request.setAttribute("gameDTO", gameDTO);
		
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/post/post_insert.jsp");
		return actionCommand;
	}
}
