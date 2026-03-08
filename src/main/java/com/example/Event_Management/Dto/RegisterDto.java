package com.example.Event_Management.Dto;

import com.example.Event_Management.Utils.AppUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RegisterDto {
    @NotNull(message = "userId cannot be null")
    private Long userId;
    @NotNull(message = "eventId cannot be null")
    private Long eventId;



    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }


}
