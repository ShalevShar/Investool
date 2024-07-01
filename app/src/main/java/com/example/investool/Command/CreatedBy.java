package com.example.investool.Command;

public class CreatedBy {
    private UserKey userId;

    public CreatedBy() {
    }

    public UserKey getUserId() {
        return userId;
    }

    public CreatedBy setUserId(UserKey userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "createdBy{" +
                "userId=" + userId +
                '}';
    }
}
