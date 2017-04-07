package edu.mobapde.bloodnet.DBObjects;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import edu.mobapde.bloodnet.models.User;

/**
 * Created by psion on 3/29/2017.
 */

public class DBOUser{

    public static final String REF_USER = "users";
    public static final String REF_USER_POST = "user-post";
    public static final String REF_USER_PLEDGE = "user-pledge";
    public static final String REF_PLEDGE_USER = "pledge-user";
    public static final String REF_USER_PLEDGE_DATE = "pledge-date";

}
