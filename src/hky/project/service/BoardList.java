package hky.project.service;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.GameDAO;
import hky.project.dto.GameDTO;

public class BoardList implements Action {
	private static Log log = LogFactory.getLog(BoardList.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		GameDAO gameDAO = new GameDAO();
		ArrayList<GameDTO> arrayList = new ArrayList<GameDTO>();
		arrayList = gameDAO.selectAll();
		log.info(arrayList);
		request.setAttribute("arrayList", arrayList);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/board_all_view.jsp");
		return actionCommand;
	}
}
