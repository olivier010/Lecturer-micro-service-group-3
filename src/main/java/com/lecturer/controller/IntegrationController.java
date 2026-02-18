package com.lecturer.controller;

import com.lecturer.model.Lecturer;
import com.lecturer.service.LecturerService;
import com.lecturer.util.JsonUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/integration/*")
public class IntegrationController extends HttpServlet {
    private LecturerService lecturerService;
    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void init() {
        lecturerService = new LecturerService();
    }

    // Dummy endpoint for other services to validate lecturer exists
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo != null && pathInfo.startsWith("/validate/lecturer/")) {
                // Validate lecturer exists (for other services)
                int lecturerId = Integer.parseInt(pathInfo.substring("/validate/lecturer/".length()));
                boolean exists = lecturerService.lecturerExists(lecturerId);

                response.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.sendJsonResponse(response, JsonUtil.createSuccessResponse(exists));

            } else if (pathInfo != null && pathInfo.startsWith("/lecturer/")) {
                // Get basic lecturer info for other services
                int lecturerId = Integer.parseInt(pathInfo.substring("/lecturer/".length()));
                Lecturer lecturer = lecturerService.getLecturerBasicInfo(lecturerId);

                if (lecturer != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.sendJsonResponse(response, JsonUtil.createSuccessResponse(lecturer));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Lecturer not found"));
                }

            } else if (pathInfo != null && pathInfo.equals("/health")) {
                // Health check endpoint
                response.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.sendJsonResponse(response, JsonUtil.createSuccessResponse("Lecturer Service is running"));

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid integration endpoint"));
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Database error: " + e.getMessage()));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJsonResponse(response,
                    JsonUtil.createErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    // Dummy endpoint to simulate consuming another service
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo != null && pathInfo.equals("/test-communication")) {
                // Test communication with other services
                response.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.sendJsonResponse(response,
                        JsonUtil.createSuccessResponse("Lecturer Service received your message"));

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid integration endpoint"));
            }

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJsonResponse(response,
                    JsonUtil.createErrorResponse("Internal server error: " + e.getMessage()));
        }
    }
}
