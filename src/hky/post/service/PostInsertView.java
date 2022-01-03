package hky.post.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import hky.project.action.Action;
import hky.project.command.ActionCommand;
import hky.project.dao.GameDAO;
import hky.project.dao.MemberDAO;
import hky.project.dao.PostDAO;
import hky.project.dto.GameDTO;
import hky.project.dto.MemberDTO;
import hky.project.dto.PostDTO;

public class PostInsertView implements Action{
	private static final Log log = LogFactory.getLog(PostInsertView.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		String realFolder = "";
		String saveFolder = "./upload";
		realFolder = request.getSession().getServletContext().getRealPath(saveFolder);
		int fileSize = 10*1024*1024;
		try {
			MultipartRequest multipartRequest = new MultipartRequest(request, realFolder, fileSize,"UTF-8",new DefaultFileRenamePolicy());
			int gameNumber = Integer.parseInt(multipartRequest.getParameter("gameNumber"));
			String postTopic = multipartRequest.getParameter("postTopic");
			String postTitle = multipartRequest.getParameter("postTitle");
			String memId = multipartRequest.getParameter("memId");
			String postContent = multipartRequest.getParameter("postContent");
			String fileName = multipartRequest.getFilesystemName((String)multipartRequest.getFileNames().nextElement());
			
			log.info(fileName);
			
			GameDTO gameDTO = new GameDTO();
			GameDAO gameDAO = new GameDAO();
			gameDTO.setGameNumber(gameNumber);
			log.info("받은 게임 번호 : "+gameDTO.getGameNumber());
			gameDTO = gameDAO.select(gameDTO);
			log.info("검색한 게임 정보 : "+gameDTO);
			
			PostDTO postDTO = new PostDTO();
			PostDAO postDAO = new PostDAO();
			postDTO = postDAO.maxNumber(gameDTO);
			postDTO.setGameNumber(gameDTO.getGameNumber());
			log.info("검색한 최대 글번호 : "+postDTO.getPostNumber());
			postDTO.setPostNumber(postDTO.getPostNumber()+1);
			postDTO.setPostTopic(postTopic);
			postDTO.setPostTitle(postTitle);
			postDTO.setMemId(memId);
			postDTO.setPostContent(postContent);
			postDTO.setFileName(fileName);
			log.info("입력할 글 정보 : "+postDTO);
			postDTO = postDAO.insert(postDTO);
			
			MemberDTO memberDTO = new MemberDTO();
			MemberDAO memberDAO = new MemberDAO();
			memberDTO.setMemId(memId);
			memberDTO = memberDAO.select(memberDTO);
			memberDTO.setMemPoint(memberDTO.getMemPoint()+20);
			memberDAO.pointUpdate(memberDTO);
			
			request.setAttribute("postDTO", postDTO);
			request.setAttribute("gameDTO", gameDTO);
			ActionCommand actionCommand = new ActionCommand();
			actionCommand.setPath("/WEB-INF/view/post/post_insert_view.jsp");
			return actionCommand;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
