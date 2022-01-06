package hky.project.dto;

public class GameDTO {
	private int gameNumber;
	private String gameName;
	private String gamePlatform;
	private String gameGenre;
	private String gameDate;
	private String gameImage;
	public int getGameNumber() {
		return gameNumber;
	}
	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGamePlatform() {
		return gamePlatform;
	}
	public void setGamePlatform(String gamePlatform) {
		this.gamePlatform = gamePlatform;
	}
	public String getGameGenre() {
		return gameGenre;
	}
	public void setGameGenre(String gameGenre) {
		this.gameGenre = gameGenre;
	}
	public String getGameDate() {
		return gameDate;
	}
	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}
	public String getGameImage() {
		return gameImage;
	}
	public void setGameImage(String gameImage) {
		this.gameImage = gameImage;
	}
	@Override
	public String toString() {
		return "GameDTO [gameNumber=" + gameNumber + ", gameName=" + gameName + ", gamePlatform=" + gamePlatform
				+ ", gameGenre=" + gameGenre + ", gameDate=" + gameDate + ", gameImage=" + gameImage + "]";
	}
	
	
}
