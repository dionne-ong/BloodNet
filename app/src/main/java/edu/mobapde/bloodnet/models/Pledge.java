package edu.mobapde.bloodnet.models;

import java.util.GregorianCalendar;

/**
 * Created by Luisa Gilig on 19/03/2017.
 */

public class Pledge {

    private User user;
    private Post post;

    public Pledge(int userId, int postId){
        // user = db.get (userId);
        // post = db.get (postId);

        user = new User(2, "Darlene Marpa", "A+", "09127381928", "F", new GregorianCalendar(1997, 11, 8));
        post = new Post(5, user, "Philippine General", "Somewhere St. 32 Bacon City", 5, 3, new GregorianCalendar());
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

}
