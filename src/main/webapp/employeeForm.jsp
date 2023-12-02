<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark"
			style="background-color: Gray">
			<div>
				<a href="#" class="navbar-brand"> Employee Management
					Application </a>
			</div>

			<ul class="navbar-nav">
				<li><a href="<%=request.getContextPath()%>/list"
					class="nav-link">Employee</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${employee != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${employee == null}">
					<form action="insert" method="post">
				</c:if>
				<caption>
					<h2>
						<c:if test="${employee != null}">
            			Edit User
            			</c:if>
						<c:if test="${employee == null}">
            			Add New User
            			</c:if>
					</h2>
				</caption>
				<c:if test="${employee != null}">
					<input type="hidden" name="id" value="<c:out value='${employee.empId}' />" />
				</c:if>
				<fieldset class="form-group">
					<label>Employee Name</label> <input type="text" value="<c:out value='${employee.empName}' />" class="form-control"
						name="name" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>Employee Dept</label> <input type="text" value="<c:out value='${employee.empDept}' />" class="form-control"
						name="dept">
				</fieldset>
				<fieldset class="form-group">
					<label>Employee Salary</label> <input type="text" value="<c:out value='${employee.empSal}' />" class="form-control"
						name="salary">
				</fieldset>
				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>