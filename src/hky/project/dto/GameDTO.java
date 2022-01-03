package hky.project.dto;

public class GameDTO {
	private int gameNumber;
	private String gameName;
	private String platForm;
	private String genre;
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
	public String getPlatForm() {
		return platForm;
	}
	public void setPlatForm(String platForm) {
		this.platForm = platForm;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
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
		return "GameDTO [gameNumber=" + gameNumber + ", gameName=" + gameName + ", platForm=" + platForm + ", genre="
				+ genre + ", gameDate=" + gameDate + ", gameImage=" + gameImage + "]";
	}
	
}
