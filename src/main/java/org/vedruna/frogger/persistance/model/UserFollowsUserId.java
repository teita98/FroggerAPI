package org.vedruna.frogger.persistance.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
public class UserFollowsUserId implements Serializable {
    private Integer followerId;
    private Integer followingId;

    public UserFollowsUserId() {}

    public UserFollowsUserId(Integer followerId, Integer followingId) {
        this.followerId = followerId;
        this.followingId = followingId;
    }

    // Getters

    public Integer getFollowerId() {
        return followerId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public Integer getFollowingId() {
        return followingId;
    }

    public void setFollowingId(Integer followingId) {
        this.followingId = followingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFollowsUserId)) return false;
        UserFollowsUserId that = (UserFollowsUserId) o;
        return followerId.equals(that.followerId) &&
                followingId.equals(that.followingId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(followerId, followingId);
    }
}
