package org.maktab.photogallery.network.model;

import com.google.gson.annotations.SerializedName;

public class PhotoItem{

	@SerializedName("owner")
	private String owner;

	@SerializedName("server")
	private String server;

	@SerializedName("ispublic")
	private int ispublic;

	@SerializedName("isfriend")
	private int isfriend;

	@SerializedName("farm")
	private int farm;

	@SerializedName("id")
	private String id;

	@SerializedName("url_s")
	private String urlS;

	@SerializedName("secret")
	private String secret;

	@SerializedName("title")
	private String title;

	@SerializedName("isfamily")
	private int isfamily;

	public String getOwner(){
		return owner;
	}

	public String getServer(){
		return server;
	}

	public int getIspublic(){
		return ispublic;
	}

	public int getIsfriend(){
		return isfriend;
	}

	public int getFarm(){
		return farm;
	}

	public String getId(){
		return id;
	}

	public String getUrlS() {
		return urlS;
	}

	public String getSecret(){
		return secret;
	}

	public String getTitle(){
		return title;
	}

	public int getIsfamily(){
		return isfamily;
	}
}