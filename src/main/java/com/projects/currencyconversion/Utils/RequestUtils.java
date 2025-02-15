package com.projects.currencyconversion.Utils;

import com.projects.currencyconversion.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class RequestUtils {

    private static final Integer CODE_LENGTH = Integer.parseInt(PropertiesUtil.get("db.currency.code.length"));
    private static final String EMPTY_STRING = "";

    public static String getPathFromRequest(HttpServletRequest req) {
        int ignoreAmt = req.getContextPath().length() + req.getServletPath().length();
        return req.getRequestURI().substring(ignoreAmt + 1);
    }

    public static String getParamValueFromRequest(HttpServletRequest req, String paramName) {
        String[] values = req.getParameterValues(paramName);
        validateSingleParam(paramName, values);
        return values[0];
    }

    public static String getParamValueFromBody(HttpServletRequest req, String paramName) {
        String[] values = extractValueFromBody(req, paramName);
        validateSingleParam(paramName, values);
        return values[0];
    }

    public static String[] getCoupleOfCurrencyCode(String coupleOfCode) {
        String[] codes = new String[2];

        if (coupleOfCode.length() < CODE_LENGTH) {
            codes[0] = coupleOfCode;
            codes[1] = EMPTY_STRING;
        } else {
            codes[0] = coupleOfCode.substring(0, CODE_LENGTH);
            codes[1] = coupleOfCode.substring(CODE_LENGTH);
        }
        return codes;
    }

    private static void validateSingleParam(String paramName, String[] values) {
        if (values == null || values.length == 0) {
            throw new ValidationException("Missing required parameter: " + paramName);
        }
        if (values.length > 1) {
            throw new ValidationException("Multiple values found for parameter: " + paramName);
        }
    }

    private static String[] extractValueFromBody(HttpServletRequest req, String paramName) {
        Map<String, List<String>> params;
        try (BufferedReader reader = req.getReader()) {
            String lines = reader.readLine();
            if (lines == null || lines.isEmpty()) {
                throw new ValidationException("The request is missing parameters.");
            }

            params = parseRequestBodyParam(lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> values = params.getOrDefault(paramName, Collections.emptyList());
        return values.toArray(new String[0]);
    }

    private static Map<String, List<String>> parseRequestBodyParam(String lines) {
        Map<String, List<String>> params = new HashMap<>();
        String[] pairs = lines.split("&");
        for (String pair : pairs) {
            if (pair.isEmpty()) {
                throw  new ValidationException("One of the request parameters is empty");
            }

            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                addParamToMap(keyValue, params);
            } else {
                throw new ValidationException("Incorrect params in request body");
            }
        }
        return params;
    }

    private static void addParamToMap(String[] keyValue, Map<String, List<String>> params) {
        String key = keyValue[0];
        String value = keyValue[1];
        if (!params.containsKey(key)) {
            params.put(key, new ArrayList<>(Collections.singleton(value)));
        } else {
            List<String> values = params.get(key);
            values.add(value);
            params.put(key, values);
        }
    }
}
