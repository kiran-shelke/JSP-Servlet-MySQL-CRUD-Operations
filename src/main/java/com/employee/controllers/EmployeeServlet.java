package com.employee.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.employee.dao.EmployeeDAO;
import com.employee.model.Employee;

/**
 * Servlet implementation class EmployeeServlet
 */
@WebServlet("/")
public class EmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EmployeeDAO employeeDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeServlet() {
        super();
        this.employeeDAO=new EmployeeDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action=request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(request,response);
			break;
		case "/insert":
			insertEmployee(request,response);
			break;
		case "/delete":
			try {
				deleteEmployee(request,response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "/edit":
			try {
				showEditForm(request,response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateEmployee(request,response);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			//handle list
			listEmployee(request,response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	//list all employee
	private void listEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Employee> list = employeeDAO.selectAllEmp();
		request.setAttribute("listEmployee", list);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeList.jsp");
		dispatcher.forward(request, response);
	}
	
	//update employee
	private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int empId = Integer.parseInt(request.getParameter("id"));
		String empName = request.getParameter("name");
		String empDept = request.getParameter("dept");
		double empSal = Double.parseDouble(request.getParameter("salary"));
		Employee employee = new Employee(empId, empName, empDept, empSal);
		employeeDAO.updateEmployee(employee);
		response.sendRedirect("list");
	}
	
	
	//show edit form
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		Employee employee = employeeDAO.selectEmp(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("employeeForm.jsp");
		request.setAttribute("employee", employee);
		dispatcher.forward(request, response);
	}
	
	//Show new form
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher=request.getRequestDispatcher("employeeForm.jsp");
		dispatcher.forward(request, response);
	}
	
	//insert employee
	private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String empName=request.getParameter("name");
		String empDept=request.getParameter("dept");
		double empSal=Double.parseDouble(request.getParameter("salary"));
		Employee employee = new Employee(empName, empDept, empSal);
		employeeDAO.inserEmployee(employee);
		response.sendRedirect("list");
	}
	
	//delete employee
	private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		employeeDAO.deleteEmployee(id);
		response.sendRedirect("list");
	}
	

}
