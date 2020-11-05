package org.maktab.photogallery.network.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Photos{

	@SerializedName("perpage")
	private int perpage;

	@SerializedName("total")
	private int total;

	@SerializedName("pages")
	private int pages;

	@SerializedName("photo")
	private List<PhotoItem> photo;

	@SerializedName("page")
	private int page;

	public int getPerpage(){
		return perpage;
	}

	public int getTotal(){
		return total;
	}

	public int getPages(){
		return pages;
	}

	public List<PhotoItem> getPhoto(){
		return photo;
	}

	public int getPage(){
		return page;
	}
}