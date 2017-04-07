package edu.mobapde.bloodnet.models.posts;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import edu.mobapde.bloodnet.models.User;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class Post {
    private String id;
    private String userId;

    private String patientName;
    private String bloodType;
    private String contactNum;

    private String hospitalName;
    private String hospitalAddress;

    private int neededBags;
    private int pledgedBags;
    private long datePosted;

    private boolean hasPic;

    public Post(){}

    public Post(String id, String userId, String patientName, String bloodType, String contactNum,
                String hospitalName, String hospitalAddress, int neededBags, int pledgedBags, long datePosted) {
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

    public Post(String id, User user, String hospitalName, String hospitalAddress, int neededBags, int pledgedBags, long datePosted){
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

    public long getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(long datePosted) {
        this.datePosted = datePosted;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public boolean isHasPic() {
        return hasPic;
    }

    public void setHasPic(boolean hasPic) {
        this.hasPic = hasPic;
    }
}
