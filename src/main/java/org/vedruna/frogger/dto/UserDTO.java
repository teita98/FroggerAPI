package org.vedruna.frogger.dto;

import lombok.*;


@Getter
@Setter
public class UserDTO {
    private Integer userId;
    private String username;
    private String email;
    private Integer followingCount;
    private Integer followersCount;
    private String recordTime;

    public UserDTO() { }

    public UserDTO( Integer userId, String username, String email, Integer followingCount, Integer followersCount, String recordTime) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.followingCount = followingCount;
        this.followersCount = followersCount;
        this.recordTime = recordTime;
    }


    // Getters y Setters
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getFollowingCount() {
        return followingCount;
    }
    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }
    public Integer getFollowersCount() {
        return followersCount;
    }
    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }
    public String getRecordTime() {
        return recordTime;
    }
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}
