package com.sqk.model;


/**
 * Message实体类
 * 
 * @author SongQiankun
 */
public class Message {
	private int messageId;
	private int ownerId;
	private String createDate;
	private String messageText;
	private int goodNum;
	private int shareNum;

	public Message() {
	}

	public Message(int mId, int oId, String cDate, String mText, int gN, int sN) {
		this.messageId = mId;
		this.ownerId = oId;
		this.createDate = cDate;
		this.messageText = mText;
		this.goodNum = gN;
		this.shareNum = sN;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public int getGoodNum() {
		return goodNum;
	}

	public void setGoodNum(int goodNum) {
		this.goodNum = goodNum;
	}

	public int getShareNum() {
		return shareNum;
	}

	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}
}
