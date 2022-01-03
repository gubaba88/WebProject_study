package hky.project.dto;

public class PostDTO {
	private int gameNumber;
	private int postNumber;
	private String postTopic;
	private String postTitle;
	private String memId;
	private String postContent;
	private String postDate;
	private String fileName;
	
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
	public String getPostTopic() {
		return postTopic;
	}
	public void setPostTopic(String postTopic) {
		this.postTopic = postTopic;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public String getPostDate() {
		return postDate;
	}
	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public String toString() {
		return "PostDTO [gameNumber=" + gameNumber + ", postNumber=" + postNumber + ", postTopic=" + postTopic
				+ ", postTitle=" + postTitle + ", memId=" + memId + ", postContent=" + postContent + ", postDate="
				+ postDate + ", fileName=" + fileName + "]";
	}
}
