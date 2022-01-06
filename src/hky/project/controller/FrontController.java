package hky.project.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.member.service.MemDelete;
import hky.member.service.MemInsert;
import hky.member.service.MemSearch;
import hky.member.service.MemSelect;
import hky.member.service.MemSelectAll;
import hky.member.service.MemUpdate;
import hky.member.service.MemUpdateView;
import hky.post.service.PostDelete;
import hky.post.service.PostInsert;
import hky.post.service.PostInsertView;
import hky.post.service.PostSearch;
import hky.post.service.PostSelect;
import hky.post.service.PostSelectAll;
import hky.post.service.PostUpdate;
import hky.post.service.PostUpdateView;
import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.service.BoardList;
import hky.project.service.Login;
import hky.project.service.Logout;

public class FrontController extends HttpServlet {
	private static Log log = LogFactory.getLog(FrontController.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		service(request, response);
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String pathURI = requestURI.substring(contextPath.length());
		ActionCommand actionCommand = null;
		Action action = null;
		
		if (pathURI.equals("/BoardList.do")) {
			action = new BoardList();
			try {
				actionCommand = action.execute(request, response);				
			} catch (Exception e) {
				log.info("게시판 목록 조회 실패 - " + e);
			}
		}
		else if (pathURI.equals("/Login.do")) {
			actionCommand = new ActionCommand();
			actionCommand.setPath("WEB-INF/view/login/login.jsp");
		}
		else if (pathURI.equals("/LoginCheck.do")) {
			action = new Login();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("로그인 실패 - " + e);
			}
		}
		else if (pathURI.equals("/Logout.do")) {
			action = new Logout();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("로그아웃 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostSelectAll.do")) {
			action = new PostSelectAll();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("게시글 목록 조회 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostSelect.do")) {
			action = new PostSelect();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("게시글 조회 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostInsert.do")) {
			action = new PostInsert();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("글 작성 페이지 이동 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostInsertView.do")) {
			action = new PostInsertView();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("글 등록 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostUpdate.do")) {
			action = new PostUpdate();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("글 수정 페이지 이동 실패" + e);
			}
		}
		else if (pathURI.equals("/PostUpdateView.do")) {
			action = new PostUpdateView();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("글 수정 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostDelete.do")) {
			action = new PostDelete();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("글 삭제 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemSelectAll.do")) {
			action = new MemSelectAll();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 전체 조회 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemSelect.do")) {
			action = new MemSelect();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 상세 조회 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemInsert.do")) {
			actionCommand = new ActionCommand();
			actionCommand.setPath("/WEB-INF/view/member/mem_insert.jsp");
		}
		else if (pathURI.equals("/MemInsertView.do")) {
			action = new MemInsert();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 가입 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemUpdate.do")) {
			action = new MemUpdate();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 수정 페이지 이동 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemUpdateView.do")) {
			action = new MemUpdateView();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 수정 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemDelete.do")) {
			action = new MemDelete();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 삭제 실패 - " + e);
			}
		}
		else if (pathURI.equals("/MemSearch.do")) {
			action = new MemSearch();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("회원 검색 실패 - " + e);
			}
		}
		else if (pathURI.equals("/PostSearch.do")) {
			action = new PostSearch();
			try {
				actionCommand = action.execute(request, response);
			} catch (Exception e) {
				log.info("글 목록 검색 실패 - " + e);
			}
		}
		
		if (actionCommand != null) {
			if (actionCommand.isRedirect()) {
				response.sendRedirect(actionCommand.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(actionCommand.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
}