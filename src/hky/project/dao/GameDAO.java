package hky.project.dao;

import java.sql.Connection;
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

public class GameDAO {
private static final Log log = LogFactory.getLog(MemberDAO.class);
	
	public GameDAO() {
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			log.info("데이터베이스 연결 성공" + dataSource);
		} catch (Exception e) {
			log.info("데이터베이스 연결 실패 - " + e);
		}
	}
	public ArrayList<GameDTO> selectAll(){
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ArrayList<GameDTO> arrayList = new ArrayList<GameDTO>();
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql="select gameNumber, gameName, gamePlatform, gameGenre, gameImage, gameDate ";
			sql += " from boardList where gameNumber > 0 order by gameNumber desc";
			log.info("SQL - "+sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				GameDTO gameDTO = new GameDTO();
				gameDTO.setGameNumber(resultSet.getInt("gameNumber"));
				gameDTO.setGameName(resultSet.getString("gameName"));
				gameDTO.setGamePlatform(resultSet.getString("gamePlatform"));
				gameDTO.setGameGenre(resultSet.getString("gameGenre"));
				gameDTO.setGameDate(resultSet.getString("gameDate").substring(0, 10));
				gameDTO.setGameImage(resultSet.getString("gameImage"));
				arrayList.add(gameDTO);
				log.info("조회 데이터 확인 - "+gameDTO);
			}
			if (resultSet.getRow() == 0) {
				log.info("등록된 게시판이 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrayList;
	}

	public GameDTO select(GameDTO gameDTO) {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Context context = new InitialContext();
			DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc");
			connection = dataSource.getConnection();
			String sql="select * from boardList where gameNumber ='"+gameDTO.getGameNumber()+"'";
			log.info("SQL - "+sql);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			if(resultSet.next()) {
				gameDTO.setGameNumber(resultSet.getInt("gameNumber"));
				gameDTO.setGameName(resultSet.getString("gameName"));
				gameDTO.setGamePlatform(resultSet.getString("gamePlatform"));
				gameDTO.setGameGenre(resultSet.getString("gameGenre"));
				gameDTO.setGameDate(resultSet.getString("gameDate"));
				gameDTO.setGameImage(resultSet.getString("gameImage"));
				log.info("조회 데이터 확인 - "+gameDTO);
			}
		} catch (Exception e) {
			log.info("게시판 검색 실패 - " + e);
		} finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return gameDTO;
	}
}
