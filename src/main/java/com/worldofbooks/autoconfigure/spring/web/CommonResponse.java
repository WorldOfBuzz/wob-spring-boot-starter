package com.worldofbooks.autoconfigure.spring.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonResponse {

    private String errorCode;
    private Map<String, Object> parameters = new HashMap<>();
    private String exceptionMessage;
    private List<String> eventMessages = new ArrayList<>();
    private List<String> errorMessages = new ArrayList<>();

    public CommonResponse addParameter(String key, Object value) {
        parameters.put(key, value);
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

    public String getErrorCode() {
        return errorCode;
    }

    public CommonResponse setErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public CommonResponse setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public CommonResponse setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
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
