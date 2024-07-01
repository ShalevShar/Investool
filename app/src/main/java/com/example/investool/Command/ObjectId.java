package com.example.investool.Command;

public class ObjectId {
    private String superapp;
    private String id;

    public String getSuperapp() {
        return superapp;
    }

    public ObjectId setSuperapp(String superapp) {
        this.superapp = superapp;
        return this;
    }

    public String getId() {
        return id;
    }

    public ObjectId setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String toString() {
        return "ObjectId{" +
                "superapp='" + superapp + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
