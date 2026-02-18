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
import java.util.List;

@WebServlet("/api/lecturers/*")
public class LecturerApiController extends HttpServlet {
    private LecturerService lecturerService;
    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void init() {
        lecturerService = new LecturerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                // Get all lecturers
                List<Lecturer> lecturers = lecturerService.getAllLecturers();
                response.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.sendJsonResponse(response, JsonUtil.createSuccessResponse(lecturers));

            } else if (pathInfo.matches("/\\d+")) {
                // Get lecturer by ID
                int id = Integer.parseInt(pathInfo.substring(1));
                Lecturer lecturer = lecturerService.getLecturerById(id);

                if (lecturer != null) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.sendJsonResponse(response, JsonUtil.createSuccessResponse(lecturer));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Lecturer not found"));
                }

            } else if (pathInfo.startsWith("/department/")) {
                // Get lecturers by department
                String department = pathInfo.substring("/department/".length());
                List<Lecturer> lecturers = lecturerService.getLecturersByDepartment(department);
                response.setStatus(HttpServletResponse.SC_OK);
                JsonUtil.sendJsonResponse(response, JsonUtil.createSuccessResponse(lecturers));

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid endpoint"));
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo == null || pathInfo.equals("/")) {
                // Add new lecturer - parse JSON manually
                String jsonBody = JsonUtil.getRequestBody(request);
                Lecturer lecturer = parseLecturerFromJson(jsonBody);

                boolean success = lecturerService.addLecturer(lecturer);

                if (success) {
                    response.setStatus(HttpServletResponse.SC_CREATED);
                    JsonUtil.sendJsonResponse(response,
                            JsonUtil.createSuccessResponse("Lecturer created successfully"));
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Failed to create lecturer"));
                }

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid endpoint"));
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Database error: " + e.getMessage()));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid request: " + e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo != null && pathInfo.matches("/\\d+")) {
                // Update lecturer
                int id = Integer.parseInt(pathInfo.substring(1));
                String jsonBody = JsonUtil.getRequestBody(request);
                Lecturer lecturer = parseLecturerFromJson(jsonBody);
                lecturer.setId(id);

                boolean success = lecturerService.updateLecturer(lecturer);

                if (success) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.sendJsonResponse(response,
                            JsonUtil.createSuccessResponse("Lecturer updated successfully"));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.sendJsonResponse(response,
                            JsonUtil.createErrorResponse("Lecturer not found or update failed"));
                }

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid endpoint"));
            }

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Database error: " + e.getMessage()));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid request: " + e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = request.getPathInfo();

            if (pathInfo != null && pathInfo.matches("/\\d+")) {
                // Delete lecturer
                int id = Integer.parseInt(pathInfo.substring(1));

                boolean success = lecturerService.deleteLecturer(id);

                if (success) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    JsonUtil.sendJsonResponse(response,
                            JsonUtil.createSuccessResponse("Lecturer deleted successfully"));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Lecturer not found"));
                }

            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                JsonUtil.sendJsonResponse(response, JsonUtil.createErrorResponse("Invalid endpoint"));
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

    // Helper method to parse Lecturer from JSON
    private Lecturer parseLecturerFromJson(String json) {
        Lecturer lecturer = new Lecturer();

        String staffId = JsonUtil.extractJsonValue(json, "staffId");
        String fullName = JsonUtil.extractJsonValue(json, "fullName");
        String email = JsonUtil.extractJsonValue(json, "email");
        String department = JsonUtil.extractJsonValue(json, "department");
        String phone = JsonUtil.extractJsonValue(json, "phone");

        lecturer.setStaffId(staffId != null ? staffId : "");
        lecturer.setFullName(fullName != null ? fullName : "");
        lecturer.setEmail(email != null ? email : "");
        lecturer.setDepartment(department != null ? department : "");
        lecturer.setPhone(phone != null ? phone : "");

        return lecturer;
    }
}
