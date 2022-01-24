package com.tamara.zivototekrug;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String phone;
    private String userType;
    private String rating;
    private String ratedNo;

    public User() {
    }

    public User(String email, String password, String fullName, String phone, String userType, String rating, String ratedNo) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phone = phone;
        this.userType = userType;
        this.rating = rating;
        this.ratedNo = ratedNo;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserType() {
        return userType;
    }

    public String getRating() {
        return rating;
    }

    public String getRatedNo() {
        return ratedNo;
    }
}
