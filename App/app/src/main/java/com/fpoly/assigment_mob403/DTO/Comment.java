package com.fpoly.assigment_mob403.DTO;

import java.io.Serializable;

public class Comment implements Serializable {

    private String _id;
    private String storyID;
    private String userID;
    private String content;
    private long time;

    public Comment() {
    }

    public Comment(String _id, String storyID, String userID, String content, long time) {
        this._id = _id;
        this.storyID = storyID;
        this.userID = userID;
        this.content = content;
        this.time = time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getStoryID() {
        return storyID;
    }

    public void setStoryID(String storyID) {
        this.storyID = storyID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "_id='" + _id + '\'' +
                ", storyID='" + storyID + '\'' +
                ", userID='" + userID + '\'' +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }
}
