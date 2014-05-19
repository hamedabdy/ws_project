package com.db;

import java.util.Date;
import java.util.Random;

public class Video {

	private int id;
	private String filePath;
	private String fileName;
	private String fileExt;
	private String title;
	private Date uploadDate;
	
	public Video() {
		generateId();
		uploadDate = new Date();
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}
	
	public void generateId() {
		int x = (new Random()).nextInt();
		setId(x);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	public String getFileExt() {
		return fileExt;
	}


	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}
