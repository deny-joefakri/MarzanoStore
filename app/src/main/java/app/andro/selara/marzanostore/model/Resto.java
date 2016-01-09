package app.andro.selara.marzanostore.model;

import java.util.ArrayList;

public class Resto {
	private String title, desc, type, image;


	public Resto() {
	}

	public Resto(String name, String desc, String type, String image) {
		this.title = name;
		this.desc = desc;
		this.type = type;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String name) {
		this.title = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}


}
