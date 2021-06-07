package SimplePad;

import java.io.Serializable;

public class Content implements Serializable{
	String contentTitle;
	String contentText;
	
	public Content() {
		contentTitle = "noname";
		contentText = "";
	}
	public Content(String contentTitle, String contentText) {
		setContentTitle(contentTitle);
		setContentText(contentText);
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getContentText() {
		return contentText;
	}

	public void setContentText(String contentText) {
		this.contentText = contentText;
	}
}
