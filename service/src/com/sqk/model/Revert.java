package com.sqk.model;

import java.util.Date;

/**
 * Revert实体类
 * 
 * @author SongQiankun
 */
public class Revert {
	private int revertId;
	private int toRevertId = 0;
	private int messageId;
	private String revertText;
	private int ownerId;
	private String createDate;

	public Revert() {
	}

	public Revert(int rId, int toRId, int mId, String rText, int oId, String cDate) {
		this.revertId = rId;
		this.toRevertId = toRId;
		this.messageId = mId;
		this.revertText = rText;
		this.ownerId = oId;
		this.createDate = cDate;
	}

	public int getRevertId() {
		return revertId;
	}

	public void setRevertId(int revertId) {
		this.revertId = revertId;
	}

	public int getToRevertId() {
		return toRevertId;
	}

	public void setToRevertId(int toRevertId) {
		this.toRevertId = toRevertId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getRevertText() {
		return revertText;
	}

	public void setRevertText(String revertText) {
		this.revertText = revertText;
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
}
