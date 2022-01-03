package hky.project.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import hky.project.dto.MemberDTO;

public class MemberDAO {
private static final Log log = LogFactory.getLog(MemberDAO.class);
	
	public MemberDAO() {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			log.info("데이터베이스 연결 성공 " + dataSource);
		} catch (Exception e) {
			log.info("데이터베이스 연결 실패 - " + e);
		}
	}

	// MemberDTO 클래스 반환 자료형으로 특정 회원 아이디와 비밀번호를 조회한다.
	public MemberDTO login(MemberDTO memberDTO) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			// 쿼리문 작성
			String sql = "select memId, memPasswd from member where memid = '" + memberDTO.getMemId() + "'";
			log.info("SQL - " + sql);
			statement = connection.createStatement();
			// SQL문을 실행하고 데이터를 조회한다.
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				memberDTO.setMemId(resultSet.getString("memId"));
				log.info("아이디 확인 - " + resultSet.getString("memId"));
				if (resultSet.getString("memPasswd").equals(memberDTO.getMemPasswd())) {
					memberDTO.setMemPasswd(resultSet.getString("memPasswd"));
					log.info("비밀번호 확인 - " + resultSet.getString("memPasswd"));
				} else {
					memberDTO.setMemPasswd("");
				}
			} else {
				memberDTO.setMemId("");
			}
		} catch (Exception e) {
			log.info("로그인 실패 - " + e);
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}

	public ArrayList<MemberDTO> selectAll(int page, int limit) {
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		int startrow = (page - 1)*10 +1;
		int endrow = startrow + limit -1;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			// 쿼리문 입력
			String sql = "select * from (select rownum rnum, memNumber, memId, memEmail, memDate from (select * ";
			sql += "from member where memId != 'admin' order by memNumber desc))";
			sql += " where rnum between "+startrow+" and "+endrow;
			log.info("SQL - " + sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setMemNumber(resultSet.getInt("memNumber"));
				memberDTO.setMemId(resultSet.getString("memId"));
				memberDTO.setMemEmail(resultSet.getString("memEmail"));
				memberDTO.setMemDate(resultSet.getString("memDate").substring(0, 10));
				arrayList.add(memberDTO);
				log.info("조회 데이터 확인" + memberDTO);
			}
			if (resultSet.getRow() == 0) {
				log.info("등록된 회원이 없습니다.");
			}
		} catch (Exception e) {
			log.info("전체 회원 조회 실패 - " + e);
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

	// MemberDTO 클래스 반환 자료형으로 특정 회원 데이터를 조회한다.
	public MemberDTO select(MemberDTO memberDTO) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			// 쿼리문 작성
			String sql = "select * from member";
			sql += " where memId = '" + memberDTO.getMemId() + "'";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				log.info("아이디 확인 - " + resultSet.getString("memId"));
				memberDTO.setMemNumber(resultSet.getInt("memNumber"));
				memberDTO.setMemId(resultSet.getString("memId"));
				memberDTO.setMemEmail(resultSet.getString("memEmail"));
				memberDTO.setMemName(resultSet.getString("memName"));
				memberDTO.setMemPhone(resultSet.getString("memPhone"));
				memberDTO.setMemBirth(resultSet.getString("memBirth").substring(0, 10));
				memberDTO.setMemGender(resultSet.getString("memGender"));
				memberDTO.setMemAddress(resultSet.getString("memAddress"));
				memberDTO.setMemComment(resultSet.getString("memComment"));
				memberDTO.setMemPoint(resultSet.getInt("memPoint"));
				memberDTO.setMemDate(resultSet.getString("memDate"));
			}
		} catch (Exception e) {
			log.info("개별 회원 조회 실패 - " + e);
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}

	// 회원 데이터베이스에 새로운 회원 데이터를 입력한다.
	public MemberDTO insert(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "insert into member values (?,?,?,?,?,?,?,?,?,50,sysdate,?)";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getMemId());
			preparedStatement.setString(2, memberDTO.getMemPasswd());
			preparedStatement.setString(3, memberDTO.getMemEmail());
			preparedStatement.setString(4, memberDTO.getMemName());
			preparedStatement.setString(5, memberDTO.getMemPhone());
			preparedStatement.setString(6, memberDTO.getMemBirth());
			preparedStatement.setString(7, memberDTO.getMemGender());
			preparedStatement.setString(8, memberDTO.getMemAddress());
			preparedStatement.setString(9, memberDTO.getMemComment());
			preparedStatement.setInt(10, memberDTO.getMemNumber());
			int count = preparedStatement.executeUpdate();
			log.info("입력 데이터 확인 - " + memberDTO);
			if (count > 0) {
				connection.commit();
				log.info("커밋되었습니다.");
			} else {
				connection.rollback();
				log.info("롤백되었습니다.");
			}
		} catch (Exception e) {
			log.info("회원 가입 실패 - " + e);
		} finally {
			try {
				connection.close();
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}

	// MemberDTO 클래스 반환 자료형으로 특정 회원 데이터를 수정한다.
	public MemberDTO update(MemberDTO memberDTO) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		log.info(memberDTO);
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "update member set memPasswd = ?, memEmail = ?, memPhone = ?, memAddress = ?, memComment = ? ";
			sql += "where memId = ?";
			log.info("SQL - " + sql);
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, memberDTO.getMemPasswd());
			preparedStatement.setString(2, memberDTO.getMemEmail());
			preparedStatement.setString(3, memberDTO.getMemPhone());
			preparedStatement.setString(4, memberDTO.getMemAddress());
			preparedStatement.setString(5, memberDTO.getMemComment());
			preparedStatement.setString(6, memberDTO.getMemId());
			int count = preparedStatement.executeUpdate();
			log.info("수정 데이터 확인 - " + memberDTO);
			if (count > 0) {
				connection.commit();
				log.info("커밋되었습니다.");
			} else {
				connection.rollback();
				log.info("롤백되었습니다.");
			}
		} catch (Exception e) {
			log.info("회원 정보 수정 실패 - " + e);
		} finally {
			try {
				connection.close();
				preparedStatement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}

	// MemberDTO 클래스 반환 자료형으로 특정 회원 데이터를 삭제한다.
	public MemberDTO delete(MemberDTO memberDTO) {
		Connection connection = null;
		Statement statement = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "delete from member where memid = '" + memberDTO.getMemId() + "'";
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
			log.info("회원 삭제 실패 - " + e);
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}

	public ArrayList<MemberDTO> search(String keyword, int page, int limit) {
		log.info("검색 단어 확인 - " + keyword);
		String searchCall = "";
		if (!keyword.equals("")) {
			searchCall = "where memId like '%" + keyword + "%'";
		}
		ArrayList<MemberDTO> arrayList = new ArrayList<MemberDTO>();
		int startrow = (page - 1)*10 +1;
		int endrow = startrow + limit -1;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "select * from (select rownum rnum, memNumber, memId, memEmail, memDate from (select * ";
			sql += "from member "+searchCall+" order by memNumber desc) where memId != 'admin')";
			sql += " where rnum between "+startrow+" and "+endrow;
			log.info("SQL - " + sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				MemberDTO memberDTO = new MemberDTO();
				memberDTO.setMemNumber(resultSet.getInt("memNumber"));
				memberDTO.setMemId(resultSet.getString("memId"));
				memberDTO.setMemEmail(resultSet.getString("memEmail"));
				memberDTO.setMemDate(resultSet.getString("memDate").substring(0, 10));
				arrayList.add(memberDTO);
				log.info("조회 데이터 확인" + memberDTO);
			}
			if (resultSet.getRow() == 0) {
				log.info("등록된 회원이 없습니다.");
			}
		} catch (Exception e) {
			log.info("회원 포인트 검색 실패 - " + e);
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

	// MemberDTO 클래스 반환 자료형으로 특정 회원의 포인트를 수정한다.
	public MemberDTO pointUpdate(MemberDTO memberDTO) {
		Connection connection = null;
		Statement statement = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "update member set memPoint = " + memberDTO.getMemPoint();
			sql += " where memId = '" + memberDTO.getMemId() + "'";
			log.info("SQL - " + sql);
			statement = connection.createStatement();
			int count = statement.executeUpdate(sql);
			log.info("수정 데이터 확인 - " + memberDTO);
			if (count > 0) {
				connection.commit();
				log.info("커밋되었습니다.");
			} else {
				connection.rollback();
				log.info("롤백되었습니다.");
			}
		} catch (Exception e) {
			log.info("회원 기본 수정 실패 - " + e);
		} finally {
			try {
				statement.close();
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return memberDTO;
	}
	
	public int maxNumber() {
		int i = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "select max(memNumber) from member";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				i = resultSet.getInt("max(memNumber)");
			}
		} catch (Exception e) {
			log.info("최대 회원 번호 검색 실패 - " + e);
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
	public int memberCount() {
		int i = 0;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "select count(*) from member where memId != 'admin'";
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
	
	public int searchMemberCount(String keyword) {
		int i = 0;
		log.info("검색 단어 확인 - " + keyword);
		String searchCall = "";
		if (!keyword.equals("")) {
			searchCall = "memId like '%" + keyword + "%'";
		}
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql = "select count(*) from (select * from member where memId != 'admin') where "+searchCall;
			log.info("SQL 확인 - "+sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				i = resultSet.getInt(1);
			}
		} catch (Exception e) {
			log.info("아이디 검색 회원 수 검색 실패 - " + e);
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
