package com.example.investool.Command;

public class InvokedBy {
    private UserKey userId;

    public InvokedBy() {
    }

    public UserKey getUserId() {
        return userId;
    }

    public InvokedBy setUserId(UserKey userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "InvokedBy{" +
                "userID=" + userId +
                '}';
    }
}
