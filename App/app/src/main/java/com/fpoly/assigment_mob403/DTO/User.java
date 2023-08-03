package com.fpoly.assigment_mob403.DTO;

public class User {
    private String _id;
    private String username;
    private String password;
    private String email;
    private String fullName;

    private String avatar;

    public User() {
    }

    public User(String _id, String username, String password, String email, String fullName,String avatar) {
        this._id = _id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public User(String username, String password, String email, String fullName, String avatar) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id='" + _id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
