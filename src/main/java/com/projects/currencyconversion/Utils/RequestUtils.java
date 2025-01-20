package com.projects.currencyconversion.Utils;

import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;
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
            String[] pairs = reader.readLine().split("&");

            for (String pair : pairs) {
                String[] keyValue = pair.split("=", 2);
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                } else {
                    throw new RemoteException("Incorrect params in request body");
                }
            }
        }
        return params;
    }
}
