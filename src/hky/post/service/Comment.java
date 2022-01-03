package hky.post.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;

public class Comment implements Action {
	private static final Log log = LogFactory.getLog(Comment.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		String comWriter = request.getParameter("comWriter");
		log.info(comWriter);
		return null;
	}
}
