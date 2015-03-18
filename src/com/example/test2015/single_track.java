package com.example.test2015;

public class single_track {
	private String title, artist, thumbnailUrl, info, id, rate, preis;
	private boolean imgfromlocal;
	Integer localimg;
	public single_track(){
		//this.imgfromlocal = false;
	}
	public single_track(String title, String artist, String thumbnailUrl, String info, String id, Integer localimg, boolean imgfromlocal, String rate, String preis){
		this.title = title;
		this.artist = artist;
		this.thumbnailUrl = thumbnailUrl;
		this.info = info;
		this.id = id;
		this.localimg = localimg;
		this.imgfromlocal = imgfromlocal;
		this.rate = rate;
		this.preis = preis;
	}
	
	public boolean getImgFromLocal(){
		return this.imgfromlocal;
	}
	
	public void setImgFromLocal(boolean imgfromlocal){
		this.imgfromlocal = imgfromlocal;
	}
	
	public Integer getLocalImg(){
		return this.localimg; 
	}
	
	public void setLocalImg(Integer localimg){
		this.localimg = localimg;
	}
	
	public String getId(){
		return this.id;
	}
	
	public void setId(String id) {
        this.id = id;
    }
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String name) {
        this.title = name;
    }
	
	public String getArtist(){
		return this.artist;
	}
	
	public void setArtist(String name) {
        this.artist = name;
    }
	
	public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }
	
	public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
	
	public String getInfo(){
		return this.info;
	}
	
	public void setInfo(String name) {
        this.info = name;
    }
	
	public void setRate(String name) {
		this.rate = name;
	}
	
	public String getRate () {
		return this.rate;
	}
	
	public void setPreis(String name ){
		this.preis = name;
	}
	
	public String getPreis () {
		return this.preis;
	}
}
