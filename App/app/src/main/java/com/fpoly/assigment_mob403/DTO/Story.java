package com.fpoly.assigment_mob403.DTO;

import java.io.Serializable;
import java.util.List;

public class Story implements Serializable {
    private String _id;
    private String name;
    private String describe;
    private String author;
    private long timeRelease;
    private String Background;
    private List<String> Images;
    private List<Comment> Comments;


    public Story() {
    }

    public Story(String _id, String name, String describe, String author, long timeRelease, String background, List<String> images, List<Comment> comments) {
        this._id = _id;
        this.name = name;
        this.describe = describe;
        this.author = author;
        this.timeRelease = timeRelease;
        Background = background;
        Images = images;
        Comments = comments;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getTimeRelease() {
        return timeRelease;
    }

    public void setTimeRelease(int timeRelease) {
        this.timeRelease = timeRelease;
    }

    public String getBackground() {
        return Background;
    }

    public void setBackground(String background) {
        Background = background;
    }

    public List<String> getImages() {
        return Images;
    }

    public void setImages(List<String> images) {
        Images = images;
    }

    public List<Comment> getComments() {
        return Comments;
    }

    public void setComments(List<Comment> comments) {
        Comments = comments;
    }

    @Override
    public String toString() {
        return "Story{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", author='" + author + '\'' +
                ", timeRelease=" + timeRelease +
                ", Background='" + Background + '\'' +
                ", Images=" + Images +
                ", Comments=" + Comments +
                '}';
    }
}
