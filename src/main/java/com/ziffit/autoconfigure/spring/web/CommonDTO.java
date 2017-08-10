package com.ziffit.autoconfigure.spring.web;

import java.util.ArrayList;
import java.util.List;

public class CommonDTO {

    private List<String> eventMessages = new ArrayList<>();
    private List<String> errorMessages = new ArrayList<>();

    public void addEventMessage(String message) {
        eventMessages.add(message);
    }

    public void addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
    }

    public List<String> getEventMessages() {
        return eventMessages;
    }

    public void setEventMessages(List<String> eventMessages) {
        this.eventMessages = eventMessages;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
}
