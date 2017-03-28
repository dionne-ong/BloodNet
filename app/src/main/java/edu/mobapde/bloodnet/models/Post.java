package edu.mobapde.bloodnet.models;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class Post {
    private int id;
    private int userId;

    private String patientName;
    private String bloodType;
    private String contactNum;

    private String hospitalName;
    private String hospitalAddress;

    private int neededBags;
    private int pledgedBags;
    private GregorianCalendar datePosted;

    public Post(){}

    public Post(int id, int userId, String patientName, String bloodType, String contactNum, String hospitalName, String hospitalAddress, int neededBags, int pledgedBags, GregorianCalendar datePosted) {
        this.id = id;
        this.userId = userId;
        this.patientName = patientName;
        this.bloodType = bloodType;
        this.contactNum = contactNum;
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.neededBags = neededBags;
        this.pledgedBags = pledgedBags;
        this.datePosted = datePosted;
    }

    public Post(int id, User user, String hospitalName, String hospitalAddress, int neededBags, int pledgedBags, GregorianCalendar datePosted){
        this.id = id;
        this.patientName = user.getName();
        this.bloodType = user.getBloodType();
        this.contactNum = user.getContactNum();
        this.hospitalName = hospitalName;
        this.hospitalAddress = hospitalAddress;
        this.neededBags = neededBags;
        this.pledgedBags = pledgedBags;
        this.datePosted = datePosted;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public int getNeededBags() {
        return neededBags;
    }

    public void setNeededBags(int neededBags) {
        this.neededBags = neededBags;
    }

    public int getPledgedBags() {
        return pledgedBags;
    }

    public void setPledgedBags(int pledgedBags) {
        this.pledgedBags = pledgedBags;
    }

    public GregorianCalendar getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(GregorianCalendar datePosted) {
        this.datePosted = datePosted;
    }
}
