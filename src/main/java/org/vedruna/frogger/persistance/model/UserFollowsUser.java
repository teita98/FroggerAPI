package org.vedruna.frogger.persistance.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_follows_user")
@Getter
@Setter
public class UserFollowsUser {

    @EmbeddedId
    private UserFollowsUserId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "user_who_follows", referencedColumnName = "user_id",nullable = false)
    private User follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "user_to_follow", referencedColumnName = "user_id", nullable = false)
    private User following;

    public UserFollowsUser() {
    }

    public UserFollowsUser(User follower, User following) {
        this.id = new UserFollowsUserId(follower.getUserId(), following.getUserId());
        this.follower = follower;
        this.following = following;
    }

    // Getters

    public UserFollowsUserId getId() {
        return id;
    }

    public void setId(UserFollowsUserId id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
