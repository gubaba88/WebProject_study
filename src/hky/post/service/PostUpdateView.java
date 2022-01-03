package hky.post.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.PostDAO;
import hky.project.dto.PostDTO;

public class PostUpdateView implements Action {
	private static final Log log = LogFactory.getLog(PostUpdateView.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		PostDTO postDTO = new PostDTO();
		postDTO.setGameNumber(Integer.parseInt(request.getParameter("gameNumber")));
		postDTO.setPostNumber(Integer.parseInt(request.getParameter("postNumber")));
		postDTO.setPostTopic(request.getParameter("postTopic"));
		postDTO.setPostTitle(request.getParameter("postTitle"));
		postDTO.setPostContent(request.getParameter("postContent"));
		log.info("수정할 글 정보"+postDTO);
		PostDAO postDAO = new PostDAO();
		postDTO = postDAO.update(postDTO);
		request.setAttribute("postDTO", postDTO);
		ActionCommand actionCommand = new ActionCommand();
		actionCommand.setPath("/WEB-INF/view/post/post_update_view.jsp");
		return actionCommand;
	}
}
