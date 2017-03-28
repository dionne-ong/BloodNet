package edu.mobapde.bloodnet.models;

import java.util.Calendar;

/**
 * Created by Luisa Gilig on 20/03/2017.
 */

public class User {
    private int id;
    private String name;
    private String bloodType;
    private String contactNum;
    private String gender;
    private Calendar birthdate;


    public User(int id, String name, String bloodType, String contactNum, String gender, Calendar birthdate) {
        this.id = id;
        this.name = name;
        this.bloodType = bloodType;
        this.contactNum = contactNum;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Calendar birthdate) {
        this.birthdate = birthdate;
    }
}
