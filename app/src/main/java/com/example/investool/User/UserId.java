package com.example.investool.User;

public class UserId {
    private String superapp;
    private String email;

    public String getSuperapp() {
        return superapp;
    }

    public void setSuperapp(String superapp) {
        this.superapp = superapp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserId{" +
                "superapp='" + superapp + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}