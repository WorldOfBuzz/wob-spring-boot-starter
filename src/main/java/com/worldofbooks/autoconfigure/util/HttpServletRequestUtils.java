package com.worldofbooks.autoconfigure.util;

import com.google.common.base.Strings;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import javax.servlet.AsyncContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtils {

    private static final String SPRING_EXCEPTION_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";

    public static String extractUriFromServletRequest(HttpServletRequest request) {
        String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        if (Strings.isNullOrEmpty(uri)) {
            uri = (String) request.getAttribute(AsyncContext.ASYNC_REQUEST_URI);
        }

        return uri;
    }

    public static Throwable extractExceptionFromServletRequest(HttpServletRequest request) {
        Throwable e = (Throwable) request.getAttribute(SPRING_EXCEPTION_ATTRIBUTE);

        if (e == null) {
            e = (Throwable) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        }

        return e;
    }
}
