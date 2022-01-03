package hky.project.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hky.project.command.ActionCommand;

public interface Action {
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response);
}
