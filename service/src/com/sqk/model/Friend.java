package com.sqk.model;

/**
 * Friend实体类
 * 
 * @author SongQiankun
 */
public class Friend {
	private int myId;
	private int friendId;

	public Friend() {
	}

	public Friend(int mId, int fId, int isF, int State) {
		this.myId = mId;
		this.friendId = fId;
	}

	public int getMyId() {
		return myId;
	}

	public void setMyId(int myId) {
		this.myId = myId;
	}

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

}
