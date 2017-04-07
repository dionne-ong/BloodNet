package edu.mobapde.bloodnet.models;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONObject;

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
    private long birthdate;
    private long pledgedate;

    public User(){
        this.birthdate = -1;
    };

    public User(String name, String bloodType, String contactNum, String gender, long birthdate) {
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

    public long getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(long birthdate) {
        this.birthdate = birthdate;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public long getPledgedate() {
        return pledgedate;
    }

    public void setPledgedate(long pledgedate) {
        this.pledgedate = pledgedate;
    }
}
