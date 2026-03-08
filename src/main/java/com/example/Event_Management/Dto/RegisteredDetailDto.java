package com.example.Event_Management.Dto;

import com.example.Event_Management.Utils.AppUtils;

public class
RegisteredDetailDto { // used as response

    private Long registeredId;
    private String userName;
    private String eventName;
    private String registeredAt;
    private AppUtils.Status status;

    public Long getRegisteredId() {
        return registeredId;
    }

    public void setRegisteredId(Long registeredId) {
        this.registeredId = registeredId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(String registeredAt) {
        this.registeredAt = registeredAt;
    }

    public AppUtils.Status getStatus() {
        return status;
    }

    public void setStatus(AppUtils.Status status) {
        this.status = status;
    }
}
