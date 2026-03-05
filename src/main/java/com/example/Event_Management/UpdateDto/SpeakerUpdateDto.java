package com.example.Event_Management.UpdateDto;

import org.springframework.web.multipart.MultipartFile;

public class SpeakerUpdateDto {
    private String name;
    private String designation;
    private Long contactNumber;
    private String email;
    private String linkedInURL;
    private MultipartFile linkedIn;
    private MultipartFile profile;
    private String profileUrl;
    private String description;

    private Long eventId;

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public MultipartFile getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(MultipartFile linkedIn) {
        this.linkedIn = linkedIn;
    }

    public MultipartFile getProfile() {
        return profile;
    }

    public void setProfile(MultipartFile profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLinkedInURL() {
        return linkedInURL;
    }

    public void setLinkedInURL(String linkedInURL) {
        this.linkedInURL = linkedInURL;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
