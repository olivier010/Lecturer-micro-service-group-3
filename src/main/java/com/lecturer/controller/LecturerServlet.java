package com.lecturer.controller;

import com.lecturer.model.Lecturer;
import com.lecturer.service.LecturerService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/lecturers") // This is the URL path
public class LecturerServlet extends HttpServlet {

    // Use the service layer instead of DAO directly
    private LecturerService lecturerService;

    public void init() {
        lecturerService = new LecturerService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("view".equals(action)) {
                // View lecturer profile
                int id = Integer.parseInt(request.getParameter("id"));
                Lecturer lecturer = lecturerService.getLecturerById(id);
                request.setAttribute("lecturer", lecturer);
                request.getRequestDispatcher("views/lecturer-profile.jsp").forward(request, response);
            } else if ("edit".equals(action)) {
                // Edit lecturer form
                int id = Integer.parseInt(request.getParameter("id"));
                Lecturer lecturer = lecturerService.getLecturerById(id);
                request.setAttribute("lecturer", lecturer);
                request.getRequestDispatcher("views/edit-lecturer.jsp").forward(request, response);
            } else if ("department".equals(action)) {
                // View lecturers by department
                String department = request.getParameter("dept");
                java.util.List<Lecturer> list = lecturerService.getLecturersByDepartment(department);
                request.setAttribute("listLecturers", list);
                request.setAttribute("department", department);
                request.getRequestDispatcher("views/list-lecturers.jsp").forward(request, response);
            } else {
                // Default: list all lecturers
                java.util.List<Lecturer> list = lecturerService.getAllLecturers();
                request.setAttribute("listLecturers", list);
                request.getRequestDispatcher("views/list-lecturers.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");

            if ("add".equals(action)) {
                // Add new lecturer
                String staffId = request.getParameter("staffId");
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String department = request.getParameter("department");
                String phone = request.getParameter("phone");

                Lecturer lecturer = new Lecturer(staffId, fullName, email, department, phone);
                lecturerService.addLecturer(lecturer);
                response.sendRedirect("lecturers");

            } else if ("update".equals(action)) {
                // Update existing lecturer
                int id = Integer.parseInt(request.getParameter("id"));
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String department = request.getParameter("department");
                String phone = request.getParameter("phone");

                Lecturer lecturer = new Lecturer();
                lecturer.setId(id);
                lecturer.setFullName(fullName);
                lecturer.setEmail(email);
                lecturer.setDepartment(department);
                lecturer.setPhone(phone);

                lecturerService.updateLecturer(lecturer);
                response.sendRedirect("lecturers");

            } else if ("delete".equals(action)) {
                // Delete lecturer
                int id = Integer.parseInt(request.getParameter("id"));
                lecturerService.deleteLecturer(id);
                response.sendRedirect("lecturers");
            }

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}