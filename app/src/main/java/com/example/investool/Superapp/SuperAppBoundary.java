package com.example.investool.Superapp;

import com.example.investool.Command.ObjectId;
import com.example.investool.Command.CreatedBy;

import java.util.Date;
import java.util.Map;

public class SuperAppBoundary {

    private String type;
    private String alias;
    private Boolean active;
    private Date creationTimestamp;
    private CreatedBy createdBy;
    private Map<String, Object> objectDetails;
    private ObjectId objectId;

    public SuperAppBoundary() {
    }

    public String getType() {
        return type;
    }

    public SuperAppBoundary setType(String type) {
        this.type = type;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public SuperAppBoundary setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public Boolean getActive() {
        return active;
    }

    public SuperAppBoundary setActive(Boolean active) {
        this.active = active;
        return this;
    }

    public Date getCreationTimestamp() {
        return creationTimestamp;
    }

    public SuperAppBoundary setCreationTimestamp(Date creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
        return this;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public SuperAppBoundary setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public Map<String, Object> getObjectDetails() {
        return objectDetails;
    }

    public SuperAppBoundary setObjectDetails(Map<String, Object> objectDetails) {
        this.objectDetails = objectDetails;
        return this;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public SuperAppBoundary setObjectId(ObjectId objectId) {
        this.objectId = objectId;
        return this;
    }

    @Override
    public String toString() {
        return "SuperAppBoundary{" +
                "type='" + type + '\'' +
                ", alias='" + alias + '\'' +
                ", active=" + active +
                ", creationTimestamp=" + creationTimestamp +
                ", createdBy=" + createdBy +
                ", objectDetails=" + objectDetails +
                ", objectId=" + objectId +
                '}';
    }
}
