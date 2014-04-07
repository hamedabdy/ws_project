package com.db;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class AddConcerts {

	private long id;
	private String title;
	private String artists;
	
	@Id
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtists() {
		return artists;
	}

	public void setArtists(String artists) {
		this.artists = artists;
	}

	public AddConcerts() {
		// TODO Auto-generated constructor stub
	}

}
