package hky.post.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.PostDAO;
import hky.project.dto.PostDTO;

public class PostDelete implements Action {
	private static final Log log = LogFactory.getLog(PostDelete.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		int gameNumber = Integer.parseInt(request.getParameter("gameNumber"));
		int postNumber = Integer.parseInt(request.getParameter("postNumber"));
		PostDTO postDTO = new PostDTO();
		PostDAO postDAO = new PostDAO();
		postDTO.setGameNumber(gameNumber);
		postDTO.setPostNumber(postNumber);
		log.info("입력받은 삭제할 데이터 : " + postDTO);
		postDTO = postDAO.delete(postDTO);
		request.setAttribute("postDTO", postDTO);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/post/post_delete.jsp");
		return actionCommand;
	}
}
