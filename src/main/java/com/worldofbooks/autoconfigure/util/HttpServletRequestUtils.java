package com.worldofbooks.autoconfigure.util;

import com.google.common.base.Strings;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {

    public String extractUriFromServletRequest(HttpServletRequest request) {
        String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        if (Strings.isNullOrEmpty(uri)) {
            uri = (String) request.getAttribute(AsyncContext.ASYNC_REQUEST_URI);
        }

        return uri;
    }
}
