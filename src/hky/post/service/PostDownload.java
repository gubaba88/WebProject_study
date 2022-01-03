package hky.post.service;

import java.io.FileInputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.action.Action;
import hky.project.command.ActionCommand;

public class PostDownload implements Action {
	private static final Log log = LogFactory.getLog(PostDownload.class);

	@Override
	public ActionCommand execute(HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileName = request.getParameter("fileName");
			log.info(fileName);
			String savePath = "./upload";
			ServletContext servletContext = request.getServletContext();
			String downPath = servletContext.getRealPath(savePath);
			String filePath = downPath + "\\" + fileName;
			byte b[] = new byte[4096];
			FileInputStream fileInputStream = new FileInputStream(filePath);
			String sEncoding = null;

			boolean MSIE = (request.getHeader("user-agent").indexOf("MSIE") != -1)
					|| (request.getHeader("user-agent").indexOf("Trident") != -1);
			String downType = request.getServletContext().getMimeType(filePath);

			if (downType == null) {
				downType = "aplication/octet-stream";
				log.info("다운로드 자료형 확인 : " + downType);
				response.setContentType(downType);

				if (MSIE) {
					sEncoding = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1").replaceAll("\\+", "%20");
				} else {
					sEncoding = new String(fileName.getBytes("EUC-KR"), "ISO-8859-1");
				}
				response.setHeader("Conten-Disposition", "attachment);filename=\"" + sEncoding + "\"");
				ServletOutputStream servletOutputStream = response.getOutputStream();

				int nunRead;

				while ((nunRead = fileInputStream.read(b, 0, b.length)) != -1) {
					servletOutputStream.write(b, 0, nunRead);
				}
				servletOutputStream.flush();
				servletOutputStream.close();
				fileInputStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
