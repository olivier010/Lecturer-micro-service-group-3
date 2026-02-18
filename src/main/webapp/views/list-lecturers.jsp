<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Lecturer Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #072353, #084298);
        }

        .container {
            width: 95%;
            max-width: 1200px;
            margin: auto;
            padding: 40px 0;
        }

        h2 {
            margin: 0 0 20px 0;
            color: #ffffff;
        }

        /* Card */
        .card {
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
            margin-bottom: 25px;
            overflow: hidden;
        }

        .card-header {
            padding: 12px 20px;
            font-weight: bold;
            color: white;
        }

        .card-header.primary {
            background-color: #0d6efd;
        }

        .card-header.dark {
            background-color: #212529;
        }

        .card-body {
            padding: 20px;
        }

        /* Form Layout */
        .row {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }

        .col {
            flex: 1 1 180px;
            min-width: 150px;
            display: flex;
            flex-direction: column;
        }

        label {
            font-size: 12px;
            color: #6c757d;
            margin-bottom: 5px;
        }

        input, select {
            padding: 8px 10px;
            border: 1px solid #ced4da;
            border-radius: 4px;
            font-size: 14px;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #0d6efd;
        }

        /* Buttons */
        .btn {
            padding: 8px 10px;
            font-size: 14px;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
            border: 1px solid transparent;
            text-decoration: none;
            display: inline-block;
        }

        .btn-success {
            background-color: #198754;
            color: white;
            border-color: #198754;
        }

        .btn-outline-primary {
            border-color: #0d6efd;
            color: #0d6efd;
            background: transparent;
        }

        .btn-outline-warning {
            border-color: #ffc107;
            color: #ffc107;
            background: transparent;
        }

        .btn-outline-danger {
            border-color: #dc3545;
            color: #dc3545;
            background: transparent;
        }

        .btn:hover {
            opacity: 0.9;
        }

        /* Table */
        table {
            width: 100%;
            border-collapse: collapse;
        }

        thead {
            background-color: #f1f3f5;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #dee2e6;
            text-align: left;
            font-size: 14px;
        }

        th {
            font-weight: bold;
        }

        tbody tr:hover {
            background-color: #f8f9fa;
        }

        .badge {
            background-color: #6c757d;
            color: white;
            padding: 4px 8px;
            border-radius: 12px;
            font-size: 12px;
        }

        /* Actions */
        .actions {
            display: flex;
            gap: 6px;
            justify-content: center;
            flex-wrap: nowrap;
        }

        .actions a,
        .actions form {
            flex: 1;
        }

        .actions button,
        .actions a {
            width: 100%;
            text-align: center;
        }

        /* Empty state */
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #6c757d;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .row {
                flex-direction: column;
            }

            .actions {
                flex-direction: column;
            }
        }

    </style>
</head>

<body>

<div class="container">

    <h2>Lecturer Management</h2>

    <!-- Add Lecturer Card -->
    <div class="card">
        <div class="card-header primary">
            Add New Lecturer
        </div>
        <div class="card-body">
            <form action="lecturers" method="post">
                <input type="hidden" name="action" value="add">

                <div class="row">

                    <div class="col">
                        <label>Staff ID</label>
                        <input type="text" name="staffId" required>
                    </div>

                    <div class="col">
                        <label>Full Name</label>
                        <input type="text" name="fullName" required>
                    </div>

                    <div class="col">
                        <label>Email</label>
                        <input type="email" name="email" required>
                    </div>

                    <div class="col">
                        <label>Department</label>
                        <select name="department" required>
                            <option value="">Select</option>
                            <option value="Computer Science">Computer Science</option>
                            <option value="Information Technology">Information Technology</option>
                            <option value="Software Engineering">Software Engineering</option>
                            <option value="Data Science">Data Science</option>
                            <option value="Cybersecurity">Cybersecurity</option>
                            <option value="Business Administration">Business Administration</option>
                        </select>
                    </div>

                    <div class="col">
                        <label>Phone</label>
                        <input type="tel" name="phone">
                    </div>

                    <div class="col" style="justify-content: flex-end;">
                        <button type="submit" class="btn btn-success">
                            + Add Lecturer
                        </button>
                    </div>

                </div>
            </form>
        </div>
    </div>

    <!-- Lecturer Table Card -->
    <div class="card">
        <div class="card-header dark">
            Lecturer List
        </div>
        <div class="card-body" style="padding:0;">

            <table>
                <thead>
                <tr>
                    <th>Staff ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Department</th>
                    <th>Phone</th>
                    <th>Created</th>
                    <th style="text-align:center;">Actions</th>
                </tr>
                </thead>
                <tbody>

                <c:forEach var="l" items="${listLecturers}">
                    <tr>
                        <td>${l.staffId}</td>
                        <td><strong>${l.fullName}</strong></td>
                        <td>${l.email}</td>
                        <td><span class="badge">${l.department}</span></td>
                        <td>${l.phone}</td>
                        <td>
                            <fmt:formatDate value="${l.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                        </td>
                        <td style="text-align:center;">

                            <div class="actions">

                                <a href="lecturers?action=view&id=${l.id}"
                                   class="btn btn-outline-primary">
                                    View
                                </a>

                                <a href="lecturers?action=edit&id=${l.id}"
                                   class="btn btn-outline-warning">
                                    Edit
                                </a>

                                <form action="lecturers" method="post"
                                      onsubmit="return confirm('Are you sure you want to delete ${l.fullName}?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${l.id}">
                                    <button type="submit" class="btn btn-outline-danger">
                                        Delete
                                    </button>
                                </form>

                            </div>

                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <!-- Empty State -->
            <c:if test="${empty listLecturers}">
                <div class="empty-state">
                    <h5>No lecturers found</h5>
                    <p>Add your first lecturer using the form above.</p>
                </div>
            </c:if>

        </div>
    </div>

</div>

</body>
</html>
