package hky.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.dto.GameDTO;
import hky.project.dto.PostDTO;

public class PostDAO {
	// 로그 출력을 위해 템플릿을 이용하여 작성
		private static final Log log = LogFactory.getLog(PostDAO.class);

		// 기본생성자 생성
		public PostDAO() {
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				log.info("데이터베이스 연결 성공" + dataSource);
			} catch (Exception e) {
				log.info("데이터베이스 연결 실패 - " + e);
			}
		}

		// 제네릭 PostDTO 클래스 반환 자료형으로 특정 게임 게시판의 전체 글 데이터를 조회한다.
		public ArrayList<PostDTO> selectAll(GameDTO gameDTO, int page, String topic) {
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			ArrayList<PostDTO> arrayList = new ArrayList<PostDTO>();
			int startrow = (page-1)*10 +1;
			int endrow = startrow + 9;
			String searchCall = "";
			if (!topic.equals("")) {
				searchCall = " where postTopic = '"+topic+"' ";
			}
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "select * from (select rownum rnum, postNumber, postTopic, postTitle, memId, postDate ";
				sql+= "from (select * from board" + gameDTO.getGameNumber()+searchCall+" order by postNumber desc)) ";
				sql+= " where rnum between "+startrow+" and "+endrow;
				log.info("SQL - " + sql);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
					PostDTO postDTO = new PostDTO();
					postDTO.setPostNumber(resultSet.getInt("postNumber"));
					postDTO.setPostTopic(resultSet.getString("postTopic"));
					postDTO.setPostTitle(resultSet.getString("postTitle"));
					postDTO.setMemId(resultSet.getString("memId"));
					postDTO.setPostDate(resultSet.getString("postDate"));
					arrayList.add(postDTO);
					log.info("조회 데이터 확인 - " + postDTO);
				}
			} catch (Exception e) {
				log.info("게시판 글 조회 실패 - " + e);
			} finally {
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return arrayList;
		}

		// PostDTO 클래스 반환 자료형으로 특정 글의 데이터를 조회한다.
		public PostDTO select(PostDTO postDTO) {
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "select * from board"+postDTO.getGameNumber();
				sql += " where postNumber = '"+postDTO.getPostNumber()+"'";
				log.info("SQL - " + sql);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					log.info("게시글 번호 확인 - " + resultSet.getInt("postNumber"));
					postDTO.setPostNumber(resultSet.getInt("postNumber"));
					postDTO.setPostTopic(resultSet.getString("postTopic"));
					postDTO.setPostTitle(resultSet.getString("postTitle"));
					postDTO.setMemId(resultSet.getString("memId"));
					postDTO.setPostContent(resultSet.getString("postContent"));
					postDTO.setPostDate(resultSet.getString("postDate"));
					postDTO.setFileName(resultSet.getString("fileName"));
					log.info("조회 데이터 확인 - " + postDTO);
				}
			} catch (Exception e) {
				log.info("게시글 조회 실패 - " + e);
			} finally {
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return postDTO;
		}
		
		public PostDTO maxNumber(GameDTO gameDTO) {
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			PostDTO postDTO = new PostDTO();
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "select max(postNumber) from board"+gameDTO.getGameNumber();
				log.info("SQL - " + sql);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					log.info("게시글 번호 확인 - " + resultSet.getInt("max(postNumber)"));
					postDTO.setPostNumber(resultSet.getInt("max(postNumber)"));
				}
			} catch (Exception e) {
				log.info("게시글 조회 실패 - " + e);
			} finally {
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return postDTO;
		}

		// postDTO 클래스 자료형으로 글을 입력한다.
		public PostDTO insert(PostDTO postDTO) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "insert into board" + postDTO.getGameNumber();
				sql += " values (?,?,?,?,?,sysdate,?)";
				log.info("SQL - " + sql);
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, postDTO.getPostNumber());
				preparedStatement.setString(2, postDTO.getPostTopic());
				preparedStatement.setString(3, postDTO.getPostTitle());
				preparedStatement.setString(4, postDTO.getMemId());
				preparedStatement.setString(5, postDTO.getPostContent());
				preparedStatement.setString(6, postDTO.getFileName());
				int count = preparedStatement.executeUpdate();
				log.info("입력 데이터 확인 - " + postDTO);
				if (count > 0) {
					connection.commit();
					log.info("커밋되었습니다.");
				} else {
					connection.rollback();;
					log.info("롤백되었습니다.");
				}
			} catch (Exception e) {
				log.info("글 등록 실패 - " + e);
			} finally {
				try {
					connection.close();
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return postDTO;
		}

		// postDTO 클래스 반환 자료형으로 글을 수정한다.
		public PostDTO update(PostDTO postDTO) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "update board" + postDTO.getGameNumber()+" set ";
				sql += "postTopic=?,postTitle=?,postContent=? where postNumber=?";
				log.info("SQL - " + sql);
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, postDTO.getPostTopic());
				preparedStatement.setString(2, postDTO.getPostTitle());
				preparedStatement.setString(3, postDTO.getPostContent());
				preparedStatement.setInt(4, postDTO.getPostNumber());
				int count = preparedStatement.executeUpdate();
				log.info("입력 데이터 확인 - " + postDTO);
				if (count > 0) {
					connection.commit();
					log.info("커밋되었습니다.");
				} else {
					connection.rollback();;
					log.info("롤백되었습니다.");
				}
			} catch (Exception e) {
				log.info("글 등록 실패 - " + e);
			} finally {
				try {
					connection.close();
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return postDTO;
		}

		// 글 번호를 입력받아 글 데이터를 삭제한다.
		public PostDTO delete(PostDTO postDTO) {
			Connection connection = null;
			Statement statement = null;
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "delete from board"+postDTO.getGameNumber(); 
				sql += " where postNumber =" + postDTO.getPostNumber();
				log.info("SQL - " + sql);
				statement = connection.createStatement();
				int count = statement.executeUpdate(sql);
				if (count > 0) {
					connection.commit();
					log.info("커밋되었습니다.");
				} else {
					connection.rollback();
					log.info("롤백되었습니다.");
				}
			} catch (Exception e) {
				log.info("글 삭제 실패 - " + e);
			} finally {
				try {
					connection.close();
					statement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return postDTO;
		}
		
		public int listCount(GameDTO gameDTO,String topic) {
			int i = 0;
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String searchCall = "";
			if (!topic.equals("")) {
				searchCall = " where postTopic = '"+topic+"' ";
			}
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "select count(*) from BOARD"+gameDTO.getGameNumber()+searchCall;
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					i = resultSet.getInt(1);
				}
			} catch (Exception e) {
				log.info("현재 회원 수 검색 실패 - " + e);
			} finally {
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return i;
		}

		// 제네릭 PostDTO 클래스 반환 자료형으로 글 데이터를 조회하여 검색한다.
		public ArrayList<PostDTO> search(GameDTO gameDTO, int page, String topic, String keyfield, String keyword) {
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			ArrayList<PostDTO> arrayList = new ArrayList<PostDTO>();
			int startrow = (page-1)*10 +1;
			int endrow = startrow + 9;
			String searchCall1 = "";
			if (!topic.equals("")) {
				searchCall1 = " where postTopic = '"+topic+"' ";
			}
			String searchCall2 = "";
			if (keyfield.equals("")) {
				searchCall2 = " where (memId like '%' || '" + keyword + "' || '%' ) or (postTitle like '%' || '" + keyword
						+ "' || '%') or (postContent like '%' || '" + keyword + "' || '%')";
			} else if(keyfield.equals("memId")) {
				searchCall2 = " where memId like '%' || '" + keyword + "' || '%'";
			} else if(keyfield.equals("postTitle")) {
				searchCall2 = " where postTitle like '%' || '" + keyword + "' || '%'";
			} else if(keyfield.equals("postContent")) {
				searchCall2 = " where postContent like '%' || '" + keyword + "' || '%'";
			}
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "select * from (select rownum rnum, postNumber, postTopic, postTitle, memId, postDate ";
				sql+= "from (select * from board" + gameDTO.getGameNumber()+searchCall1+" order by postNumber desc)"+searchCall2+") ";
				sql+= " where rnum between "+startrow+" and "+endrow;
				log.info("SQL - " + sql);
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				while (resultSet.next()) {
					PostDTO postDTO = new PostDTO();
					postDTO.setPostNumber(resultSet.getInt("postNumber"));
					postDTO.setPostTopic(resultSet.getString("postTopic"));
					postDTO.setPostTitle(resultSet.getString("postTitle"));
					postDTO.setMemId(resultSet.getString("memId"));
					postDTO.setPostDate(resultSet.getString("postDate"));
					arrayList.add(postDTO);
					log.info("조회 데이터 확인 - " + postDTO);
				}
			} catch (Exception e) {
				log.info("게시판 글 조회 실패 - " + e);
			} finally {
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return arrayList;
		}
		public int searchCount(GameDTO gameDTO,String topic, String keyfield, String keyword) {
			int i = 0;
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String searchCall1 = "";
			if (!topic.equals("")) {
				searchCall1 = " where postTopic = '"+topic+"' ";
			}
			String searchCall2 = "";
			if (keyfield.equals("")) {
				searchCall2 = " where (memId like '%' || '" + keyword + "' || '%' ) or (postTitle like '%' || '" + keyword
						+ "' || '%') or (postContent like '%' || '" + keyword + "' || '%')";
			} else if(keyfield.equals("memId")) {
				searchCall2 = " where memId like '%' || '" + keyword + "' || '%'";
			} else if(keyfield.equals("postTitle")) {
				searchCall2 = " where postTitle like '%' || '" + keyword + "' || '%'";
			} else if(keyfield.equals("postContent")) {
				searchCall2 = " where postContent like '%' || '" + keyword + "' || '%'";
			}
			try {
				Context context = new InitialContext();
				DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
				connection = dataSource.getConnection();
				String sql = "select count(*) from (select * from BOARD"+gameDTO.getGameNumber()+searchCall1+") " +searchCall2;
				statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);
				if (resultSet.next()) {
					i = resultSet.getInt(1);
				}
			} catch (Exception e) {
				log.info("현재 회원 수 검색 실패 - " + e);
			} finally {
				try {
					resultSet.close();
					statement.close();
					connection.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return i;
		}
}
