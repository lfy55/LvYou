package com.sqk.model;

import java.util.Date;

/**
 * Scenic实体类
 * 
 * @author SongQiankun
 */
public class Scenic {
	private int userId;
	private String scenicName;
	private Date date;
	private String feelText;

	public Scenic() {
	}

	public Scenic(int uId, String sName, Date date, String fText) {
		this.userId = uId;
		this.scenicName = sName;
		this.date = date;
		this.feelText = fText;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getScenicName() {
		return scenicName;
	}

	public void setScenicName(String scenicName) {
		this.scenicName = scenicName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFeelText() {
		return feelText;
	}

	public void setFeelText(String feelText) {
		this.feelText = feelText;
	}
}
