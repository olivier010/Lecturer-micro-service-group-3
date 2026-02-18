package com.lecturer.util;

import com.lecturer.model.Lecturer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JsonUtil {

    // Simple JSON builder for objects
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }

        if (obj instanceof Lecturer) {
            Lecturer lecturer = (Lecturer) obj;
            StringBuilder json = new StringBuilder();
            json.append("{");
            json.append("\"id\":").append(lecturer.getId()).append(",");
            json.append("\"staffId\":\"").append(escapeJson(lecturer.getStaffId())).append("\",");
            json.append("\"fullName\":\"").append(escapeJson(lecturer.getFullName())).append("\",");
            json.append("\"email\":\"").append(escapeJson(lecturer.getEmail())).append("\",");
            json.append("\"department\":\"").append(escapeJson(lecturer.getDepartment())).append("\",");
            json.append("\"phone\":\"").append(escapeJson(lecturer.getPhone())).append("\",");
            json.append("\"createdAt\":\"").append(lecturer.getCreatedAt()).append("\"");
            json.append("}");
            return json.toString();
        }

        if (obj instanceof List) {
            List<?> list = (List<?>) obj;
            StringBuilder json = new StringBuilder();
            json.append("[");
            for (int i = 0; i < list.size(); i++) {
                if (i > 0)
                    json.append(",");
                json.append(toJson(list.get(i)));
            }
            json.append("]");
            return json.toString();
        }

        if (obj instanceof String) {
            return "\"" + escapeJson((String) obj) + "\"";
        }

        if (obj instanceof Boolean || obj instanceof Number) {
            return obj.toString();
        }

        return "\"" + escapeJson(obj.toString()) + "\"";
    }

    // Parse JSON from request body (simple implementation)
    public static String getRequestBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    // Create success response
    public static String createSuccessResponse(Object data) {
        return "{\"success\":true,\"message\":\"Success\",\"data\":" + toJson(data) + "}";
    }

    // Create error response
    public static String createErrorResponse(String message) {
        return "{\"success\":false,\"message\":\"" + escapeJson(message) + "\",\"data\":null}";
    }

    // Send JSON response
    public static void sendJsonResponse(HttpServletResponse response, String json) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

    // Escape special characters for JSON
    private static String escapeJson(String str) {
        if (str == null)
            return "";
        return str.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Simple JSON parser for basic operations
    public static String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if (startIndex == -1)
            return null;

        startIndex += searchKey.length();

        // Skip whitespace
        while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
            startIndex++;
        }

        if (startIndex >= json.length())
            return null;

        char firstChar = json.charAt(startIndex);

        if (firstChar == '"') {
            // String value
            startIndex++;
            int endIndex = json.indexOf("\"", startIndex);
            if (endIndex == -1)
                return null;
            return json.substring(startIndex, endIndex);
        } else {
            // Number or boolean value
            int endIndex = startIndex;
            while (endIndex < json.length() &&
                    (Character.isLetterOrDigit(json.charAt(endIndex)) ||
                            json.charAt(endIndex) == '.' ||
                            json.charAt(endIndex) == '-')) {
                endIndex++;
            }
            return json.substring(startIndex, endIndex);
        }
    }
}
