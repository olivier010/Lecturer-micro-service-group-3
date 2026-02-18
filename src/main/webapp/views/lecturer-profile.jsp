<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Lecturer Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <style>
        body {
            margin: 0;
            padding: 40px 15px;
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #0d6efd, #084298);
        }

        .container {
            max-width: 900px;
            margin: auto;
        }

        .card {
            background: #ffffff;
            border-radius: 10px;
            box-shadow: 0 6px 18px rgba(0,0,0,0.08);
            overflow: hidden;
        }

        .card-header {
            background: linear-gradient(135deg, #0d6efd, #084298);
            color: white;
            padding: 18px 22px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .card-body {
            padding: 30px;
        }

        .row {
            display: flex;
            flex-wrap: wrap;
            margin-bottom: 25px;
        }

        .col-4 {
            flex: 0 0 33.333%;
            text-align: center;
        }

        .col-8 {
            flex: 0 0 66.666%;
        }

        .avatar {
            width: 150px;
            height: 150px;
            background-color: #e9ecef;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
        }

        .avatar span {
            font-size: 60px;
            color: #0d6efd;
            font-weight: bold;
        }

        h3 {
            margin-top: 0;
            color: #0d6efd;
        }

        p {
            margin: 8px 0;
            font-size: 14px;
        }

        .text-muted {
            color: #6c757d;
        }

        /* Modern Buttons */
        .button-group {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .btn {
            padding: 10px 18px;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            border: none;
            transition: all 0.2s ease;
            min-width: 140px;
            text-align: center;
        }

        .btn-primary-light {
            background-color: #ffffff;
            color: #0d6efd;
            border: 1px solid #0d6efd;
        }

        .btn-primary-light:hover {
            background-color: #0d6efd;
            color: #ffffff;
        }

        .btn-warning {
            background-color: #ffc107;
            color: #000;
        }

        .btn-warning:hover {
            background-color: #e0a800;
        }

        .btn-danger {
            background-color: #dc3545;
            color: white;
        }

        .btn-danger:hover {
            background-color: #bb2d3b;
        }

        .btn-info {
            background-color: #0dcaf0;
            color: #000;
        }

        .btn-info:hover {
            background-color: #0bb5d6;
        }

        .alert {
            padding: 15px;
            border-radius: 6px;
            background-color: #f8d7da;
            color: #842029;
            border: 1px solid #f5c2c7;
        }

        @media (max-width: 768px) {
            .row {
                flex-direction: column;
                text-align: center;
            }

            .col-4, .col-8 {
                flex: 100%;
            }

            .button-group {
                flex-direction: column;
                align-items: center;
            }

            .btn {
                width: 100%;
                max-width: 300px;
            }
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h4>Lecturer Profile</h4>
            <a href="lecturers" class="btn btn-primary-light">
                Back to List
            </a>
        </div>

        <div class="card-body">

            <c:if test="${not empty lecturer}">
                <div class="row">
                    <div class="col-4">
                        <div class="avatar">
                            <span>${lecturer.fullName.charAt(0)}</span>
                        </div>
                    </div>

                    <div class="col-8">
                        <h3>${lecturer.fullName}</h3>
                        <p class="text-muted"><strong>Staff ID:</strong> ${lecturer.staffId}</p>
                        <p><strong>Department:</strong> ${lecturer.department}</p>
                        <p><strong>Email:</strong> ${lecturer.email}</p>
                        <p><strong>Phone:</strong> ${lecturer.phone}</p>
                        <p><strong>Joined:</strong>
                            <fmt:formatDate value="${lecturer.createdAt}" pattern="MMMM dd, yyyy HH:mm"/>
                        </p>
                    </div>
                </div>

                <div class="button-group">
                    <a href="lecturers?action=edit&id=${lecturer.id}" class="btn btn-warning">
                        Edit Information
                    </a>

                    <form action="lecturers" method="post"
                          onsubmit="return confirm('Are you sure you want to delete this lecturer?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${lecturer.id}">
                        <button type="submit" class="btn btn-danger">
                            Delete
                        </button>
                    </form>

                    <a href="lecturers?action=department&dept=${lecturer.department}" class="btn btn-info">
                        View Department
                    </a>
                </div>
            </c:if>

            <c:if test="${empty lecturer}">
                <div class="alert">
                    Lecturer not found!
                </div>
            </c:if>

        </div>
    </div>
</div>

</body>
</html>
