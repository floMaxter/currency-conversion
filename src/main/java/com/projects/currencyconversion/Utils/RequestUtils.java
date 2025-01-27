package com.projects.currencyconversion.Utils;

import com.projects.currencyconversion.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class RequestUtils {

    public static String getPathFromRequest(HttpServletRequest req) {
        int ignoreAmt = req.getContextPath().length() + req.getServletPath().length();
        return req.getRequestURI().substring(ignoreAmt + 1);
    }

    public static Map<String, String> getParamsFromRequestBody(HttpServletRequest req) throws IOException {
        Map<String, String> params = new HashMap<>();
        try (BufferedReader reader = req.getReader()) {
            String lines = reader.readLine();
            if (lines == null) {
                throw new ValidationException("The request doesn't contain the rate parameter.");
            }

            String[] pairs = lines.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                } else {
                    throw new ValidationException("Incorrect params in request body");
                }
            }
        }
        return params;
    }
}
