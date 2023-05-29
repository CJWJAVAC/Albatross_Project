package com.company.albatross.models;

public class Post {
    private String employerIdToken, id, name, job, day,
            education, eperiod, gender, image, period,
            startHour, startMinute, endHour,
            endMinute, phoneNumber, region, wage;


    public Post() {
    }

    public Post(String employerIdToken, String id, String name,
                String job, String day, String education, String eperiod,
                String gender, String image, String period, String startHour,
                String startMinute, String endHour, String endMinute,
                String phoneNumber, String region, String wage) {
        this.employerIdToken = employerIdToken;
        this.id = id;
        this.name = name;
        this.job = job;
        this.day = day;
        this.education = education;
        this.eperiod = eperiod;
        this.gender = gender;
        this.image = image;
        this.period = period;
        this.startHour = startHour;
        this.startMinute = startMinute;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.phoneNumber = phoneNumber;
        this.region = region;
        this.wage = wage;
    }

    public String getEmployerIdToken() {
        return employerIdToken;
    }

    public void setEmployerIdToken(String employerIdToken) {
        this.employerIdToken = employerIdToken;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEperiod() {
        return eperiod;
    }

    public void setEperiod(String eperiod) {
        this.eperiod = eperiod;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(String startMinute) {
        this.startMinute = startMinute;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }

    public String getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(String endMinute) {
        this.endMinute = endMinute;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }
}
