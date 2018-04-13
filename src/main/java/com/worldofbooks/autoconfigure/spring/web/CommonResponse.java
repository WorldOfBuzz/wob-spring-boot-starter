package com.worldofbooks.autoconfigure.spring.web;

import java.util.ArrayList;
import java.util.List;

public class CommonResponse {

    private String exceptionMessage;
    private List<String> eventMessages = new ArrayList<>();
    private List<String> errorMessages = new ArrayList<>();

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public CommonResponse setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        return this;
    }

    public CommonResponse addEventMessage(String message) {
        eventMessages.add(message);
        return this;
    }

    public CommonResponse addErrorMessage(String errorMessage) {
        errorMessages.add(errorMessage);
        return this;
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
