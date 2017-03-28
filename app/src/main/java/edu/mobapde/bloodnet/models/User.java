package edu.mobapde.bloodnet.models;

import android.text.TextUtils;

import java.util.Calendar;

import edu.mobapde.bloodnet.utils.StringUtils;

/**
 * Created by Luisa Gilig on 20/03/2017.
 */

public class User {



    private String name;
    private String bloodType;
    private String contactNum;
    private String gender;
    private Calendar birthdate;


    public User(){};

    public User(int id, String name, String bloodType, String contactNum, String gender, Calendar birthdate) {
        this.name = name;
        this.bloodType = bloodType;
        this.contactNum = contactNum;
        this.gender = gender;
        this.birthdate = birthdate;
    }

    public String getName() {
        return StringUtils.defaultString(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBloodType() {
        return StringUtils.defaultString(bloodType);
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getContactNum() {
        return StringUtils.defaultString(contactNum);
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public String getGender() {
        return StringUtils.defaultString(gender);
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
