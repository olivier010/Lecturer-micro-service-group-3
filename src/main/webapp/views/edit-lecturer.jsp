<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Lecturer</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container py-5">
<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card shadow">
            <div class="card-header bg-warning text-dark d-flex justify-content-between align-items-center">
                <h4 class="mb-0">Edit Lecturer Information</h4>
                <a href="lecturers?action=view&id=${lecturer.id}" class="btn btn-secondary btn-sm">
                    Cancel
                </a>
            </div>
            <div class="card-body">
                <c:if test="${not empty lecturer}">
                    <form action="lecturers" method="post">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${lecturer.id}">
                        
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Staff ID</label>
                                <input type="text" class="form-control" value="${lecturer.staffId}" readonly>
                                <small class="text-muted">Staff ID cannot be changed</small>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Full Name</label>
                                <input type="text" name="fullName" class="form-control" value="${lecturer.fullName}" required>
                            </div>
                        </div>
                        
                        <div class="row mb-3">
                            <div class="col-md-6">
                                <label class="form-label">Email</label>
                                <input type="email" name="email" class="form-control" value="${lecturer.email}" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label">Phone</label>
                                <input type="tel" name="phone" class="form-control" value="${lecturer.phone}">
                            </div>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label">Department</label>
                            <select name="department" class="form-select" required>
                                <option value="">Select Department</option>
                                <option value="Computer Science" ${lecturer.department == 'Computer Science' ? 'selected' : ''}>Computer Science</option>
                                <option value="Information Technology" ${lecturer.department == 'Information Technology' ? 'selected' : ''}>Information Technology</option>
                                <option value="Software Engineering" ${lecturer.department == 'Software Engineering' ? 'selected' : ''}>Software Engineering</option>
                                <option value="Data Science" ${lecturer.department == 'Data Science' ? 'selected' : ''}>Data Science</option>
                                <option value="Cybersecurity" ${lecturer.department == 'Cybersecurity' ? 'selected' : ''}>Cybersecurity</option>
                                <option value="Business Administration" ${lecturer.department == 'Business Administration' ? 'selected' : ''}>Business Administration</option>
                            </select>
                        </div>
                        
                        <div class="d-flex gap-2">
                            <button type="submit" class="btn btn-warning">
                                Update Information
                            </button>
                            <a href="lecturers?action=view&id=${lecturer.id}" class="btn btn-secondary">
                                Cancel
                            </a>
                        </div>
                    </form>
                </c:if>
                <c:if test="${empty lecturer}">
                    <div class="alert alert-danger">
                        Lecturer not found!
                    </div>
                </c:if>
            </div>
        </div>
    </div>
</div>
</body>
</html>
