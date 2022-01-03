package hky.project.dto;

public class CommentDTO {
	private int comNumber;
	private String comWriter;
	private String comContent;
	private String comDate;
	private int gameNumber;
	private int postNumber;
	
	public int getComNumber() {
		return comNumber;
	}
	public void setComNumber(int comNumber) {
		this.comNumber = comNumber;
	}
	public String getComWriter() {
		return comWriter;
	}
	public void setComWriter(String comWriter) {
		this.comWriter = comWriter;
	}
	public String getComContent() {
		return comContent;
	}
	public void setComContent(String comContent) {
		this.comContent = comContent;
	}
	public String getComDate() {
		return comDate;
	}
	public void setComDate(String comDate) {
		this.comDate = comDate;
	}
	public int getGameNumber() {
		return gameNumber;
	}
	public void setGameNumber(int gameNumber) {
		this.gameNumber = gameNumber;
	}
	public int getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(int postNumber) {
		this.postNumber = postNumber;
	}
	
	@Override
	public String toString() {
		return "CommentDTO [comNumber=" + comNumber + ", comWriter=" + comWriter + ", comContent=" + comContent
				+ ", comDate=" + comDate + ", gameNumber=" + gameNumber + ", postNumber=" + postNumber + "]";
	}
}
