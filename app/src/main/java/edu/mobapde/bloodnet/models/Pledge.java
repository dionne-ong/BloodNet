package edu.mobapde.bloodnet.models;

import java.util.GregorianCalendar;

import edu.mobapde.bloodnet.models.posts.Post;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class Pledge {

    public static final String PLEDGE_EXTRA = "pledge_extra";
    public static final String FIELD_USERID = "userid";
    public static final String FIELD_POSTID = "postid";
    public static final String FIELD_DONATED = "donated";

    private User user;
    private Post post;


    private Boolean donated;

    public Pledge(int userId, int postId, Boolean isDonated){
        // user = db.get (userId);
        // post = db.get (postId);
        this.donated = isDonated;
        user = new User("Winnie The Pooh", "B+", "09178075984", "F", new GregorianCalendar(1997, 11, 8).getTimeInMillis());
        post = new Post("5", user, "Chinese General Hospital", "286 Blumentritt Rd, Sampaloc, Manila, Metro Manila", 5, 3, new GregorianCalendar().getTimeInMillis());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


    public Boolean getDonated() {
        return donated;
    }

    public void setDonated(Boolean donated) {
        this.donated = donated;
    }
}
