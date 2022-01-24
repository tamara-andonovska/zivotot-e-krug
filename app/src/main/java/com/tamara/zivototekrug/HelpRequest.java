package com.tamara.zivototekrug;

public class HelpRequest {
    private String id;
    private String email;
    private String phone;
    private String helpType;
    private String description;
    private String emergencyLevel;
    private String repeated;
    private String location;
    private String rating;
    private String date;
    private String time;
    private String status;
    private String volunteerName;
    private String volunteerPhone;
    private String volunteerEmail;
    private String volunteerRating;
    private String userId;
    private String volunteerId;

    public HelpRequest() {}

    public HelpRequest(String id, String email, String phone, String helpType, String description, String emergencyLevel, String repeated, String location, String rating, String date, String time, String volunteerName, String volunteerPhone, String volunteerEmail, String volunteerRating, String status, String userId, String volunteerId) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.helpType = helpType;
        this.description = description;
        this.emergencyLevel = emergencyLevel;
        this.repeated = repeated;
        this.location = location;
        this.rating = rating;
        this.date = date;
        this.time = time;
        this.volunteerName = volunteerName;
        this.volunteerPhone = volunteerPhone;
        this.volunteerEmail = volunteerEmail;
        this.volunteerRating = volunteerRating;
        this.status = status;
        this.userId = userId;
        this.volunteerId = volunteerId;
    }

    public String getHelpType() {
        return helpType;
    }

    public String getDescription() { return description; }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public String getRepeated() {
        return repeated;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() { return email; }

    public String getDate() { return date; }

    public String getTime() { return time; }

    public String getStatus() {
        return status;
    }

    public String getVolunteerName() {
        return volunteerName;
    }

    public String getVolunteerPhone() {
        return volunteerPhone;
    }

    public String getVolunteerEmail() {
        return volunteerEmail;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getVolunteerRating() {
        return volunteerRating;
    }

    public String getRating() {
        return rating;
    }

    public String getVolunteerId() {
        return volunteerId;
    }

    public String getPhone() {
        return phone;
    }
}
