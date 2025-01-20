package com.projects.currencyconversion.Utils;

import jakarta.servlet.http.HttpServletRequest;

public final class RequestUtils {

    public static String getPathFromRequest(HttpServletRequest req) {
        int ignoreAmt = req.getContextPath().length() + req.getServletPath().length();
        return req.getRequestURI().substring(ignoreAmt + 1);
    }
}
