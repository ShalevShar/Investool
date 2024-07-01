package com.example.investool.Command;

public class UserKey {
    private String superapp;
    private String email;

    public String getSuperapp() {
        return superapp;
    }
    public UserKey setSuperapp(String superapp) {
        this.superapp = superapp;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserKey setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "UserKey{" +
                "superapp='" + superapp + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
