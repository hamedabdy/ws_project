package com.db;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Video {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Id
	private String filePath;
	private String fileName;
	private String fileFormat;
	private String description;
	private Date uploadDate;
	
	
	public Video() {
		// TODO Auto-generated constructor stub
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

	
	public String getFileFormat() {
		return fileFormat;
	}


	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

}
