package com.example.investool.Command;

public class TargetObject {
    private ObjectId objectId;

    public ObjectId getObjectId() {
        return objectId;
    }
    public TargetObject setObjectId(ObjectId objectId) {
        this.objectId = objectId;
        return this;
    }

    @Override
    public String toString() {
        return "TargetObject{" +
                "objectId=" + objectId +
                '}';
    }
}
